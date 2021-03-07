package com.mobile.example.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UserUtil {

    public static String setRole(int nSalary) {
    	
    	String role=null;;
    	if(nSalary>50000) {
    		role="PLATINUM";
    	}else if(nSalary>=30000 && nSalary<=50000) {
    		role="GOLD";
    	}else if(nSalary<30000) {
    		role="SILVER";
    	}
    	return role;
    }
    
    public static String setReference(String phone) {
    	String ref = null;
    	SimpleDateFormat sfd = new SimpleDateFormat("yyyyMMdd");
    	ref=sfd.format(new Date())+phone.substring(6, phone.length());
    	return ref;
    }
    
    public static String replacePhone(String phone) {
    	String begin = phone.substring(6, 10);
    	return "XXXXXX"+begin;
    }
    
}
