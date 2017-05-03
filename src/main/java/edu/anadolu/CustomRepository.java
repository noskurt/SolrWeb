package edu.anadolu;

import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.SolrResultPage;

import java.util.List;

public interface CustomRepository {

    List<Article> searchFilter(String query, String fQuery, Pageable pageable);

}
