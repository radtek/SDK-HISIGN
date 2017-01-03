package com.hisign.sdk.cache;

import java.util.List;

import com.hisign.sdk.cache.Person.Sex;


public interface PersonService {
	
	Person findById(long id);
	
	List<Person> findByPage(int startIndex, int limit);
	
	List<Person> findBySex(Sex sex);
	
	List<Person> findByAge(int lessAge);
	
	List<Person> findByUsers(List<Person> users);
	
	boolean update(Person user);
	
	boolean deleteById(long id);
}
