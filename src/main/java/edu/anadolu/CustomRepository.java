package edu.anadolu;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomRepository {

    List<Article> searchFilter(String query, String fQuery, Pageable pageable);

}
