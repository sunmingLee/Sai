//package com.sai;
//
//import java.awt.print.Book;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.EntityTransaction;
//import javax.persistence.Persistence;
//
//public class TestMain {
//
//	public static void main(String[] args) {
//		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
//
//		EntityManager em = emf.createEntityManager();
//
//		EntityTransaction tx = em.getTransaction();
//		tx.begin();
//
//		try {
//
//			Book book = new Book();
//			book.setName("JPA");
//			book.setAuthor("±è¿µÇÑ");
//			em.persist(book);
//
//			tx.commit();
//
//		} catch (Exception e) {
//			tx.rollback();
//		} finally {
//			em.close();
//		}
//
//		emf.close();
//	}
//
//}
