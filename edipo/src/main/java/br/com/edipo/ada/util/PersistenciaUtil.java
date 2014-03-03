package br.com.edipo.ada.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class PersistenciaUtil {

	private static final String PERSISTENCE_UNIT_NAME = "default";

	private static ThreadLocal<EntityManager> manager = new ThreadLocal<EntityManager>();

	private static EntityManagerFactory factory;

	private PersistenciaUtil() {
	}

	public static boolean isEntityManagerOpen(){
		return manager.get() != null && manager.get().isOpen();
	}
	
	public static EntityManager getEntityManager() {
		if (factory == null) {
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		}
		EntityManager em = manager.get();
		if (em == null || !em.isOpen()) {
			em = factory.createEntityManager();
			manager.set(em);
		}
		return em;
	}

	public static void closeEntityManager() {
		EntityManager em = manager.get();
		if (em != null) {
			EntityTransaction tx = em.getTransaction();
			if (tx.isActive()) { 
				tx.commit();
			}
			em.close();
			manager.set(null);
		}
	}
	
	public static void closeEntityManagerFactory(){
		closeEntityManager();
		factory.close();
	}
}
