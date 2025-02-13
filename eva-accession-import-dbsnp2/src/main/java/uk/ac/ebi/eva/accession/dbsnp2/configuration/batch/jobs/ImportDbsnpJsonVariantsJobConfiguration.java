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
package uk.ac.ebi.eva.accession.dbsnp2.configuration.batch.jobs;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static uk.ac.ebi.eva.accession.dbsnp2.configuration.BeanNames.IMPORT_DBSNP_JSON_VARIANTS_FLOW;
import static uk.ac.ebi.eva.accession.dbsnp2.configuration.BeanNames.IMPORT_DBSNP_JSON_VARIANTS_JOB;

/**
 * Configuration to run a job that imports a dbSNP JSON file.
 */
@Configuration
@EnableBatchProcessing
public class ImportDbsnpJsonVariantsJobConfiguration {

    @Autowired
    @Qualifier(IMPORT_DBSNP_JSON_VARIANTS_FLOW)
    private Flow importFlow;

    @Bean(IMPORT_DBSNP_JSON_VARIANTS_JOB)
    public Job importDbsnpJsonVariantsJob(JobBuilderFactory jobBuilderFactory) {
        return jobBuilderFactory.get(IMPORT_DBSNP_JSON_VARIANTS_JOB)
                .incrementer(new RunIdIncrementer())
                .start(importFlow)
                .end()
                .build();
    }
}
