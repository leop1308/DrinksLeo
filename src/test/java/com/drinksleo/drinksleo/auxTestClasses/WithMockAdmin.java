package com.drinksleo.drinksleo.auxTestClasses;


import org.springframework.security.test.context.support.WithMockUser;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithMockUser(username = "adminName",
roles = {"USER","ADMIN"})
public @interface WithMockAdmin {
}
