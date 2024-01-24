package study.spring.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserEditForm {

    @NotNull
    private Long userId;

    @NotBlank
    private String name;

    @Range(min = 1, max = 100)
    private Integer age;

    public UserEditForm(){

    }
}
