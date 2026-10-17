package com.example.democoming.config.entity;
import java.io.EOFException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.HashMap;

@Service
public class Userservice {
    private Map<String, User> users = new HashMap<>();
    @Value("${app.auth.user-file}")
    private String userFile;

    public void save(User user)throws IOException {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!users.containsKey(user.getName())) {
            user.setPassword(encoder.encode(user.getPassword()));
            Path path = Path.of(userFile);
            Files.createDirectories(path.getParent());
            String List=user.getName()+"\t"+user.getPassword()+System.lineSeparator();
            Files.writeString(path,
                    List,
                    StandardOpenOption.
                            CREATE,
                    StandardOpenOption.
                            APPEND);
            users.put(user.getName(), user);
        }
    }

    public User findUser(String username) {
        return users.get(username);
    }
}

