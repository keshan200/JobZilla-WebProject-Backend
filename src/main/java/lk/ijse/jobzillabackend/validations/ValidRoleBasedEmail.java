package lk.ijse.jobzillabackend.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RoleBasedEmailValidator.class)
public @interface ValidRoleBasedEmail {
    String message() default "Invalid email domain for the specified role";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
