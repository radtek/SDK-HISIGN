package com.hisign.sdk.msg.demo;

import java.io.Serializable;

public class Person implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long id;
	private String name;
	private int sex;
	public Person() {
	}

	public Person(long id, String name, int sex) {
		this.id = id;
		this.name = name;
		this.sex = sex;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Person [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", sex=");
		builder.append(sex);
		builder.append("]");
		return builder.toString();
	}
	
}
