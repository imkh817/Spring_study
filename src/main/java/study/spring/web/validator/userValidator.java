package study.spring.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import study.spring.domain.User;
@Component
public class userValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        // clazz로 들어오는 클래스가 User클래스와 타입이 맞는지 확인
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User)target;

        if(!StringUtils.hasText(user.getName())){
            errors.rejectValue("name","required.user.name");
        }
        if(user.getAge() == null || user.getAge() < 1 || user.getAge() >100){
            errors.rejectValue("age","range.user.age",new Object[]{1,100},null);
        }

        // 특정 필드가 아닌 복합룰
        // -> new FieldError가 아닌 new ObjectError를 쓴다.
        if(!StringUtils.hasText(user.getName()) && user.getAge() == null){
            // ObjectName, Default Message
            errors.reject("required.user.nameAge");
        }
    }
}
