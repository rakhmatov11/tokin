package it.city.tokenvalidation.service;

import it.city.tokenvalidation.entity.Role;
import it.city.tokenvalidation.entity.Token;
import it.city.tokenvalidation.entity.User;
import it.city.tokenvalidation.entity.enums.RoleName;
import it.city.tokenvalidation.entity.enums.TokenType;
import it.city.tokenvalidation.payload.*;
import it.city.tokenvalidation.repository.AuthRepository;
import it.city.tokenvalidation.repository.RoleRepository;
import it.city.tokenvalidation.repository.TokenRepository;
import it.city.tokenvalidation.security.JwtService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.*;

@Service
public class AuthService {
    final AuthRepository authRepository;
    final PasswordEncoder passwordEncoder;
    final RoleRepository roleRepository;
    final JavaMailSender javaMailSender;
    final JwtService jwtService;
    final TokenRepository tokenRepository;
    final AuthenticationManager authenticationManager;

    public AuthService(AuthRepository authRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, JavaMailSender javaMailSender, JwtService jwtService, TokenRepository tokenRepository, AuthenticationManager authenticationManager) {
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.javaMailSender = javaMailSender;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.authenticationManager = authenticationManager;
    }

    public ApiResponse register(UserDto userDto) {
        boolean exists = authRepository.existsByUsernameOrEmailOrPhoneNumber(userDto.getUsername(), userDto.getEmail(), userDto.getPhoneNumber());
        if (!exists) {
            User user = new User();
            if (userDto.getEmail() != null) {
                user.setFirstName(userDto.getFirstName());
                user.setLastName(userDto.getLastName());
                user.setUsername(userDto.getUsername());
                user.setEmail(userDto.getEmail());
                user.setPhoneNumber(userDto.getPhoneNumber());
                user.setRoles(Collections.singleton(roleRepository.findByRoleName(RoleName.ROLE_USER)));
                user.setPassword(passwordEncoder.encode(userDto.getPassword()));
                String randomStr = RandomStringUtils.randomNumeric(5);
                user.setActivationCode(randomStr);
                User saveUser = authRepository.save(user);
                SimpleMailMessage message = new SimpleMailMessage();
                message.setSubject("Verify your new account " + "http://localhost/auth/verify/" + saveUser.getActivationCode());
                message.setText("Click here link");
                message.setTo(user.getEmail());
                javaMailSender.send(message);
                return new ApiResponse("Registered", true);
            }
            return new ApiResponse("User email not found", false);
        }
        return new ApiResponse("Username or email or phoneNumber already exists", false);
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    public ApiResponse verifyUser(String verifyCode) {
        List<User> userList = authRepository.findAllByEnabledFalse();
        for (User user : userList) {
            if (user.getActivationCode().equals(verifyCode)) {
                user.setEnabled(true);
                user.setActivationCode(null);
                User saveUser = authRepository.save(user);
                String generateToken = jwtService.generateToken(saveUser);
                saveUserToken(saveUser, generateToken);
                return new ApiResponse(generateToken, true);
            }
            return new ApiResponse("Invalid verify code", false);
        }
        return new ApiResponse("User not found", false);
    }

    public ApiResponse login(LoginDto loginDto) {
        Optional<User> user1 = authRepository.findByUsername(loginDto.getUsername());
        if (user1.isPresent()) {
            User user = user1.get();
            boolean isPasswordMatch = passwordEncoder.matches(loginDto.getPassword(), user.getPassword());
            if (isPasswordMatch) {
                if (user.isEnabled()) {
                    String generateToken = jwtService.generateToken(user);
                    saveUserToken(user, generateToken);
                    revokeAllUserTokens(user);
                    return new ApiResponse(generateToken, true);
                }
                return new ApiResponse("User enabled false", false);
            }
            return new ApiResponse("Password incorrect", false);
        }
        return new ApiResponse("User not found", false);
    }


    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public ApiResponse editPassword(UUID id, String oldPassword, PasswordDto passwordDto) {
        User getUser = authRepository.findById(id).orElseThrow(() -> new RuntimeException("getUser"));
        if (getUser.getPassword().equals(oldPassword)) {
            if (passwordDto.getPassword().equals(passwordDto.getPrePassword())) {
                getUser.setPassword(passwordDto.getPassword());
                authRepository.save(getUser);
                return new ApiResponse("Successfully edited password", true);
            }
            return new ApiResponse("Password and prePassword not equals", false);
        }
        return new ApiResponse("the current password is not the same as the previous password", false);
    }

    public ApiResponse forgetPassword(String username) {
        Optional<User> user = authRepository.findByUsernameOrEmailOrPhoneNumber(username, username, username);
        if (user.isPresent()) {
            User getUser = user.get();
            SimpleMailMessage message = new SimpleMailMessage();
            String randomStr = RandomStringUtils.randomNumeric(5);
            getUser.setActivationCode(randomStr);
            message.setSubject("Verify your new account " + "http://localhost/auth/verify/" + getUser.getActivationCode());
            message.setText("Click here link");
            message.setTo(getUser.getEmail());
            javaMailSender.send(message);
            return new ApiResponse("verify code", true);
        }
        return new ApiResponse("user not found", false);

    }

    public ApiResponse verifyPassword(String verifyCode, PasswordDto passwordDto) {
        Optional<User> user = authRepository.findByActivationCode(verifyCode);
        if (user.isPresent()) {
            if (passwordDto.getPassword().equals(passwordDto.getPrePassword())) {
                User user1 = user.get();
                user1.setPassword(passwordDto.getPassword());
                return new ApiResponse("Edit password", true);
            }
            return new ApiResponse("Password and prePassword not equals", false);
        }
        return new ApiResponse("User not found", false);

    }


    public UserDto getOneUser(UUID id) {
        User user = authRepository.findById(id).orElseThrow(() -> new ResourceAccessException("getUser"));
        return new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getPhoneNumber(), user.getEmail(), user.getPassword());
    }

    public List<UserDto> getUserList() {
        List<User> userList = authRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : userList) {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setFirstName(user.getFirstName());
            userDto.setLastName(user.getLastName());
            userDto.setEmail(user.getEmail());
            userDto.setUsername(user.getUsername());
            userDto.setPhoneNumber(user.getPhoneNumber());
            userDto.setPassword(user.getPassword());
            userDtos.add(userDto);
        }
        return userDtos;
    }

    public ApiResponse roleEdit(UUID id, User user, RoleDto role) {
        User getUser = authRepository.findById(id).orElseThrow(() -> new ResourceAccessException("getUser"));
        Role name = roleRepository.findByRoleName(role.getRoleName());
        for (Role userRole : user.getRoles()) {
            if (userRole.getRoleName().equals(RoleName.ROLE_SUPER_ADMIN)) {
                Set<Role> roles = new HashSet<>();
                roles.add(name);
                getUser.setRoles(roles);
                authRepository.save(getUser);
                return new ApiResponse("Role edited", true);
            }
        }
        return new ApiResponse("Current user role is not super admin", false);
    }


    public ApiResponse logout(UUID id) {
//        User getUser = authRepository.findById(id).orElseThrow(() -> new ResourceAccessException("getUser"));
        Optional<Token> tokenOptional = tokenRepository.findByUser_Id(id);
        if (tokenOptional.isPresent()) {
            Token token = tokenOptional.get();
            tokenRepository.deleteByToken(token.getToken());
            return new ApiResponse("Successfully logout", true);
        }
        return new ApiResponse("Token not found", false);
    }

}