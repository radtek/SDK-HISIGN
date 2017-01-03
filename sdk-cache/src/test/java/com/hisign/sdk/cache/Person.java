package com.hisign.sdk.cache;

import java.io.Serializable;

import com.hisign.sdk.cache.ercache.ListKeyParam;


public class Person implements Serializable, ListKeyParam{
	
	@Override
	public Object getKey() {
		return id+":"+userName;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public enum Sex{
		M,FM
	}
	private long id;
	private String userName;
	private String passWord;
	private int age;
	private Sex sex;
	
	public Person() {
		this.id = id;
		this.userName = userName;
		this.passWord = passWord;
		this.age = age;
		this.sex = sex;
	}
	
	public Person(long id, String userName, int age) {
		this.id = id;
		this.userName = userName;
		this.age = age;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Sex getSex() {
		return sex;
	}
	public void setSex(Sex sex) {
		this.sex = sex;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", passWord="
				+ passWord + ", age=" + age + ", sex=" + sex + "]";
	}
	
	
}
