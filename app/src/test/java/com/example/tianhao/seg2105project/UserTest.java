package com.example.tianhao.seg2105project;

import com.example.tianhao.seg2105project.Model.User;

        import static org.junit.Assert.*;
        import org.junit.Test;

public class UserTest {
    @Test
    public void checkUserName() {
        User user = new User("username", "email", "password", "userType");
        assertEquals("check the name of user", "username", user.getUsername());
    }


    public void checkEmail() {
        User user = new User("username", "email", "password", "userType");
        assertEquals("check the email of user", "email", user.getEmail());
    }

    public void checkPassword(){
        User user = new User("username", "email", "password","userType");
        assertEquals("check the password of user", "password", user.getPassword());
    }

    public void checkuserType(){
        User user = new User("username", "email", "password","userType");
        assertEquals("check the usertype of user", "userType", user.getUserType());
    }
}