package pl.patrykdepka.basicspringmvcapp.appuser;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;

public class AppUserSpecification {

    private AppUserSpecification() {
    }

    public static Specification<AppUser> bySearch(String searchQuery) {
        return (root, query, criteriaBuilder) -> {
            Predicate firstName = criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), "%" + searchQuery.toLowerCase() + "%");
            Predicate lastName = criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%" + searchQuery.toLowerCase() + "%");
            Predicate email = criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + searchQuery.toLowerCase() + "%");
            return criteriaBuilder.or(firstName, lastName, email);
        };
    }

    public static Specification<AppUser> bySearch(String searchWord, String searchWord2) {
        return (root, query, criteriaBuilder) -> {
            Predicate firstName = criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), "%" + searchWord.toLowerCase() + "%");
            Predicate lastName = criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%" + searchWord2.toLowerCase() + "%");
            return criteriaBuilder.and(firstName, lastName);
        };
    }
}
