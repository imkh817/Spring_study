package study.spring.domain;

import lombok.Data;

@Data
public class User {

    private Long userId;
    private String name;
    private String age;

    public User(String name, String age) {
        this.name = name;
        this.age = age;
    }
}
