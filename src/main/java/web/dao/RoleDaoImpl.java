package web.dao;

import org.springframework.stereotype.Repository;
import web.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Role getRole(Long id) {
        return entityManager.getReference(Role.class, id);
    }

    @Override
    public List<Role> getAllRoles() {
        return entityManager.createQuery("from Role").getResultList();
    }
}
