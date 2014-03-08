package br.com.edipo.ada.controller;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.edipo.ada.entity.Curso;
import br.com.edipo.ada.model.CursoSB;
import br.com.edipo.ada.model.UsuarioSB;
import br.com.edipo.ada.security.AutorizacaoSB;
import br.com.edipo.ada.util.VisaoUtil;

/***
 * <i>Backing bean</i> de escopo por vis‹o que faz o papel de controlador para o dom’nio de cursos.
 * 
 * @author Denys
 */
@ViewScoped
@ManagedBean
public class CursoMB {

	private static final Logger log = Logger.getLogger(CursoMB.class.getName());

	private String visaoOrigem = VisaoUtil.VISAOORIGEM;

	private Curso curso;
	private List<Curso> cursos;

	@PostConstruct
	public void init() {

		if (curso == null) {
			Integer idUsuario = null;

			try {
				idUsuario = Integer.parseInt(AutorizacaoSB.getAtributo("id"));
			} catch (Exception e) {
				log.severe("init: " + e.toString());
			}

			curso = new Curso();
			curso.setUsuario(UsuarioSB.getById(idUsuario));
		}
	}

	@PreDestroy
	public void release() {
		log.info("Liberando recursos...");
	}

	public String getVisaoOrigem() {
		return visaoOrigem;
	}

	public void setVisaoOrigem(String origem) {
		this.visaoOrigem = origem;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public List<Curso> getCursos() {
		Integer idUsuario = null;

		if (cursos == null) {
			try {
				idUsuario = Integer.parseInt(AutorizacaoSB.getAtributo("id"));
			} catch (Exception e) {
				log.severe("getCursos: " + e.toString());
			}

			cursos = CursoSB.getByUser(idUsuario);
		}
		return cursos;
	}

	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}

	public String salvar(Curso curso) {

		String excecao = CursoSB.salvar(curso);

		if (excecao=="") {
			String mensagem = String.format("Curso %s salvo.", curso.getDsCurso());
			VisaoUtil.setMessage(mensagem);
			return visaoOrigem;
		} else {
			VisaoUtil.setMessage(excecao);
			return "";
		}
	}

	public String excluir(Curso curso) {

		String excecao = CursoSB.excluir(curso);

		if (excecao=="") {
			String mensagem = String.format("Curso %s exclu’do.", curso.getDsCurso());
			VisaoUtil.setMessage(mensagem);
			return visaoOrigem;
		} else {
			VisaoUtil.setMessage(excecao);
			return "";
		}
	}
}