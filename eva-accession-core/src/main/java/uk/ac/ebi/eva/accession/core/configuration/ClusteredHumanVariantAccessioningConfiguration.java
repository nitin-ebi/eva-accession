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
package uk.ac.ebi.eva.accession.core.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import uk.ac.ebi.ampt2d.commons.accession.autoconfigure.EnableSpringDataContiguousIdService;
import uk.ac.ebi.ampt2d.commons.accession.persistence.jpa.monotonic.service.ContiguousIdBlockService;
import uk.ac.ebi.eva.accession.core.ClusteredVariantAccessioningService;
import uk.ac.ebi.eva.accession.core.IClusteredVariant;
import uk.ac.ebi.eva.accession.core.persistence.DbsnpClusteredVariantInactiveEntity;
import uk.ac.ebi.eva.accession.core.persistence.DbsnpClusteredVariantOperationEntity;
import uk.ac.ebi.eva.accession.core.persistence.DbsnpClusteredVariantOperationRepository;
import uk.ac.ebi.eva.accession.core.persistence.DbsnpMonotonicAccessionGenerator;
import uk.ac.ebi.eva.accession.core.repositoryHuman.DbsnpClusteredHumanVariantAccessioningDatabaseService;
import uk.ac.ebi.eva.accession.core.repositoryHuman.DbsnpClusteredHumanVariantOperationAccessioningService;
import uk.ac.ebi.eva.accession.core.repositoryHuman.DbsnpClusteredHumanVariantOperationRepository;
import uk.ac.ebi.eva.accession.core.repositoryHuman.DbsnpHumanClusteredVariantAccessionRepository;
import uk.ac.ebi.eva.accession.core.service.DbsnpClusteredHumanVariantAccessioningService;
import uk.ac.ebi.eva.accession.core.service.DbsnpClusteredVariantInactiveService;

@Configuration
@EnableSpringDataContiguousIdService
@Import({MongoHumanConfiguration.class})
public class ClusteredHumanVariantAccessioningConfiguration {

    @Autowired
    private DbsnpHumanClusteredVariantAccessionRepository dbsnpHumanRepository;

    @Autowired
    private DbsnpClusteredHumanVariantOperationRepository dbsnpClusteredHumanVariantOperationRepository;

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private ContiguousIdBlockService service;

    @Autowired
    private DbsnpClusteredVariantInactiveService dbsnpInactiveService;

    @Bean("humanActiveService")
    public ClusteredVariantAccessioningService dbsnpClusteredHumanActiveVariantAccessioningService() {
        return new ClusteredVariantAccessioningService(dbsnpClusteredVariantAccessionGenerator(),
                                                       dbsnpClusteredVariantAccessioningDatabaseService());
    }

    private DbsnpMonotonicAccessionGenerator<IClusteredVariant> dbsnpClusteredVariantAccessionGenerator() {
        ApplicationProperties properties = applicationProperties;
        return new DbsnpMonotonicAccessionGenerator<>(properties.getClustered().getCategoryId(),
                                                      properties.getInstanceId(), service);
    }

    private DbsnpClusteredHumanVariantAccessioningDatabaseService dbsnpClusteredVariantAccessioningDatabaseService() {
        return new DbsnpClusteredHumanVariantAccessioningDatabaseService(dbsnpHumanRepository, dbsnpInactiveService);
    }

    @Bean("humanOperationsService")
    public DbsnpClusteredHumanVariantOperationAccessioningService dbsnpClusteredHumanVariantOperationAccessioningService() {
        return new DbsnpClusteredHumanVariantOperationAccessioningService(dbsnpClusteredHumanVariantOperationRepository);
    }

    @Bean("humanService")
    public DbsnpClusteredHumanVariantAccessioningService dbsnpClusteredHumanVariantAccessioningService() {
        return new DbsnpClusteredHumanVariantAccessioningService(dbsnpClusteredHumanActiveVariantAccessioningService(),
                dbsnpClusteredHumanVariantOperationAccessioningService());
    }

    @Bean
    public DbsnpClusteredHumanVariantOperationRepository dbsnpClusteredVariantOperationRepository() {
        return dbsnpClusteredHumanVariantOperationRepository;
    }

    @Bean
    public DbsnpClusteredVariantInactiveService dbsnpClusteredVariantInactiveService() {
        return new DbsnpClusteredVariantInactiveService(dbsnpClusteredHumanVariantOperationRepository,
                DbsnpClusteredVariantInactiveEntity::new,
                DbsnpClusteredVariantOperationEntity::new);
    }
}
