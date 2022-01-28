package com.alkemy.disney.repository;

import com.alkemy.disney.model.GenreModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface GenreRepository extends JpaRepository<GenreModel, Long> {
    ArrayList<GenreModel> findByName(String name);
}