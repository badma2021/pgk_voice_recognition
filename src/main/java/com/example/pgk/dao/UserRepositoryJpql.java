package com.example.pgk.dao;

import com.example.pgk.model.entity.User;

import java.util.List;

public interface UserRepositoryJpql {
    void save(User user);
    void delete(User user);
    User getUserAndRolesByEmail(String email);
    User getUserByEmail(String email);
    List<User> getAllUserAndRole();
    void appendUserAndUAP(Long user_id,Long uap_id);
    User getUserById(Long user_id);
}
