package br.edu.ifpb.memoriam.facade;

import java.util.ArrayList;
import java.util.List;

public class Resultado {
	private Object entidade;
	private boolean erro;
	private List<Mensagem> mensagens;
	
	public Resultado() {
		this.mensagens = new ArrayList<Mensagem>();
	}

	public void addMensagem(Mensagem m) {
		this.mensagens.add(m);
	}

	public boolean isErro() {
		return erro;
	}

	public void setErro(boolean erro) {
		this.erro = erro;
	}

	public Object getEntidade() {
		return entidade;
	}

	public void setEntidade(Object entidade) {
		this.entidade = entidade;
	}

	public List<Mensagem> getMensagens() {
		return mensagens;
	}

	public void setMensagens(List<Mensagem> mensagens) {
		this.mensagens = mensagens;
	}

	public void addMensagens(List<String> mensagensErro, Categoria categoria) {
		List<Mensagem> mensagens = new ArrayList<Mensagem>();
		for (String s : mensagensErro) {
			mensagens.add(new Mensagem(s, categoria));
		}
		this.mensagens.addAll(mensagens);
		
	}

}
