package com.github.zigcat.ormlite.services;

import com.github.zigcat.DatabaseConfiguration;
import com.github.zigcat.ormlite.exceptions.NotAuthorizedException;
import com.github.zigcat.ormlite.models.User;
import org.apache.commons.validator.routines.EmailValidator;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class Security {
    private static Logger l = LoggerFactory.getLogger(Security.class);

    public static User auth(String email, String password) throws SQLException {
        l.info("\t!!!CHECKING USER'S CREDENTIALS");
        for(User u: DatabaseConfiguration.userDao.queryForAll()){
            if(u.getEmail().equals(email) && BCrypt.checkpw(password, u.getPassword())){
                l.info("\t!!!USER "+u.toString()+" is AUTHORIZED");
                return u;
            }
        }
        throw new NotAuthorizedException("User not found or wrong credentials");
    }

    public static boolean isValidEmail(String email){
        String mail = email.trim();
        EmailValidator emailValidator = EmailValidator.getInstance();
        return emailValidator.isValid(mail);
    }

    public static boolean isValidDate(String date){
        int spaceCounter = 0, numCounter = 0;
        if(date.equals("0000")){
            return false;
        } else {
            for(int i=0;i<date.length();i++){
                if(date.charAt(i) == ' '){
                    spaceCounter ++;
                } else if(date.charAt(i) >= '0' && date.charAt(i) <= '9'){
                    numCounter++;
                }
            }
            return numCounter == 8 && spaceCounter == 2;
        }
    }
}
