package me.kadarh.mecaworks.config.security;

import java.util.Arrays;
import java.util.List;

public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String SAISI = "ROLE_SAISI";

    public static List<String> getRoles() {
        return Arrays.asList(ADMIN, USER, SAISI);
    }
}
