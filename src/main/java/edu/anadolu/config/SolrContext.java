package edu.anadolu.config;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.solr.core.SolrOperations;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

import javax.annotation.Resource;

@Configuration
@EnableSolrRepositories(basePackages = {"edu.anadolu"}, multicoreSupport = false)
public class SolrContext {

    static final String SOLR_HOST = "solr.host";


    private @Resource Environment environment;

    @Bean
    public SolrClient solrClient() {
        String solrHost = environment.getRequiredProperty(SOLR_HOST);
        return new HttpSolrClient(solrHost);
    }

    @Bean
    public SolrOperations solrTemplate() {
        return new SolrTemplate(solrClient());
    }
}