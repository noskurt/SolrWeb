package edu.anadolu;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomTodoDocRepo {

    Page<Article> search(String searchTerm, Pageable page);

}