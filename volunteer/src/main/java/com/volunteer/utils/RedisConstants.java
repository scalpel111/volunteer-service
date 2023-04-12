package com.volunteer.utils;

public class RedisConstants {
//    public static final String LOGIN_CODE_KEY = "login:code:";
//    public static final Long LOGIN_CODE_TTL = 2L;
    public static final String LOGIN_USER_KEY = "login:token:";
    public static final Long LOGIN_USER_TTL = 30L;

    public  static final String CACHE_ACTIVITYS_KEY = "cache:activitys";
    public  static final String CACHE_ACTIVITYS_UPDATE_KEY = "cache:activitys:update:";
//    public static final Long CACHE_ACTIVITYS_TTL = 3L;

    public  static final String CACHE_INSTITUTIONS_KEY = "cache:institutions";

    public  static final String CACHE_USER_INSTITUTION_KEY = "cache:user_institutions:";

    public  static final String CACHE_USER_ACTIVITY_KEY = "cache:user_activities:";

    public  static final String ACTIVITY_USER_KEY = "activity:user:";
    public  static final String USER_MESSAGE_KEY = "user:message:";
//    public  static final String FEED_MESSAGE_KEY = "feed:message:";
    public  static final String CHECK_KEY = "check:";


}
