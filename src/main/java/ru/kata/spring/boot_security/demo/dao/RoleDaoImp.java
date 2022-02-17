package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class RoleDaoImp implements RoleDao {

    private EntityManager entityManager;

    @Override
    public Set<Role> findRoles(List<Long> roles) {
        TypedQuery<Role> query = entityManager.createQuery("select r from Role r where r.id in :role", Role.class);
        return new HashSet<>(query.getResultList());
    }

    @Override
    public List<Role> getAllRoles() {
        return entityManager.createQuery("select r from Role r").getResultList();
    }
}
