package edu.anadolu;

import edu.anadolu.config.SolrContext;
import edu.anadolu.config.WebContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@Import({WebContext.class, SolrContext.class})
public class SolrWebApplication extends WebMvcConfigurerAdapter {

    // Main
    public static void main(String[] args) {
        SpringApplication.run(SolrWebApplication.class, args);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/");
    }

}
