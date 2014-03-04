package br.com.edipo.ada.model;

import java.util.List;

import javax.ejb.Stateless;

import br.com.edipo.ada.entity.Perfil;
import br.com.edipo.ada.util.PersistenciaUtil;

/***
 * <i>Stateless bean</i> que faz o papel de modelo para o dom’nio de perfis.
 * 
 * @author Denys
 */
@Stateless
public class PerfilSB {
	public static List<Perfil> getTodos() {
		String jpql = "select p from Perfil p";

		return PersistenciaUtil.getEntityManager().createQuery(jpql, Perfil.class).getResultList();
	}
}
