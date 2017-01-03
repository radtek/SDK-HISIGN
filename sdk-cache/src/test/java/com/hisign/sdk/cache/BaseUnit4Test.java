/**
 * 
 */
package com.hisign.sdk.cache;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hisign.sdk.cache.ercache.EhRedisCache;
import com.hisign.sdk.cache.redis.RedisClient;
import com.hisign.sdk.config.SysConfigLoader;


/**
 * JUNIT测试基类
 * @author chailiangzhi
 * @date 2015-8-21
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
//加载配置
@ContextConfiguration(locations = {"classpath:spring-test.xml"})
public class BaseUnit4Test {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	
	@Autowired
	private RedisClient redisClient;
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private EhRedisCache ehRedisCache;
	
	public BaseUnit4Test(){
		SysConfigLoader.getInstance().loadSysConfig("UAOP");
		System.out.println("system properties="+System.getProperties());
	}

	@Test
	public void testApp() {
		Assert.assertTrue(true);
	}
	
	@Test
	public void testBasic(){
		System.out.println("system.properties="+System.getProperties().toString());
		String key1 = "UAOP:test:key1";
		redisClient.set(key1,"123");
		System.out.println("put "+key1+" 123 to redis");
		String value = redisClient.get(key1);
		System.out.println("get "+key1+" value="+value+" from redis");
	}
	
	@Test
	public void testObject(){
		Person p1 = new Person(1,"p1",25);
		String key1 = "UAOP:test:obj:"+p1.getUserName();
		redisClient.setObj(key1, p1);
		System.out.println("put "+key1+"  to redis");
		Person value = redisClient.getObj(key1);
		System.out.println("get "+key1+" value="+value+" from redis");
	}
	
	@Test
	public void testEhRedisCache(){
		for(int i  = 0; i < 5; i++){
			System.out.println(this.personService.findById(1));
//			try {
//				Thread.sleep(15000);
//			} catch (InterruptedException e) {
//			}
		}
	}
	
	@Test
	public void testEhRedisCacheTimeout(){
		for(int i  = 0; i < 5; i++){
			String key = "UAOP:user:1";
			System.out.println(ehRedisCache.get(key));
			try {
				Thread.sleep(15000);
			} catch (InterruptedException e) {
			}
		}
	}
}
