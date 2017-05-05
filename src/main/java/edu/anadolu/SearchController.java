package edu.anadolu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Pageable;
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

    @Resource
    private CustomRepository customRepository;

    @RequestMapping("/search")
    public String search(@RequestParam(value = "q", required = false) String query,
                         @PageableDefault(page = 0, size = 10) Pageable pageable,
                         Model model) {

        model.addAttribute("page", articleRepository.findByTitle(query, pageable));
        model.addAttribute("pageable", pageable);
        model.addAttribute("query", query);

        return "search";
    }

    @RequestMapping("/search/filter")
    public String filtered(@RequestParam(value = "q", required = false) String query,
                           @RequestParam(value = "fq", required = false) String filterQuery,
                           @PageableDefault(page = 0, size = 10) Pageable pageable,
                           Model model) {

        if (filterQuery == null) {
            return search(query, pageable, model);
        } else {
            model.addAttribute("page", customRepository.searchFilter(query, filterQuery, pageable));
            model.addAttribute("faceted", customRepository.getFacetPage(query, filterQuery));
            model.addAttribute("highlighted", customRepository.getHighlightPage(query, filterQuery));
            model.addAttribute("pageable", pageable);
            model.addAttribute("query", query);
            model.addAttribute("fquery", filterQuery);

            return "filtered";
        }
    }

    @Autowired
    public void setNewsService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Autowired
    public SearchController(ArticleRepository articleRepository, CustomRepository customRepository) {
        this.articleRepository = articleRepository;
        this.customRepository = customRepository;
    }
}