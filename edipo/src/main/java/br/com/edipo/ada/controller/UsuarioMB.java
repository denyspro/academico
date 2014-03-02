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

/***
 * <i>Backing bean</i> de escopo por vis‹o que faz o papel de controlador para o dom’nio de usu‡rios.
 * 
 * @author Denys
 */
@ViewScoped
@ManagedBean
public class UsuarioMB {

	private static final Logger log = Logger.getLogger(UsuarioMB.class.getName());

	private Usuario usuario;
	private List<Usuario> usuarios;

	private String visaoOrigem = "lista?faces-redirect=true";

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
		log.info("Liberando recursos...");
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

	public String getVisaoOrigem() {
		return visaoOrigem;
	}

	public void setVisaoOrigem(String origem) {
		this.visaoOrigem = origem;
	}

	public String excluir(Usuario usuario) {

		String mensagem = String.format("Usu‡rio %s exclu’do.", usuario.getDsIdentificador());

		if (UsuarioSB.delete(usuario)) {
			ViewUtil.setMessage(mensagem);
		} else {
			visaoOrigem = "";
		}

		return visaoOrigem;
	}

	public String salvar(Usuario usuario) {

		String mensagem = String.format("Usu‡rio %s salvo.", usuario.getDsIdentificador());

		if (UsuarioSB.save(usuario)) {
			ViewUtil.setMessage(mensagem);
		} else {
			visaoOrigem = "";
		}

		return visaoOrigem;
	}
}