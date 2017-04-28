package edu.anadolu;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.SolrResultPage;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class TodoDocRepoImpl implements CustomTodoDocRepo {

    @Resource
    private SolrTemplate solrTemplate;

    @Override
    public Page<Article> search(String searchTerm, Pageable page) {

        String[] words = searchTerm.split(" ");

        Criteria criteria = createSearchConditions(words);
        SimpleQuery search = new SimpleQuery(criteria);

//        List<Article> results = solrTemplate.queryForPage(search, Article.class);
//
//        return new SolrResultPage<Article>(results, page);
        return null;
    }

//    private Page<Article> execute(final SimpleQuery query, final Pageable page) {
//        final QueryResponse resp = solrTemplate.getSolrClient().query(query);
//        final List<Article> beans = solrTemplate.convertQueryResponseToBeans(resp, Article.class);
//        return new SolrResultPage<Article>(beans, page, resp.getResults().getNumFound());
//    }

    private Criteria createSearchConditions(String[] words) {
        Criteria criteria = null;

        for (String word : words) {
            if(criteria == null) {
                criteria = new Criteria("source").is(word);
            } else {
                criteria = criteria.or(new Criteria("source").is(word));
            }
        }

        return criteria;
    }
}