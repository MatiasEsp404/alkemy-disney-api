package com.alkemy.disney.repository.specifications;

import com.alkemy.disney.model.GenreModel;
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
public class MovieSpecification {

    public Specification<MovieModel> getByFilters(String title, Set<Long> genres, String order) {
        // Lambda expression
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasLength(title)) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("title")),
                        "%" + title.toLowerCase() + "%"));
            }
            if (!CollectionUtils.isEmpty(genres)) {
                Join<GenreModel, MovieModel> join = root.join("genres", JoinType.INNER);
                Expression<String> genreId = join.get("id");
                predicates.add(genreId.in(genres));
            }
            // Remove duplicates
            query.distinct(true);
            // Order resolver
            String orderByField = "creationDate";
            // Ternary operator
            query.orderBy(
                    order.equalsIgnoreCase("asc") ?
                            criteriaBuilder.asc(root.get(orderByField)) :
                            criteriaBuilder.desc(root.get(orderByField)));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
