package edu.anadolu;

import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.core.query.result.SolrResultPage;
import org.springframework.data.solr.repository.Facet;
import org.springframework.data.solr.repository.Highlight;

public interface CustomRepository {

    @Facet(fields = {"source"}, limit = 10)
    @Highlight(prefix = "<b>", postfix = "</b>", fields = {"title", "content"})
    SolrResultPage<Article> searchFilter(String query, String fQuery, Pageable pageable);

    @Facet(fields = {"source"}, limit = 10)
    FacetPage<Article> getFacetPage(String query, String filterQuery, Pageable pageable);

    @Highlight(prefix = "<b>", postfix = "</b>", fields = {"title", "content"})
    HighlightPage<Article> getHighlightPage(String query, String filterQuery, Pageable pageable);

}