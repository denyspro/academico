package br.com.edipo.ada.controller;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.edipo.ada.entity.Usuario;
import br.com.edipo.ada.model.UsuarioSB;
import br.com.edipo.ada.util.ViewUtil;

@ViewScoped
@ManagedBean
public class UsuarioMB {

	private static final Logger log = Logger.getLogger(UsuarioMB.class
			.getName());

	private Usuario usuario;
	private List<Usuario> usuarios;

	@PostConstruct
	public void init() {

		String id = ViewUtil.getViewParam("idUsuario");

		if (id != null) {
			try {
				usuario = UsuarioSB.getById(Integer.parseInt(id));
			} catch (Exception e) {
				log.severe(e.toString());
			}
		}

		if (usuario == null) {
			usuario = new Usuario();
		}
	}

	@PreDestroy
	public void release() {
		log.info("PreDestroy");
	}

	public List<Usuario> getUsuarios() {
		if (usuarios == null) {
			usuarios = UsuarioSB.getAll();
		}
		return usuarios;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String excluir(Usuario u) {

		String navRule = "";
		String m = String
				.format("Usu‡rio %s exclu’do.", u.getDsIdentificador());

		if (UsuarioSB.delete(u)) {
			navRule = "listar";
			ViewUtil.setMessage(m);
		}

		return navRule;
	}

	public String salvar(Usuario u) {

		String navRule = "";
		String m = String.format("Usu‡rio %s criado.", u.getDsIdentificador());

		if (UsuarioSB.save(u)) {
			navRule = "listar";
			ViewUtil.setMessage(m);
		}

		return navRule;
	}
}