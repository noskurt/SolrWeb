package edu.anadolu;

import org.apache.solr.client.solrj.SolrServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;


@Controller
@Component
@Scope("prototype")
public class SearchController {

    @Resource
    private ArticleRepository articleRepository;

    @RequestMapping("/search")
    public String search(@RequestParam(value = "q", required = false) String query,
                         @PageableDefault(page = 0, size = 10) Pageable pageable,
                         @RequestParam(value = "fq", required = false) String filter,
                         Model model) {

        Query query1 = new SimpleQuery(new Criteria("source").is("MyInforms"));

        model.addAttribute("filter", filter);
        model.addAttribute("page", articleRepository.findByTitle(query, pageable));
        model.addAttribute("pageable", pageable);
        model.addAttribute("query", query);
        return "search";
    }

    @Autowired
    public void setNewsService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Autowired
    public SearchController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }
}