package com.alkemy.disney.repository;

import com.alkemy.disney.model.CharacterModel;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface CharacterRepository extends JpaRepository<CharacterModel, Long>, JpaSpecificationExecutor<CharacterModel> {

    ArrayList<CharacterModel> findByName(String name);

    List<CharacterModel> findAll(Specification<CharacterModel> spec);
}