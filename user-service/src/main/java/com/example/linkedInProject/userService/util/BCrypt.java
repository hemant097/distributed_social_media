package com.example.linkedInProject.userService.util;

public class BCrypt {

    public static String hashString(String str){
        return org.mindrot.jbcrypt.BCrypt.hashpw(str, org.mindrot.jbcrypt.BCrypt.gensalt());
    }

    public static boolean match(String passwordText, String passwordHashed){
        return org.mindrot.jbcrypt.BCrypt.checkpw(passwordText,passwordHashed);
    }

}
