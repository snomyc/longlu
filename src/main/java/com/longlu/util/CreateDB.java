package com.longlu.util;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * 用于由Hibernate，OR映射文件，生成表 
 * 如果实体类中字段改变了，运行该类则数据库中的表字段会自动改变
 * 注意该类只适用于实体类加注解的方式(必须有persistence.xml)，不适用hbm.xml文件
 * @author Administrator
 * 
 */
public class CreateDB {
	/**
	 * 根据Hibernate配置文件、映射文件。在相应数据库中生成表。
	 */
	public void createTable() {
		try {
			System.out.println("初始化数据开始......");
			Map<String, String> map = new HashMap<String, String>();
			map.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
			map.put("hibernate.connection.driver_class",
					"com.mysql.jdbc.Driver");
			map.put("hibernate.connection.url",
					"jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf-8&&zeroDateTimeBehavior=convertToNull");
			map.put("hibernate.connection.username", "root");
			map.put("hibernate.connection.password", "123456");
			map.put("hibernate.show_sql", "true");
			
			//create 创建表，之前数据库已存在的表删除
			//update 更新表
			map.put("hibernate.hbm2ddl.auto", "create");
			//persistence.xml persistence-unit name="shiroTestPU"
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("shiroTestPU", map);
			emf.close();
			System.out.println("初始化数据完成");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		CreateDB db = new CreateDB();
		db.createTable();
	}
}