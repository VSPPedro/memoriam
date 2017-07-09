package br.edu.ifpb.memoriam.facade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import br.edu.ifpb.memoriam.dao.ContatoDAO;
import br.edu.ifpb.memoriam.dao.OperadoraDAO;
import br.edu.ifpb.memoriam.dao.PersistenceUtil;
import br.edu.ifpb.memoriam.entity.Contato;
import br.edu.ifpb.memoriam.entity.Operadora;

public class OperadoraController {

	private Operadora operadora;
	private List<String> mensagensErro;

	public List<Operadora> consultar() {
		OperadoraDAO dao = new OperadoraDAO();
		List<Operadora> operadoras = dao.findAll();
		return operadoras;
	}

	public Operadora buscar(Map<String, String[]> parametros) {
		List<Operadora> operadoras = consultar();
		String[] id = parametros.get("id");

		for (Operadora operadora : operadoras) {
			if (operadora.getId().toString().equals(id[0])) {
				return operadora;
			}
		}

		System.out.println("Operadora não encontrada!");
		return null;
	}
	
	public Operadora buscar(String id){
		List<Operadora> operadoras = consultar();
		
		for (Operadora operadora : operadoras) {
			if (operadora.getId().toString().equals(id)){
				return operadora;
			}
		}
		
		System.out.println("Operadora não encontrada!");
		return null;
	}
	
	public Resultado deletar(Map<String, String[]> parametros) {

		Resultado resultado = new Resultado();
		String[] idsDasOperadorasSelecionadas = parametros.get("delids");
		
		if (idsDasOperadorasSelecionadas.length > 0) {
			OperadoraDAO dao = new OperadoraDAO(PersistenceUtil.getCurrentEntityManager());
			dao.beginTransaction();

			for (String id : idsDasOperadorasSelecionadas) {
				Operadora contato = buscar(id);
				dao.delete(contato);
			}

			dao.commit();

			resultado.setErro(false);
			Mensagem mensagem = new Mensagem("Operadora(s) deletado(s) com sucesso!", Categoria.INFO);
			resultado.addMensagem(mensagem);
		} else {
			resultado.setErro(true);
			Mensagem mensagem = new Mensagem("Nenhuma operadora foi selecionada!", Categoria.ERRO);
			resultado.addMensagem(mensagem);
		}

		return resultado;
	}

	public Resultado cadastrar(Map<String, String[]> parametros) {
		Resultado resultado = new Resultado();

		if (isParametrosValidos(parametros)) {
			// Recupera a operadora selecionada, a partir do seu id
			OperadoraDAO dao = new OperadoraDAO(PersistenceUtil.getCurrentEntityManager());
			dao.beginTransaction();

			if (this.operadora.getId() == null) {
				dao.insert(this.operadora);
			} else {
				dao.update(this.operadora);
			}

			dao.commit();
			resultado.setErro(false);
			Mensagem mensagem = new Mensagem("Operadora(s) cadastrado(s) com sucesso!", Categoria.INFO);
			resultado.addMensagem(mensagem);
		} else {
			resultado.setEntidade(this.operadora);
			resultado.setErro(true);
			Mensagem mensagem = new Mensagem("Nenhuma operadora foi cadastrada!", Categoria.ERRO);
			resultado.addMensagem(mensagem);
			for (String mensagemErro : mensagensErro) {
				resultado.addMensagem(new Mensagem(mensagemErro,Categoria.ERRO));
			}
		}

		return resultado;
	}

	private boolean isParametrosValidos(Map<String, String[]> parametros) {
		// nomes dos parâmetros vêm dos atributos 'name' das tags HTML do
		// formulário
		String[] id = parametros.get("id");
		String[] nome = parametros.get("nome");
		String[] prefixo = parametros.get("prefixo");

		System.out.println("Valor id: " + id[0]);
		System.out.println("Valor nome: " + nome[0]);
		System.out.println("Valor prefixo: " + prefixo[0]);

		this.operadora = new Operadora();
		this.mensagensErro = new ArrayList<String>();

		if (id != null && id.length > 0 && !id[0].isEmpty()) {
			operadora.setId(Integer.parseInt(id[0]));
		}

		if (nome == null || nome.length == 0 || nome[0].isEmpty()) {
			this.mensagensErro.add("Nome é campo obrigatório!");
		} else {
			operadora.setNome(nome[0]);
		}

		if (prefixo == null || prefixo.length == 0 || prefixo[0].isEmpty()) {
			this.mensagensErro.add("Prefixo é campo obrigatório!");
		} else {
			operadora.setPrefixo(Integer.parseInt(prefixo[0]));
		}

		return this.mensagensErro.isEmpty();
	}
}
