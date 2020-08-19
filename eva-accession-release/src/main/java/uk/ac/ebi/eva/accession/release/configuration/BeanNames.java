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
package uk.ac.ebi.eva.accession.release.configuration;

public class BeanNames {

    ///////////////// common beans
    public static final String ACCESSION_RELEASE_JOB = "ACCESSION_RELEASE_JOB";

    public static final String RELEASE_PROCESSOR = "RELEASE_PROCESSOR";

    public static final String EXCLUDE_VARIANTS_LISTENER = "EXCLUDE_VARIANTS_LISTENER";

    public static final String PROGRESS_LISTENER = "PROGRESS_LISTENER";

    ///////////////// dbsnp beans
    public static final String RELEASE_DBSNP_MAPPED_ACTIVE_VARIANTS_STEP = "RELEASE_DBSNP_MAPPED_ACTIVE_VARIANTS_STEP";

    public static final String RELEASE_DBSNP_MAPPED_MERGED_VARIANTS_STEP = "RELEASE_DBSNP_MAPPED_MERGED_VARIANTS_STEP";

    public static final String RELEASE_DBSNP_MAPPED_MERGED_DEPRECATED_VARIANTS_STEP =
            "RELEASE_DBSNP_MAPPED_MERGED_DEPRECATED_VARIANTS_STEP";

    public static final String RELEASE_DBSNP_MULTIMAP_VARIANTS_STEP = "RELEASE_DBSNP_MULTIMAP_VARIANTS_STEP";

    public static final String RELEASE_DBSNP_MAPPED_DEPRECATED_VARIANTS_STEP =
            "RELEASE_DBSNP_MAPPED_DEPRECATED_VARIANTS_STEP";

    public static final String DBSNP_ACCESSIONED_VARIANT_READER = "DBSNP_ACCESSIONED_VARIANT_READER";

    public static final String DBSNP_MERGED_VARIANT_READER = "DBSNP_MERGED_VARIANT_READER";

    public static final String DBSNP_DEPRECATED_VARIANT_READER = "DBSNP_DEPRECATED_VARIANT_READER";

    public static final String DBSNP_MERGED_DEPRECATED_VARIANT_READER = "DBSNP_MERGED_DEPRECATED_VARIANT_READER";

    public static final String DBSNP_MULTIMAP_VARIANT_READER = "DBSNP_MULTIMAP_VARIANT_READER";

    public static final String DBSNP_RELEASE_WRITER = "DBSNP_RELEASE_WRITER";

    public static final String DBSNP_MERGED_RELEASE_WRITER = "DBSNP_MERGED_RELEASE_WRITER";

    public static final String DBSNP_MULTIMAP_RELEASE_WRITER = "DBSNP_MULTIMAP_RELEASE_WRITER";

    public static final String DBSNP_MERGED_DEPRECATED_RELEASE_WRITER = "DBSNP_MERGED_DEPRECATED_RELEASE_WRITER";

    public static final String DBSNP_DEPRECATED_RELEASE_WRITER = "DBSNP_DEPRECATED_RELEASE_WRITER";

    public static final String LIST_DBSNP_ACTIVE_CONTIGS_STEP = "LIST_DBSNP_ACTIVE_CONTIGS_STEP";

    public static final String LIST_DBSNP_MERGED_CONTIGS_STEP = "LIST_DBSNP_MERGED_CONTIGS_STEP";

    public static final String LIST_DBSNP_MULTIMAP_CONTIGS_STEP = "LIST_DBSNP_MULTIMAP_CONTIGS_STEP";

    public static final String DBSNP_ACTIVE_CONTIG_READER = "DBSNP_ACTIVE_CONTIG_READER";

    public static final String DBSNP_MERGED_CONTIG_READER = "DBSNP_MERGED_CONTIG_READER";

    public static final String DBSNP_MULTIMAP_CONTIG_READER = "DBSNP_MULTIMAP_CONTIG_READER";

    public static final String DBSNP_ACTIVE_CONTIG_WRITER = "DBSNP_ACTIVE_CONTIG_WRITER";

    public static final String DBSNP_MERGED_CONTIG_WRITER = "DBSNP_MERGED_CONTIG_WRITER";

    public static final String DBSNP_MULTIMAP_CONTIG_WRITER = "DBSNP_MULTIMAP_CONTIG_WRITER";

    ///////////////// eva beans
    public static final String RELEASE_EVA_MAPPED_ACTIVE_VARIANTS_STEP = "RELEASE_EVA_MAPPED_ACTIVE_VARIANTS_STEP";

    public static final String RELEASE_EVA_MAPPED_MERGED_VARIANTS_STEP = "RELEASE_EVA_MAPPED_MERGED_VARIANTS_STEP";

    public static final String RELEASE_EVA_MAPPED_MERGED_DEPRECATED_VARIANTS_STEP =
            "RELEASE_EVA_MAPPED_MERGED_DEPRECATED_VARIANTS_STEP";

    public static final String RELEASE_EVA_MULTIMAP_VARIANTS_STEP = "RELEASE_EVA_MULTIMAP_VARIANTS_STEP";

    public static final String RELEASE_EVA_MAPPED_DEPRECATED_VARIANTS_STEP =
            "RELEASE_EVA_MAPPED_DEPRECATED_VARIANTS_STEP";

    public static final String EVA_ACCESSIONED_VARIANT_READER = "EVA_ACCESSIONED_VARIANT_READER";

    public static final String EVA_MERGED_VARIANT_READER = "EVA_MERGED_VARIANT_READER";

    public static final String EVA_DEPRECATED_VARIANT_READER = "EVA_DEPRECATED_VARIANT_READER";

    public static final String EVA_MERGED_DEPRECATED_VARIANT_READER = "EVA_MERGED_DEPRECATED_VARIANT_READER";

    public static final String EVA_MULTIMAP_VARIANT_READER = "EVA_MULTIMAP_VARIANT_READER";

    public static final String EVA_RELEASE_WRITER = "EVA_RELEASE_WRITER";

    public static final String EVA_MERGED_RELEASE_WRITER = "EVA_MERGED_RELEASE_WRITER";

    public static final String EVA_MULTIMAP_RELEASE_WRITER = "EVA_MULTIMAP_RELEASE_WRITER";

    public static final String EVA_MERGED_DEPRECATED_RELEASE_WRITER = "EVA_MERGED_DEPRECATED_RELEASE_WRITER";

    public static final String EVA_DEPRECATED_RELEASE_WRITER = "EVA_DEPRECATED_RELEASE_WRITER";

    public static final String LIST_EVA_ACTIVE_CONTIGS_STEP = "LIST_EVA_ACTIVE_CONTIGS_STEP";

    public static final String LIST_EVA_MERGED_CONTIGS_STEP = "LIST_EVA_MERGED_CONTIGS_STEP";

    public static final String LIST_EVA_MULTIMAP_CONTIGS_STEP = "LIST_EVA_MULTIMAP_CONTIGS_STEP";

    public static final String EVA_ACTIVE_CONTIG_READER = "EVA_ACTIVE_CONTIG_READER";

    public static final String EVA_MERGED_CONTIG_READER = "EVA_MERGED_CONTIG_READER";

    public static final String EVA_MULTIMAP_CONTIG_READER = "EVA_MULTIMAP_CONTIG_READER";

    public static final String EVA_ACTIVE_CONTIG_WRITER = "EVA_ACTIVE_CONTIG_WRITER";

    public static final String EVA_MERGED_CONTIG_WRITER = "EVA_MERGED_CONTIG_WRITER";

    public static final String EVA_MULTIMAP_CONTIG_WRITER = "EVA_MULTIMAP_CONTIG_WRITER";
}
