package com.example.wereL.dao;


import com.example.wereL.model.entity.Role;

public interface RoleRepositoryJpql {
    void save(Role role);
    void delete(Role role);
    Role getRoleAndUsersByName(String name);
    Role getRoleByName(String name);
}
