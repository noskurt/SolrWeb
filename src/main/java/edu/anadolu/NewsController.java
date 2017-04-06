package edu.anadolu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@Component
@RequestMapping("/")
public class NewsController {

    @Resource
    private ArticleRepository articleRepository;

    @Autowired
    public NewsController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @RequestMapping(value = "/news/{id}", method = RequestMethod.GET)
    public String search(@PathVariable("id") String id, Model model) {
        model.addAttribute("article", articleRepository.findOne(id));
        return "article";
    }

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseStatus(value = HttpStatus.BANDWIDTH_LIMIT_EXCEEDED)
    public String error() {
        return "error";
    }
}