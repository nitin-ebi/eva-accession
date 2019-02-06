/*
 * Copyright 2018 EMBL - European Bioinformatics Institute
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
package uk.ac.ebi.eva.accession.release.io;

import htsjdk.variant.vcf.VCFHeaderLine;
import htsjdk.variant.vcf.VCFHeaderLineType;
import htsjdk.variant.vcf.VCFInfoHeaderLine;

import java.io.File;
import java.util.Set;

import static uk.ac.ebi.eva.accession.release.io.MergedVariantMongoReader.MERGED_INTO_KEY;

public class MergedVariantContextWriter extends VariantContextWriter {

    public MergedVariantContextWriter(File output, String referenceAssembly) {
        super(output, referenceAssembly);
    }

    @Override
    protected Set<VCFHeaderLine> buildHeaderLines() {
        Set<VCFHeaderLine> vcfHeaderLines = super.buildHeaderLines();
        vcfHeaderLines.add(new VCFInfoHeaderLine(MERGED_INTO_KEY, 1, VCFHeaderLineType.String,
                                                 "Active RS id to represent a given variant"));
        return vcfHeaderLines;
    }

}
