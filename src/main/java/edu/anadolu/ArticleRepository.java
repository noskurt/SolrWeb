package edu.anadolu;

import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.core.query.result.SolrResultPage;
import org.springframework.data.solr.repository.Facet;
import org.springframework.data.solr.repository.Highlight;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

public interface ArticleRepository extends SolrCrudRepository<Article, String> {

    @Highlight(prefix = "<b>", postfix = "</b>", fields = {"title", "content"})
    @Query(fields = {"title", "content"}, defaultOperator = org.springframework.data.solr.core.query.Query.Operator.OR)
    HighlightPage<Article> findByContent(String content, Pageable page);

    @Facet(fields = {"source"}, limit = 10)
    @Highlight(prefix = "<b>", postfix = "</b>", fields = {"title", "content"})
//    @Query(filters = "source:\"\"")
    SolrResultPage<Article> findByTitle(String query, Pageable page);

    //Execute faceted search
    //Query will be "q=content:<name>&facet=true&facet.field=source&facet.limit=20&start=<page.number>&rows=<page.size>"
    @Query(value = "name:?0")
    @Facet(fields = {"source"}, limit = 20)
    FacetPage<Article> findByContentAndFacetOnSource(String title, Pageable page);
}