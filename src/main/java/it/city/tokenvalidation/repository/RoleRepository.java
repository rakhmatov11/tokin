package it.city.tokenvalidation.repository;

import it.city.tokenvalidation.entity.Role;
import it.city.tokenvalidation.entity.enums.RoleName;
import it.city.tokenvalidation.projection.CustomRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "role", collectionResourceRel = "list", excerptProjection = CustomRole.class)
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRoleName(RoleName roleName);
    Role findAllByRoleName(RoleName roleName);
}
