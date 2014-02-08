package br.com.edipo.ada.model.persistence;

import java.util.List;
import javax.ejb.Stateless;
import br.com.edipo.ada.model.entity.Usuario;

@Stateless
public class UsuarioDAO {
	public List<Usuario> listaUsuario() {
		List<Usuario> u = Usuario.findAll();
		return u;
	}

	public void criaUsuario() {
		Usuario u = new Usuario();
		u.set("dsIdentificador", "denysbrag@gmail.com");
		u.set("dsSenha", "nononon");
		u.set("dsSal", "nononon");
		u.set("dsNome", "Denys");
		u.set("dtNascimento", "1981-10-12");
		u.saveIt();
	}

	public void atualizaUsuario(Usuario u) {
		u.set("dsIdentificador", "denysbrag@gmail.com");
		u.set("dsSenha", "nononon");
		u.set("dsSal", "nononon");
		u.set("dsNome", "Denys");
		u.set("dtNascimento", "1981-10-12");
		u.saveIt();
	}

	public void apagaUsuario(Usuario u) {
		u.delete();
	}

	public boolean existeUsuarioPorIdentificador(String identificador) {
		Usuario u = obtemUsuarioPorIdentificador(identificador);

		return (u.exists() ? true : false);
	}

	public Usuario obtemUsuarioPorIdentificador(String identificador) {
		Usuario u = Usuario.findFirst("dsIdentificador = ?", identificador);
		return u;
	}
}