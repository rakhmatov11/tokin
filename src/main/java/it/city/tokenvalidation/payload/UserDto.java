package it.city.tokenvalidation.payload;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private UUID id;

    @Column(nullable = false)
    private String firstName;

    private String lastName;

    private String username;

    @Column(nullable = false)
    private String phoneNumber;

    private String email;

    private String password;

}
