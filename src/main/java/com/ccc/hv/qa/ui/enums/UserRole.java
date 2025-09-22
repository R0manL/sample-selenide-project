package com.ccc.hv.qa.ui.enums;

/**
 * Contains list of available roles.
 * label - used as html locator identifier
 * passHashCode - identifier in DB.
 */
public enum UserRole {
    SUPER_ADMINISTRATOR(1, "Super Administrator", "Super", "HWfzlYGurqwYOZmQ7VMi5PSv/oexX4WP2Gt5hPaMts8="),
    SYSTEM_ADMINISTRATOR(7, "System Administrator", "SysAdmin", "HWfzlYGurqwYOZmQ7VMi5PSv/oexX4WP2Gt5hPaMts8="),
    ACCOUNT_ADMINISTRATOR(3, "Account Administrator", "Admin", "/wGWUliLhsJbWjxJpIzkiEixlxN0Ipp8o4zAr/tF5MQ="),
    HARVEST_VIEW_MANAGER(5, "HrvView Manager", "HrvViewManager", "/wGWUliLhsJbWjxJpIzkiEixlxN0Ipp8o4zAr/tF5MQ="),
    METADATA_ADMINISTRATOR(2, "Metadata Administrator", "MetaAdmin", "v/g0nFnfU7rRE+FaGLNKKDqbo7+597dqNukJf3SmdF4="),
    ACCOUNT_MANAGER(4, "Account Manager", "PublisherManager", "fyYRW6kx4lEsVTNMApW+ky1nrK0Wa5AAshatrQ1/HPg="),
    TENANT_USER(6, "Tenant User", "User", "yc1pd0sMETyu8KuF77m7cEOBgvjgxwmniwKB3PEcFpk=");

    private final int id;
    private final String text;
    private final String optionValue;
    private final String passHashCode;

    UserRole(int id, String text, String optionValue, String passHashCode) {
        this.id = id;
        this.optionValue = optionValue;
        this.text = text;
        this.passHashCode = passHashCode;
    }

    public String getPassHashCode() {
        return this.passHashCode;
    }

    public String getOptionValue() {
        return this.optionValue;
    }

    public String getText() {
        return this.text;
    }

    public int getId() {
        return this.id;
    }
}
