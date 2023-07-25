package it.city.tokenvalidation.projection;

import it.city.tokenvalidation.entity.Role;
import it.city.tokenvalidation.entity.enums.RoleName;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = Role.class)
public interface CustomRole {
    Integer getId();
    RoleName getRoleName();
}
