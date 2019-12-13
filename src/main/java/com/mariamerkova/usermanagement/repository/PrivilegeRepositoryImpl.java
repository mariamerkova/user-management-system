package com.mariamerkova.usermanagement.repository;

import com.mariamerkova.usermanagement.model.Privilege;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class PrivilegeRepositoryImpl implements PrivilegeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }


    @Override
    public List<Privilege> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Privilege> criteriaQuery = criteriaBuilder.createQuery(Privilege.class);
        Root<Privilege> privilegeRoot = criteriaQuery.from(Privilege.class);

        TypedQuery<Privilege> query = entityManager.createQuery(criteriaQuery);

        return query.getResultList();
    }



}
