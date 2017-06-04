package com.czl.test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.czl.util.MyBatisSessionFactory;
import com.czl.vo.News;

import junit.framework.TestCase;

@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class TestNews {
	
	@Test
	public void test08GetAllCount(){
		String keyWord = "Android";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("column", "title");
		map.put("keyWord", "%" + keyWord + "%");
		Long count = MyBatisSessionFactory.getSession()
				.selectOne("com.czl.mapping.NewsNS.getAllCount", map);
		System.out.println(count);
	}
	
	@Test
	public void test07FindAllSplit(){
		long currentPage = 2;
		int lineSize = 2;
		String keyWord = "Android";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("column", "title");
		map.put("keyWord", "%" + keyWord + "%");
		map.put("start", (currentPage - 1) * lineSize);
		map.put("lineSize", lineSize);
		List<News> list = MyBatisSessionFactory.getSession().selectList("com.czl.mapping.NewsNS.findAllSplit",map);
		System.out.println(list);
	}
	
	@Test
	public void test06FindMap(){
		Map<Long, HashMap<Long, Object>> map = MyBatisSessionFactory.getSession().selectMap("com.czl.mapping.NewsNS.findMap", "nid");
		Iterator<Map.Entry<Long, HashMap<Long, Object>>> iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<Long, HashMap<Long, Object>> me = iter.next();
			System.out.println(me.getKey() + " = " + me.getValue());
			
			Iterator<Map.Entry<Long, Object>> iter2 = me.getValue().entrySet().iterator();
			while (iter2.hasNext()) {
				Map.Entry<Long, Object> me2 = iter2.next();
				System.out.println("\t|- " + me2.getKey() + " = " + me2.getValue());
			}
		}
		
	}

	@Test
	public void test05FindAll(){
		List<News> list = MyBatisSessionFactory.getSession().selectList("com.czl.mapping.NewsNS.findAll");
		TestCase.assertTrue(list.size() > 0);
	}
	
	@Test
	public void test04Get() {
		News vo = MyBatisSessionFactory.getSession().selectOne("com.czl.mapping.NewsNS.findById", 3L);
		TestCase.assertNotNull(vo);
	}

	@Test
	public void test03Delete() {
		int count = MyBatisSessionFactory.getSession().delete("com.czl.mapping.NewsNS.doRemove", 1L);
		MyBatisSessionFactory.getSession().commit();
		MyBatisSessionFactory.close();
		TestCase.assertEquals(count, 1L);
	}

	@Test
	public void test02Edit() {
		News vo = new News();
		vo.setNid(2L);
		vo.setTitle("Redis");
		vo.setNote("内存数据库");
		int count = MyBatisSessionFactory.getSession().update("com.czl.mapping.NewsNS.doUpdate", vo);
		MyBatisSessionFactory.getSession().commit();
		MyBatisSessionFactory.close();
		TestCase.assertEquals(count, 1);
	}

	@Test
	public void test01Add() {
		News vo = new News();
		vo.setTitle("Android" + new Random().nextInt(9999));
		vo.setNote("上课好累啊");
		int count = MyBatisSessionFactory.getSession().insert("com.czl.mapping.NewsNS.doCreate", vo);
		MyBatisSessionFactory.getSession().commit();
		MyBatisSessionFactory.close();
		System.out.println(vo);
		TestCase.assertEquals(count, 1);
	}
}
