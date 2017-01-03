package com.hisign.sdk.cache;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.hisign.sdk.cache.Person.Sex;

@Service("UserService")
public class PersonServiceImpl implements PersonService{

	private static final Logger LOG = LoggerFactory.getLogger(PersonServiceImpl.class);
	
	@Override
	@Cacheable(value="ehRedisCache",key="'UAOP:user:'+#id")
	public Person findById(long id) {
		LOG.info("visit business service findById,id:{}",id);
		Person user = new Person();
		user.setId(id);
		user.setUserName("tony");
		user.setPassWord("******");
		user.setSex(Sex.M);
		user.setAge(32);
		//耗时操作
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return user;
	}

	
	@Override
	public List<Person> findByPage(int startIndex, int limit) {
		return null;
	}
	
	@Cacheable("ehRedisCache")
	@Override
	public List<Person> findBySex(Sex sex) {
		LOG.info("visit business service findBySex,sex:{}",sex);
		List<Person> users = new ArrayList<Person>();
		for (int i = 0; i < 5; i++) {
			Person user = new Person();
			user.setId(i);
			user.setUserName("tony"+i);
			user.setPassWord("******");
			user.setSex(sex);
			user.setAge(32+i);
			users.add(user);
		}
		return users;
	}

	@Override
	public List<Person> findByAge(int lessAge) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//FIXME 此处将list参数的地址作为key存储，是否有问题？
	@Override
	public List<Person> findByUsers(List<Person> users) {
		LOG.info("visit business service findByUsers,users:{}",users);
		return users;
	}


	@CacheEvict("ehRedisCache")
	@Override
	public boolean update(Person user) {
		return true;
	}

	@CacheEvict("ehRedisCache")
	@Override
	public boolean deleteById(long id) {
		return false;
	}
	
}
