package edu.anadolu;

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
public class CustomRepoImpl implements CustomRepository{

    @Resource
    private SolrTemplate solrTemplate;

    @Override
    public List<Article> searchFilter(String query, String fQuery, Pageable pageable){
        String[] words = fQuery.split(" ");

        Criteria conditions = createSearchConditions(words, query);
        SimpleQuery search = new SimpleQuery(conditions);

        System.out.println("HEYYY: "+search.getCriteria().toString());

        search.setPageRequest(pageable);

        Page results = solrTemplate.queryForPage(search, Article.class);

        return results.getContent();
    }

    private Criteria createSearchConditions(String[] words, String query) {
        Criteria conditions = null;

        for (String word: words) {
            if (conditions == null) {
                conditions = new Criteria("source").is(word);
            }
            else {
                conditions = conditions.or(new Criteria("source").is(word));
            }
        }

        conditions = conditions.and(new Criteria("title").contains(query))
                .or(new Criteria("content").contains(query));

        return conditions;
    }
}
