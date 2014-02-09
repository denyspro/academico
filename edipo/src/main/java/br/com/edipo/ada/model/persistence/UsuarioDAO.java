package br.com.edipo.ada.model.persistence;

import br.com.edipo.ada.model.entity.Usuario;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

import org.javalite.activejdbc.Base;

import java.util.List;

@Stateless
public class UsuarioDAO {
	@PostConstruct
	public void init() {
		if (!Base.hasConnection())
			Base.open("java:jboss/datasources/edipo");
	}

	public List<Usuario> lista() {
		List<Usuario> u = Usuario.findAll();
		return u;
	}

	public void cria(Usuario u) {
		u.set("dsIdentificador", "denysbrag@gmail.com");
		u.set("dsSenha", "nononon");
		u.set("dsSal", "nononon");
		u.set("dsNome", "Denys");
		u.set("dtNascimento", "1981-10-12");
		u.saveIt();
	}

	public void atualiza(Usuario u) {
		u.set("dsIdentificador", "denysbrag@gmail.com");
		u.set("dsSenha", "nononon");
		u.set("dsSal", "nononon");
		u.set("dsNome", "Denys");
		u.set("dtNascimento", "1981-10-12");
		u.saveIt();
	}

	public void apaga(Usuario u) {
		u.delete();
	}

	public boolean existePorIdentificador(String identificador) {
		Usuario u = obtemPorIdentificador(identificador);

		return (u.exists() ? true : false);
	}

	public Usuario obtemPorIdentificador(String identificador) {
		Usuario u = Usuario.findFirst("dsIdentificador = ?", identificador);
		return u;
	}
}