package edu.anadolu;

import com.sun.istack.internal.Nullable;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.SolrCallback;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.SolrResultPage;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

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