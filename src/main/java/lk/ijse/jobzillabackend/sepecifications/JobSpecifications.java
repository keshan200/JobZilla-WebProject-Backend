package lk.ijse.jobzillabackend.sepecifications;

import lk.ijse.jobzillabackend.entity.Job;
import org.springframework.data.jpa.domain.Specification;

public class JobSpecifications {
    public static Specification<Job> hasTitle(String title) {
        return (root, query, criteriaBuilder) ->
            title == null ? null : criteriaBuilder.like(root.get("title"), "%" + title + "%");
    }

    public static Specification<Job> hasLocation(String location) {
        return (root, query, criteriaBuilder) ->
            location == null ? null : criteriaBuilder.like(root.get("location"), "%" + location + "%");
    }

    public static Specification<Job> hasEmploymentType(String employmentType) {
        return (root, query, criteriaBuilder) ->
            employmentType == null ? null : criteriaBuilder.equal(root.get("employmentType"), employmentType);
    }

    public static Specification<Job> hasSalaryGreaterThan(double salary) {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.greaterThanOrEqualTo(root.get("salary"), salary);
    }
}
