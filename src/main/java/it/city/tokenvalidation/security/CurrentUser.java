package it.city.tokenvalidation.security;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.*;

@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented

@AuthenticationPrincipal
public @interface CurrentUser {

}
