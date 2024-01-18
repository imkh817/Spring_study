package study.spring.domain;

import lombok.Data;

@Data
public class User {

    private Long userId;
    private String name;
    private Integer age;

    public User(){

    }
    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}
