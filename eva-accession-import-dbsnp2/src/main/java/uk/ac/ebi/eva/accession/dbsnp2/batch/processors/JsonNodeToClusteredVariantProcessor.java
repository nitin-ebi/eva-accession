/*
 * Copyright 2019 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.ebi.eva.accession.dbsnp2.batch.processors;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;
import uk.ac.ebi.ampt2d.commons.accession.hashing.SHA1HashingFunction;
import uk.ac.ebi.eva.accession.core.model.ClusteredVariant;
import uk.ac.ebi.eva.accession.core.model.IClusteredVariant;
import uk.ac.ebi.eva.accession.core.model.dbsnp.DbsnpClusteredVariantEntity;
import uk.ac.ebi.eva.accession.core.summary.ClusteredVariantSummaryFunction;
import uk.ac.ebi.eva.commons.core.models.VariantType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

/**
 * Processes a DbSNP JsonNode to produce a clustered variant entity.
 */
public class JsonNodeToClusteredVariantProcessor implements ItemProcessor<JsonNode, DbsnpClusteredVariantEntity> {

    private static Logger logger = LoggerFactory.getLogger(JsonNodeToClusteredVariantProcessor.class);
    private Function<IClusteredVariant, String> hashingFunction =
        new ClusteredVariantSummaryFunction().andThen(new SHA1HashingFunction());
    private String refseqAssembly;
    private String genbankAssembly;
    private int previousImportedBuild;
    private boolean incrementalImport;

    public JsonNodeToClusteredVariantProcessor(String refseqAssembly, String genbankAssembly,
                                               int previousImportedBuild, boolean incrementalImport) {
        this.refseqAssembly = refseqAssembly;
        this.genbankAssembly = genbankAssembly;
        this.previousImportedBuild = previousImportedBuild;
        this.incrementalImport = incrementalImport;
    }

    /**
     * Parses the JSON into a DbsnpClusteredVariantEntity
     * @param jsonRootNode root node of a dbSNP JSON line item
     * @return dbsnpClusteredVariantEntity
     */
    public DbsnpClusteredVariantEntity process(JsonNode jsonRootNode) {
        long accession = Long.parseLong(jsonRootNode.path("refsnp_id").asText());
        // If we are doing incremental import, ignore record if it is not a new RS
        if(incrementalImport && !isNewRS(jsonRootNode)) {
            logger.debug("Variant with RS ID {} skipped"
                            + " because it is not a new RS added after build {}}",
                    jsonRootNode.path("refsnp_id").asText(), previousImportedBuild);
            return null;
        }
        ClusteredVariant clusteredVariant = parseJsonNodeToClusteredVariant(jsonRootNode);
        if(clusteredVariant == null) {
            return null;
        }
        String hashedMessage = hashingFunction.apply(clusteredVariant);
        return new DbsnpClusteredVariantEntity(accession, hashedMessage, clusteredVariant);
    }

    private boolean isNewRS(JsonNode jsonNode) {
        // The record of movements between the previous and current release
        // See https://api.ncbi.nlm.nih.gov/variation/v0/var_service.yaml for definition
        JsonNode presentObsMovementsNode = jsonNode.path("present_obs_movements");
        // If the attribute is found, try looking at the earliest build from the list of movements
        if (!presentObsMovementsNode.isMissingNode()) {
            Set<Integer> movementBuilds = new HashSet<>();
            for (JsonNode movementNode : presentObsMovementsNode) {
                JsonNode lastAddedBuildNode = movementNode.path("last_added_to_this_rs");
                if (!lastAddedBuildNode.isMissingNode()) {
                    movementBuilds.add(lastAddedBuildNode.asInt());
                }
            }
            if (!movementBuilds.isEmpty()) {
                return movementBuilds.stream().reduce(Math::min).orElse(Integer.MAX_VALUE) > previousImportedBuild;
            }
        }
        // If the present_obs_movements attribute is not found, we might as well roll the dice and try importing the RS
        // If it happens to be a duplicate RS, it will be ignored anyway
        // Since there should not be a lot of records with this attribute missing, this is a tolerable overhead
        return true;
    }

    private ClusteredVariant parseJsonNodeToClusteredVariant(JsonNode jsonRootNode) {
        // @see <a href=https://www.ncbi.nlm.nih.gov/Taxonomy/Browser/wwwtax.cgi?mode=Info&id=9606>Human Tax ID.</a>
        int taxonomyAccession = 9606;
        // JSON date in ISO-8601 format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d'T'HH:mm'Z'");
        LocalDateTime createdDate = LocalDateTime.parse(jsonRootNode.path("create_date").asText(), formatter);
        VariantType type = translateVariantType(jsonRootNode.path("primary_snapshot_data").path("variant_type").asText());
        JsonNode infoNode = jsonRootNode.path("primary_snapshot_data").path("placements_with_allele");
        for(JsonNode alleleInfo : infoNode) {
            boolean isPtlp = alleleInfo.path("is_ptlp").asBoolean();
            JsonNode assemblyInfo = alleleInfo.path("placement_annot").path("seq_id_traits_by_assembly");
            if(assemblyInfo.size() == 0 || !isPtlp) {
                continue;
            }

            boolean assemblyFound = false;
            for (int i = 0; (i < assemblyInfo.size()) && !assemblyFound; i++) {
                String assemblyAccession = assemblyInfo.get(i).path("assembly_accession").asText();
                assemblyFound = refseqAssembly.equals(assemblyAccession);
            }

            // Ignore variant if its assembly accession doesn't match the one supplied in input parameters
            if(!assemblyFound) {
                logger.error("Variant with RS ID {} does not have any mappings for"
                                     + " the RefSeq assembly {} supplied in the input parameters",
                             jsonRootNode.path("refsnp_id").asText(), refseqAssembly);
                return null;
            }
            JsonNode spdi = alleleInfo.path("alleles").get(0).path("allele").path("spdi");
            String contig = spdi.path("seq_id").asText();
            // DbSNP JSON in 0 base, EVA in 1 base
            // @see <a href=https://api.ncbi.nlm.nih.gov/variation/v0/>DbSNP JSON 2.0 schema specification</a>
            long start = spdi.path("position").asLong() + 1;
            return new ClusteredVariant(genbankAssembly, taxonomyAccession, contig, start,
                                        type, Boolean.FALSE, createdDate);
        }
        // Absence of primary top level placement (PLTP) data
        logger.error("Primary top level placement data not present for accession refSNP ID: {}",
                     jsonRootNode.path("refsnp_id").asLong());
        return null;
    }

    /**
     * Translates a dbSNP variant type to the corresponding EVA one
     * @see <a href=https://api.ncbi.nlm.nih.gov/variation/v0/>DbSNP JSON 2.0 schema specification</a>
     * @param variantType DbSNP2.0 JSON variant type
     * @return EVA variant type representation
     */
    private VariantType translateVariantType(String variantType) {
        switch(variantType.toUpperCase()) {
            case "SNV":
                return VariantType.SNV;
            case "MNV":
                return VariantType.MNV;
            case "INS":
                return VariantType.INS;
            case "DEL":
                return VariantType.DEL;
            case "DELINS":
                return VariantType.INDEL;
            case "IDENTITY":
                return VariantType.NO_SEQUENCE_ALTERATION;
            default:
                throw new IllegalArgumentException(
                    "The dbSNP variant type provided doesn't have a direct mapping to an EVA type");
        }
    }
}
