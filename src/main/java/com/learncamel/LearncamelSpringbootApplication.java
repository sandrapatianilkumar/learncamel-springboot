package com.learncamel;

import com.learncamel.hibernateutil.HibernateUtil;
import com.learncamel.route.entity.Alien;
import org.hibernate.Session;
import org.hibernate.SessionBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.spi.SessionFactoryServiceRegistryBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.imageio.spi.ServiceRegistry;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
@ConfigurationProperties(value = "hibernate.cfg.xml")
public class LearncamelSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearncamelSpringbootApplication.class, args);
		Alien alien= new Alien();
		alien.setAid(1);
		alien.setAname("Anil");
		alien.setColor("white");

		Alien alien1= new Alien();
		alien.setAid(2);
		alien.setAname("Kumar");
		alien.setColor("Black");

		Transaction transaction = null;
		try(Session session= HibernateUtil.getSessionFactory().openSession()){
			transaction = session.beginTransaction();
			session.save(alien);
			session.save(alien1);
			// commit transaction
			transaction.commit();
		} catch (Exception e){
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			List<Alien> students = session.createQuery("from Alien", Alien.class).list();
			students.forEach(s -> System.out.println(s.getAname()));
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}


	@Bean
	public String getCurrentDateTime(){
		LocalDateTime dateTime=LocalDateTime.now();
		return dateTime.toString();
	}
}
