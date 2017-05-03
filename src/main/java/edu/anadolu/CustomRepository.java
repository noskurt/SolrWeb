package edu.anadolu;

import org.springframework.data.solr.core.query.result.SolrResultPage;

import java.util.List;

public interface CustomRepository {

    List<Article> searchFilter(String searchTerm);

}
