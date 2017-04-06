package edu.anadolu;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.repository.Facet;
import org.springframework.data.solr.repository.Highlight;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

import java.util.Collection;


public interface ArticleRepository extends SolrCrudRepository<Article, String> {

    @Highlight(prefix = "<b>", postfix = "</b>")
    @Query(fields = {"title", "content"}, defaultOperator = org.springframework.data.solr.core.query.Query.Operator.OR)
    HighlightPage<Article> findByTitleIn(Collection<String> names, Pageable page);

    Page<Article> findByTitle(String title, Pageable page);

    //Execute faceted search
    //Query will be "q=name:<name>&facet=true&facet.field=cat&facet.limit=20&start=<page.number>&rows=<page.size>"
    @Query(value = "name:?0")
    @Facet(fields = { "source" }, limit=20)
    FacetPage<Article> findByArticleAndFacetOnSource(String title, Pageable page);
}
