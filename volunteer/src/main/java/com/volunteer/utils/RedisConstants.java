package com.volunteer.utils;

public class RedisConstants {
//    public static final String LOGIN_CODE_KEY = "login:code:";
//    public static final Long LOGIN_CODE_TTL = 2L;
    public static final String LOGIN_USER_KEY = "login:token:";
    public static final Long LOGIN_USER_TTL = 30L;

    public  static final String CACHE_ACTIVITYS_KEY = "cache:activitys:";
    public static final Long CACHE_ACTIVITYS_TTL = 3L;

    public  static final String CACHE_INSTITUTIONS_KEY = "cache:institutions:";

    public  static final String CACHE_USER_INSTITUTION_KEY = "cache:user_institutions:";

    public  static final String CACHE_USER_ACTIVITY_KEY = "cache:user_activities:";

}
