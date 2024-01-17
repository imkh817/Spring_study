package study.spring.domain;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepository {

    private final static Map<Long,User> database = new HashMap<>();
    private static long private Long sequence = 0L;

    public User save(User user){
        user.setUserId(++sequence);
        database.put(user.getUserId(), user);
        return user;
    }

    public User findById(Long userId){
        return database.get(userId);
    }
}
