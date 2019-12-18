package com.mariamerkova.usermanagement.repository;

import com.mariamerkova.usermanagement.model.Role;
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
public class RoleRepositoryImpl implements RoleRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public List<Role> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Role> criteriaQuery = criteriaBuilder.createQuery(Role.class);
        Root<Role> roleRoot = criteriaQuery.from(Role.class);

        TypedQuery<Role> query = entityManager.createQuery(criteriaQuery);

        return query.getResultList();
    }

    @Override
    public void save(final Role role) {
        entityManager.persist(role);
    }

    @Override
    public Role findRoleByName(final String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Role> criteriaQuery = criteriaBuilder.createQuery(Role.class);
        Root<Role> roleRoot = criteriaQuery.from(Role.class);
        criteriaQuery.where(criteriaBuilder.equal(roleRoot.get("name"), name));
        TypedQuery<Role> query = entityManager.createQuery(criteriaQuery);

        Role role = null;
        try {
            role = query.getSingleResult();
        } catch (NoResultException e) {

        }
        return role;
    }

    @Override
    public void update(final Role role) {
        entityManager.merge(role);
    }

    @Override
    public Role findById(final Long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Role> criteriaQuery = criteriaBuilder.createQuery(Role.class);
        Root<Role> roleRoot = criteriaQuery.from(Role.class);
        criteriaQuery.where(criteriaBuilder.equal(roleRoot.get("id"), id));
        TypedQuery<Role> query = entityManager.createQuery(criteriaQuery);

        Role role = null;
        try {
            role = query.getSingleResult();
        } catch (NoResultException e) {

        }
        return role;
    }

    @Override
    public void delete(final Role role) {
        entityManager.remove(role);
    }


}
