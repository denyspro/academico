package br.com.edipo.ada.controller;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.edipo.ada.entity.Perfil;
import br.com.edipo.ada.model.PerfilSB;

/***
 * <i>Backing bean</i> de escopo por vis‹o que faz o papel de controlador para o dom’nio de usu‡rios.
 * 
 * @author Denys
 */
@ViewScoped
@ManagedBean
public class PerfilMB {
	private static final Logger log = Logger.getLogger(PerfilMB.class.getName());

	private Perfil perfil;
	private List<Perfil> perfis;

	@PostConstruct
	public void init() {
	}

	@PreDestroy
	public void release() {
		log.info("Liberando recursos...");
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public List<Perfil> getPerfis() {
		if (perfis == null) {
			perfis = PerfilSB.getAll();
		}
		return perfis;
	}

	public void setPerfis(List<Perfil> perfis) {
		this.perfis = perfis;
	}
}