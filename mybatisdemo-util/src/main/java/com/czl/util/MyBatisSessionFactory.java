package com.czl.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisSessionFactory {
	public static final String CONFIG_FILE = "mybatis/mybatis.cfg.xml" ;	// 配置文件路径
	private static final ThreadLocal<SqlSession> threadLocal = new ThreadLocal<SqlSession>() ;
	private static InputStream inputStream = null ;
	private static SqlSessionFactory factory ;
	static {
		try {
			inputStream = Resources.getResourceAsStream("mybatis/mybatis.cfg.xml") ;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 进行SqlSession接口对象关闭
	 */
	public static void close() {
		SqlSession session = threadLocal.get() ;
		if (session != null) {
			session.close();
			threadLocal.remove(); 
		}
	}
	
	/**
	 * 创建SqlSessionFactory接口对象
	 * @return SqlSessionFactory对象
	 */
	public static SqlSessionFactory getSessionFactory() {
		if (factory == null) {
			createSessionFactory() ;
		}
		return factory ;
	}
	/**
	 * 取得或创建一个SqlSession接口对象
	 * @return SqlSession实例化对象
	 */
	public static SqlSession getSession() {
		SqlSession session = threadLocal.get() ;
		if (session == null) {
			if (factory == null) {
				createSessionFactory() ;
			}
			session = factory.openSession() ;
			threadLocal.set(session);
		}
		return session ;
	}
	/**
	 * 进行SqlSessionFactory接口类对象创建
	 * @return SqlSessionFactory对象
	 */
	public static SqlSessionFactory createSessionFactory() {
		factory = new SqlSessionFactoryBuilder().build(inputStream) ;
		return factory ;
	}
}
