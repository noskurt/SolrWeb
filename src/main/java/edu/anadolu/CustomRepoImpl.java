package edu.anadolu;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.core.query.result.SolrResultPage;
import org.springframework.stereotype.Repository;
import org.thymeleaf.doctype.translation.DocTypeTranslation;
import org.thymeleaf.dom.DocType;

import javax.annotation.Resource;

@Repository
public class CustomRepoImpl implements CustomRepository {

    @Resource
    private SolrTemplate solrTemplate;

    @Override
    public SolrResultPage<Article> searchFilter(String query, String fQuery, Pageable pageable) {
        System.out.println(fQuery);

        String[] words = fQuery.split(",");

        Criteria conditions = createSearchConditions(words, query);
        SimpleQuery search = new SimpleQuery(conditions);

        search.setPageRequest(pageable);

        Page results = solrTemplate.queryForPage(search, Article.class);

        return new SolrResultPage<Article>(results.getContent(), pageable, results.getTotalElements(), Float.MAX_VALUE);
    }

    @Override
    public FacetPage<Article> getFacetPage(String query) {
        FacetQuery facetQuery = new SimpleFacetQuery(new Criteria("title").is(query));

        facetQuery.setFacetOptions(new FacetOptions("source"));

        return solrTemplate.queryForFacetPage(facetQuery, Article.class);
    }

    @Override
    public HighlightPage<Article> getHighlightPage(String query) {

        HighlightQuery highlightQuery = new SimpleHighlightQuery(new Criteria("title").is(query));

        HighlightOptions hlOptions = new HighlightOptions();
        hlOptions.addField("title");
        hlOptions.addField("content");
        hlOptions.setSimplePrefix("<b>");
        hlOptions.setSimplePostfix("</b>");
        highlightQuery.setHighlightOptions(hlOptions);

        return solrTemplate.queryForHighlightPage(highlightQuery,Article.class);
    }

    private Criteria createSearchConditions(String[] words, String query) {
        Criteria title = new Criteria("title").is(query);
        Criteria src = null;

        for (String word : words) {
            if (src == null) {
                src = new Criteria("source").is(word);
            } else {
                src = src.or(new Criteria("source").is(word));
            }
        }

        System.out.println("HEYY: "+title.and(src));

        return title.and(src);
    }
}