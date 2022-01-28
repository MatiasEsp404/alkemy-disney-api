package com.alkemy.disney.repository;

import com.alkemy.disney.model.MovieModel;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<MovieModel, Long>, JpaSpecificationExecutor<MovieModel> {
    ArrayList<MovieModel> findByTitle(String title);
    List<MovieModel> findAll(Specification<MovieModel> spec);
}
