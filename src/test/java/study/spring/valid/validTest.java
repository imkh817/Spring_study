package study.spring.valid;

import org.junit.jupiter.api.Test;
import study.spring.domain.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class validTest {

    @Test
    void beanValidation(){
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        User user = new User();
        user.setName(" "); // 공백
        user.setAge(0); // 0살
        Set<ConstraintViolation<User>> validate = validator.validate(user);
        for (ConstraintViolation<User> userConstraintViolation : validate) {
            System.out.println("userConstraintViolation = " + userConstraintViolation);
            System.out.println("userConstraintViolation.getMessage() = " + userConstraintViolation.getMessage());
        }
    }
}
