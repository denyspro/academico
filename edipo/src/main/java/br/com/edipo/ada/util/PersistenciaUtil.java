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

	public static boolean isEntityManagerOpen() {
		return manager.get() != null && manager.get().isOpen();
	}

	public static EntityManager getEntityManager() {
		if (factory == null) {
			factory = Persistence
					.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
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

	public static void closeEntityManagerFactory() {
		closeEntityManager();
		factory.close();
	}

	/***
	 * M�todo utilizado para auxiliar o tratamento de exce��es na JPA. O
	 * cont�iner encapsula a causa raiz em outras exce��es, sem dar acesso
	 * direto � sua classe. Portanto, a �nica forma de identificar � buscando
	 * pelo nome da classe entre as mensagens. Este m�todo retorna verdadeiro se
	 * a exce��o procurada for encontrada.
	 * 
	 * @param excecao
	 *            Exce��o disparada durante a opera��o.
	 * @param chave
	 *            Refer�ncia da exce��o expec�fica a ser procurada dentro da
	 *            cadeia de exce��es para que seu seja feito tratamento.
	 * @return verdadeiro ou falso
	 */
	public static boolean possuiNaExcecao(Throwable excecao, String chave) {
		boolean resposta = false;

		while (excecao != null) {
			if (excecao.getMessage().contains(chave)) {
				resposta = true;
				break;
			}
			excecao = excecao.getCause();
		}

		return resposta;
	}
}
