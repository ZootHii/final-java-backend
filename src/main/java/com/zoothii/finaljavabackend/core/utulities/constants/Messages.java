package com.zoothii.finaljavabackend.core.utulities.constants;

public class Messages {
    public static String successGetRoles = "Roles are successfully listed.";
    public static String successGetRoleByName = "Role returned by name.";

    public static String successRegister = "User registered successfully.";
    public static String successLogin = "User logged in successfully.";
    public static String successPassword = "Password is correct.";
    public static String errorPassword = "Password is not correct.";

    public static String successGetProducts = "Products returned successfully.";
    public static String successCreateProduct = "Product created successfully.";
    public static String successDeleteProduct = "Product deleted successfully.";

    public static String errorCreateDefaultRoleIfNotExists(String defaultRole) {
        return "Default role " + defaultRole + " is already exists.";
    }

    public static String successCreateDefaultRoleIfNotExists(String defaultRole) {
        return "Default role " + defaultRole + " is successfully created.";
    }
    public static String successCheckIfRoleExists(String role) {
        return "Role " + role + " is exists.";
    }
    public static String errorCheckIfRoleExists(String role) {
        return "Role " + role + " is not exists.";
    }
}
