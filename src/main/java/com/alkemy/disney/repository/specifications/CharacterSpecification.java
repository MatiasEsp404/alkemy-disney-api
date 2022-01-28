package com.alkemy.disney.repository.specifications;

import com.alkemy.disney.model.CharacterModel;
import com.alkemy.disney.model.MovieModel;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class CharacterSpecification {

    public Specification<CharacterModel> getByFilters(String name, Short age, Set<Long> movies) {
        // Lambda expression
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasLength(name)) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),
                        "%" + name.toLowerCase() + "%"));
            }
            if (age != null){
                predicates.add(criteriaBuilder.equal(root.get("age"), age));
            }
            if (!CollectionUtils.isEmpty(movies)){
                Join<MovieModel, CharacterModel> join = root.join("movies", JoinType.INNER);
                Expression<String> movieId = join.get("id");
                predicates.add(movieId.in(movies));
            }
            // Remove duplicates
            query.distinct(true);
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
