package com.example.democoming.config;
import org.springframework.beans.factory.annotation.Value;
import com.example.democoming.config.entity.User;
import com.example.democoming.config.entity.Userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password4j.BcryptPassword4jPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.regex.Pattern;
import java.io.IOException;
@RestController
public class AutherController {
    @Autowired
    private Userservice userservice;
    @Value("${app.auth.min-password-length}")
    private int minPasswordLength;
    @Value("${app.auth.username-pattern}")
    private String usernamePattern;
    @Value("${app.auth.max-username-length}")
    private int maxUsernameLength;
    @Value("${app.auth.userpassword-pattern}")
    private String passwordPattern;
    public void usercheck(User user){
    if(user.getName()==null||user.getName().isBlank())
    {
        throw new IllegalArgumentException("用户名不能为空");
    }
    if(user.getPassword()==null||user.getPassword().isBlank())
    {
        throw new IllegalArgumentException("密码不能为空");
    }

}
   public void registeruser(User user){
        usercheck(user);
        if(user.getPassword().length()<minPasswordLength){
            throw new IllegalArgumentException("密码的最小长度应为"+minPasswordLength+",请重新输入");
        }
        if(!user.getName().matches(usernamePattern)){
            throw new IllegalArgumentException("用户名不符合规则："+usernamePattern);
        }
        if(user.getName().length()>maxUsernameLength){
            throw new IllegalArgumentException("密码的最大长度应为"+maxUsernameLength+",请重新输入");
        }
       if(!user.getPassword().matches(passwordPattern)){
           throw new IllegalArgumentException("密码不符合规范："+passwordPattern);
       }
   }
    @PostMapping("/login")
    public String login(@RequestBody User user) {
    usercheck(user);
        User user_check1 = userservice.findUser(user.getName());
        if (user_check1 == null) {
            return "用户不存在";
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (encoder.matches(
                user.getPassword(), user_check1.getPassword())
        ) {
            return "login success";
        }
        return "密码错误";
    }

    @PostMapping("/register")
    public String register(@RequestBody User user)throws IOException{
    usercheck(user);
    registeruser(user);
        User user_check2 = userservice.findUser(user.getName());
        if (user_check2 == null) {
            userservice.save(user);
            return "register success";
        } else {
            return "用户名已存在";
        }

    }
}
