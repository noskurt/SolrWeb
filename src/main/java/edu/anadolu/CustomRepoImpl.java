package edu.anadolu;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.core.query.result.HighlightEntry;
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
    public SolrResultPage<Article> searchFilter(String query, String filterQuery, Pageable pageable) {
        String[] words = filterQuery.split(",");

        Criteria conditions = createSearchConditions(words, query);
        SimpleQuery search = new SimpleQuery(conditions);

        search.setPageRequest(pageable);

        Page results = solrTemplate.queryForPage(search, Article.class);

        return new SolrResultPage<Article>(results.getContent(), pageable, results.getTotalElements(), Float.MAX_VALUE);
    }

    @Override
    public FacetPage<Article> getFacetPage(String query, String filterQuery) {
        FacetQuery facetQuery = new SimpleFacetQuery(new Criteria("title").is(query));

        facetQuery.setFacetOptions(new FacetOptions("source"));

        return solrTemplate.queryForFacetPage(facetQuery, Article.class);
    }

    @Override
    public HighlightPage<Article> getHighlightPage(String query, String filterQuery) {

        String[] words = filterQuery.split(",");

        Criteria conditions = createSearchConditions(words, query);

        HighlightQuery highlightQuery = new SimpleHighlightQuery(conditions);

        HighlightOptions hlOptions = new HighlightOptions();
        hlOptions.addField("title");
        hlOptions.addField("content");
        hlOptions.setSimplePrefix("<b>");
        hlOptions.setSimplePostfix("</b>");
        highlightQuery.setHighlightOptions(hlOptions);

//        return processHighlight(solrTemplate.queryForHighlightPage(highlightQuery, Article.class));

//        System.out.println("HEYY: "+solrTemplate.queryForHighlightPage
//                (highlightQuery,Article.class).getHighlighted().get(0).getHighlights().get(0).getSnipplets());

        return solrTemplate.queryForHighlightPage(highlightQuery, Article.class);
    }

//    public HighlightPage<Article> processHighlight(HighlightPage<Article> highlightPage){
//        int i = 0;
//        for (HighlightEntry<Article> article: highlightPage.getHighlighted()) {
//            for (HighlightEntry.Highlight highlight: article.getHighlights()) {
//                for (String snippet: highlight.getSnipplets()){
//                    if(highlight.getField().getName().equals("content")){
//                        highlightPage.getContent().get(i).setContent(snippet);
//                    }
//                }
//            }
//            i++;
//        }
//        return highlightPage;
//    }

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

        return title.and(src);
    }
}