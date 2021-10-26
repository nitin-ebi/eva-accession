package uk.ac.ebi.eva.accession.pipeline.configuration.batch.listeners;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.eva.accession.pipeline.batch.listeners.AccessioningCounts;
import uk.ac.ebi.eva.accession.pipeline.batch.listeners.AccessioningProgressListener;
import uk.ac.ebi.eva.accession.pipeline.parameters.CountParameters;
import uk.ac.ebi.eva.accession.pipeline.parameters.InputParameters;

import static uk.ac.ebi.eva.accession.pipeline.configuration.BeanNames.PROGRESS_LISTENER;

@Configuration
public class ListenersConfiguration {

    @Bean(PROGRESS_LISTENER)
    public AccessioningProgressListener accessioningProgressListener(InputParameters parameters,
                                                                     AccessioningCounts accessioningCounts,
                                                                     CountParameters countParameters,
                                                                     RestTemplate restTemplate) {
        return new AccessioningProgressListener(parameters, accessioningCounts, restTemplate, countParameters.getUrl());
    }

    @Bean
    public AccessioningCounts importCounts() {
        return new AccessioningCounts();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder, CountParameters countParameters) {
        return builder.basicAuthentication(countParameters.getUserName(), countParameters.getPassword()).build();
    }
}
