package edu.anadolu;

import org.springframework.data.domain.Page;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.SolrResultPage;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class CustomRepoImpl implements CustomRepository{

    @Resource
    private SolrTemplate solrTemplate;

    @Override
    public List<Article> searchFilter(String searchTerm) {
        String[] words = searchTerm.split(" ");

        Criteria conditions = createSearchConditions(words);
        SimpleQuery search = new SimpleQuery(conditions);

        Page results = solrTemplate.queryForPage(search, Article.class);
        return results.getContent();
    }

    private Criteria createSearchConditions(String[] words) {
        Criteria conditions = null;

        for (String word: words) {
            if (conditions == null) {
                conditions = new Criteria("source").is(word);
            }
            else {
                conditions = conditions.or(new Criteria("source").is(word));
            }
        }

        return conditions;
    }
}
