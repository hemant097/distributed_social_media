package com.example.linkedInProject.post_service.auth;

public class AuthContextHolder {

    //using ThreadLocal provides better concurrency handling, instead of using only static final Long (which'll hamper with multiple concurrent requests)
    private static final ThreadLocal<Long> currentUserId = new ThreadLocal<>();

    public static Long getCurrentUserId(){return currentUserId.get();}

    static void setCurrentUserId(Long userId){
        currentUserId.set(userId);
    }

    static void clear(){
        currentUserId.remove();
    }
}
