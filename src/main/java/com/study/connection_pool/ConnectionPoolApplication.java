package com.study.connection_pool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.InvocationTargetException;

@SpringBootApplication
public class ConnectionPoolApplication {

	public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
		SpringApplication.run(ConnectionPoolApplication.class, args);
		Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
	}

}
