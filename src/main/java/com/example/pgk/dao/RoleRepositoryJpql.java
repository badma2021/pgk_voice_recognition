package com.example.pgk.dao;


import com.example.pgk.model.entity.Role;

public interface RoleRepositoryJpql {
    void save(Role role);
    void delete(Role role);
    Role getRoleAndUsersByName(String name);
    Role getRoleByName(String name);
}
