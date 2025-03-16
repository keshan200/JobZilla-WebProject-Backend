package lk.ijse.jobzillabackend.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lk.ijse.jobzillabackend.entity.User;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class RoleBasedEmailValidator implements ConstraintValidator<ValidRoleBasedEmail, User> {

    private final List<String> workDomains = Arrays.asList("@business.com","@outlook.com");
    private final List<String> personalDomains = Arrays.asList("@gmail.com", "@yahoo.com");




    @Override
    public boolean isValid(User user, ConstraintValidatorContext context) {
        if (user == null) {
            System.out.println("Validation failed: User is null");
            return false;
        }

        if (user.getEmail() == null || user.getRole() == null) {
            System.out.println("Validation failed: Email or Role is null");
            return false;
        }

        String email = user.getEmail().toLowerCase();
        String role = String.valueOf(user.getRole());

        System.out.println("Validating email: " + email + ", role: " + role);


        if ("EMPLOYER".equalsIgnoreCase(role)) {
            boolean isValidEmployer = workDomains.stream().anyMatch(email::endsWith);
            System.out.println("Employer email validation result: " + isValidEmployer);
            return isValidEmployer;
        }

        if ("CANDIDATE".equalsIgnoreCase(role)) {
            boolean isValidCandidate = personalDomains.stream().anyMatch(email::endsWith);
            System.out.println("Candidate email validation result: " + isValidCandidate);
            return isValidCandidate;
        }

        System.out.println("Validation failed: Role not matched");
        return false;
    }


}
