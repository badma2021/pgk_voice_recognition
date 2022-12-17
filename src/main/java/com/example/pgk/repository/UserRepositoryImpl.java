package com.example.pgk.repository;

import com.example.pgk.dao.UserRepositoryJpql;
import com.example.pgk.model.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepositoryJpql {
    @PersistenceContext
    private EntityManager em;
    @Override
    @Transactional
    public void save(User user) {
        em.persist(user);
    }

    @Override
    @Transactional
    public void delete(User user) {
        em.remove(em.contains(user) ? user : em.merge(user));
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserAndRolesByEmail(String email) {
        try {
            final User user = em.createQuery("SELECT u FROM User u LEFT JOIN FETCH u.roles AS ur " +
                            "WHERE LOWER(u.email) = ?1 ", User.class)
                    .setParameter(1,email)
                    .getSingleResult();
            return user;
        } catch (NoResultException n){
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        try {
            final User user = em.createQuery("SELECT u FROM User u WHERE LOWER(u.email) = ?1", User.class)
                    .setParameter(1,email.toLowerCase())
                    .getSingleResult();
            return user;
        } catch (NoResultException e){
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUserAndRole() {
        try {
            final List<User> users = em.createQuery("SELECT u FROM User u LEFT JOIN FETCH u.roles", User.class)
                    .getResultList();
            return users;
        } catch (NoResultException n){
            return null;
        }
    }

    @Override
    @Transactional
    public void appendUserAndUAP(Long user_id, Long uap_id) {
        em.createNativeQuery("UPDATE user_and_project SET user_id = ?1 WHERE id = ?2")
                .setParameter(1,user_id)
                .setParameter(2,uap_id)
                .executeUpdate();
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long user_id) {
        try{
            final User user = em.createQuery("SELECT u FROM User u LEFT JOIN FETCH u.roles " +
                            "WHERE u.id = ?1",User.class)
                    .setParameter(1,user_id)
                    .getSingleResult();
            return user;
        } catch (NoResultException n){
            return null;
        }
    }
}
