package com.mobile.example.dto;

import org.apache.commons.lang3.StringUtils;

import com.mobile.example.entity.User;

public class UserDTO {
	private String userName;
	private String password;
	private String salary;
	private String phone;
	private String address;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
    public User transferUser(){
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        user.setPhone(phone);
        user.setSalary(salary);
        user.setAddress(address);
        return user;
    }
    
    public String validate() {
    	String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
    	StringBuilder res = new StringBuilder();
    	boolean resultSalary = false;
    	boolean resultPhone = false;
    	try {
    		Integer.parseInt(salary);
    	}catch(Exception e) {
    		resultSalary=true;
    	}
    	try {
    		Integer.parseInt(phone);
    	}catch(Exception e) {
    		resultPhone=true;
    	}
    	if(!StringUtils.isEmpty(userName)&&userName.indexOf("@")==-1) {
    		res.append("Format username not correct");
    	}
    	if(!StringUtils.isEmpty(password)&&!pattern.matches(password)) {
    		if(res.length()!=0) {
    			res.append(",");
    		}
    		res.append("Password not strong");
    	}
    	if(!StringUtils.isEmpty("salary")&&resultSalary) {
    		if(res.length()!=0) {
    			res.append(",");
    		}
    		res.append("Salary must be integer");
    	}
    	if(!StringUtils.isEmpty(phone)&&(resultPhone||phone.length()!=10||checkStartPhone(phone))) {
    		if(res.length()!=0) {
    			res.append(",");
    		}
    		res.append("Format phone not correct");
    	}
    	
    	return res.toString();

    }
    
    public boolean checkStartPhone(String phone) {
    	boolean result = true;
    	if(phone.startsWith("09")||phone.startsWith("06")||phone.startsWith("08")) {
    		result=false;
    	}
    	return result;
    }
    
	public UserDTO(String userName, String password, String salary, String phone, String address) {
		super();
		this.userName = userName;
		this.password = password;
		this.salary = salary;
		this.phone = phone;
		this.address = address;
	}
    
    
	
	
}
