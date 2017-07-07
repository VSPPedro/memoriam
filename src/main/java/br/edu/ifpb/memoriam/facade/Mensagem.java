package br.edu.ifpb.memoriam.facade;

public class Mensagem {
	private String mensagem;
	private Categoria categoria;
	
	public Mensagem() {
		this("", Categoria.ERRO);
	}

	public Mensagem(String mensagem, Categoria categoria) {
		super();
		this.mensagem = mensagem;
		this.categoria = categoria;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

}
