package com.mariamerkova.usermanagement.repository;

import com.mariamerkova.usermanagement.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public List<User> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);

        TypedQuery<User> query = entityManager.createQuery(criteriaQuery);

        return query.getResultList();
    }

    @Override
    public User findById(final Long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        criteriaQuery.where(criteriaBuilder.equal(userRoot.get("idUser"), id));
        TypedQuery<User> query = entityManager.createQuery(criteriaQuery);

        User user = null;
        try {
            user = query.getSingleResult();
        } catch (NoResultException e) {
            // ignore message
        }

        return user;
    }

    @Override
    public void saveUser(final User user) {
        entityManager.persist(user);
    }

    @Override
    public void deleteUser(final User user) {
        entityManager.remove(user);
    }

    @Override
    public void updateUser(final User user) {
        entityManager.merge(user);
    }
}
