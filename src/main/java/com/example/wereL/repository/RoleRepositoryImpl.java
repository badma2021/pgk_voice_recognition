package com.example.wereL.repository;


import com.example.wereL.dao.RoleRepositoryJpql;
import com.example.wereL.model.entity.Role;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class RoleRepositoryImpl implements RoleRepositoryJpql {
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void save(Role role) {
        em.persist(role);
    }

    @Override
    @Transactional
    public void delete(Role role) {
        em.remove(em.contains(role) ? role : em.merge(role));
    }

    @Override
    @Transactional(readOnly = true)
    public Role getRoleAndUsersByName(String name) {
        try {
            final Role role = em.createQuery("SELECT r FROM Role r LEFT JOIN FETCH r.users " +
                            "WHERE LOWER(r.name) = ?1", Role.class)
                    .setParameter(1,name.toLowerCase())
                    .getSingleResult();
            return role;
        } catch (NoResultException n){
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Role getRoleByName(String name) {
        try{
            final Role role = em.createQuery("SELECT r FROM Role r WHERE LOWER(r.name) = ?1", Role.class)
                    .setParameter(1,name.toLowerCase())
                    .getSingleResult();
            return role;
        } catch (NoResultException n){
            return null;
        }
    }
}
