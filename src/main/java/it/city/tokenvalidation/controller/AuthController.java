package it.city.tokenvalidation.controller;

import it.city.tokenvalidation.entity.Token;
import it.city.tokenvalidation.entity.User;
import it.city.tokenvalidation.payload.*;
import it.city.tokenvalidation.repository.TokenRepository;
import it.city.tokenvalidation.security.CurrentUser;
import it.city.tokenvalidation.service.AuthService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    final AuthService authService;
    final TokenRepository tokenRepository;
    public AuthController(AuthService authService, TokenRepository tokenRepository) {
        this.authService = authService;
        this.tokenRepository = tokenRepository;
    }

    @PostMapping("/register")
    public HttpEntity<?> registerUser(@RequestBody UserDto userDto) {
        ApiResponse apiResponse = authService.register(userDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @PostMapping("/verify/{verifyCode}")
    public HttpEntity<?> verifyUser(@PathVariable String verifyCode) {
        ApiResponse apiResponse = authService.verifyUser(verifyCode);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(apiResponse);
    }

    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody LoginDto loginDto) {
        ApiResponse apiResponse = authService.login(loginDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @PutMapping("/edit/password/{id}")
    public HttpEntity<?> editPassword(@PathVariable UUID id, @RequestBody String oldPassword, @RequestBody PasswordDto passwordDto) {
        ApiResponse apiResponse = authService.editPassword(id, oldPassword, passwordDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @PutMapping("/forget/password")
    public HttpEntity<?> forgetPassword(@RequestBody String username) {
        ApiResponse apiResponse = authService.forgetPassword(username);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @PutMapping("/verify/password/{verifyCode}")
    public HttpEntity<?> verifyPassword(@PathVariable String verifyCode, @RequestBody PasswordDto passwordDto) {
        ApiResponse apiResponse = authService.verifyPassword(verifyCode, passwordDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }


    @GetMapping("/{id}")
    public HttpEntity<?> getOneUser(@PathVariable UUID id) {
        return ResponseEntity.ok(authService.getOneUser(id));
    }

    @GetMapping
    public HttpEntity<?> getUserList() {
        return ResponseEntity.ok(authService.getUserList());
    }


    @PutMapping("/role/{id}")
    public HttpEntity<?> roleEdit(@PathVariable UUID id, @CurrentUser User user, @RequestBody RoleDto role) {
        ApiResponse apiResponse = authService.roleEdit(id, user, role);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @DeleteMapping("/logout/{id}")
    public HttpEntity<?> logout(@PathVariable UUID id){
        ApiResponse logout = authService.logout(id);
        return ResponseEntity.status(logout.isSuccess()?201:409).body(logout);
    }


}