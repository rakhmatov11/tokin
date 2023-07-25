package it.city.tokenvalidation.payload;

import it.city.tokenvalidation.entity.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoleDto {
    private RoleName roleName;
}
