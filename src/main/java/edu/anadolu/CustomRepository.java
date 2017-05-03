package edu.anadolu;

import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.SolrResultPage;
import org.springframework.data.solr.repository.Facet;
import org.springframework.data.solr.repository.Highlight;
import org.springframework.data.solr.repository.SolrCrudRepository;

import java.util.List;

public interface CustomRepository {

    @Facet(fields = {"source"}, limit = 10)
    @Highlight(prefix = "<b>", postfix = "</b>", fields = {"title", "content"})
    SolrResultPage<Article> searchFilter(String query, String fQuery, Pageable pageable);

}