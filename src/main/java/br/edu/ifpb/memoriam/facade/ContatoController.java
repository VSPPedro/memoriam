package br.edu.ifpb.memoriam.facade;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import br.edu.ifpb.memoriam.dao.ContatoDAO;
import br.edu.ifpb.memoriam.dao.OperadoraDAO;
import br.edu.ifpb.memoriam.dao.PersistenceUtil;
import br.edu.ifpb.memoriam.entity.Contato;
import br.edu.ifpb.memoriam.entity.Operadora;

public class ContatoController {

	private Contato contato;
	private List<String> mensagensErro;

	public List<Contato> consultar() {
		ContatoDAO dao = new ContatoDAO();
		List<Contato> contatos = dao.findAll();
		return contatos;
	}
	
	public void deletar(Map<String, String[]> parametros){
		String[] idsDosContatosSelecionados = parametros.get("selecionarContato");
		System.out.println("Metodo deletar contato!");
		
		if (idsDosContatosSelecionados == null){
			System.out.println("O array dos contatos selecionados nao apresenta conteudo!");
		}
		
		System.out.println("Tamanho do array dos contatos selecionados: " + idsDosContatosSelecionados[0]);
		
		ContatoDAO dao = new ContatoDAO(PersistenceUtil.getCurrentEntityManager());
		dao.beginTransaction();
		
		for (String id : idsDosContatosSelecionados) {
			Contato contato = buscar(id);
			dao.delete(contato);
		}
		
		dao.commit();
	}

	public Contato buscar(String id){
		List<Contato> contatos = consultar();
		
		for (Contato contato : contatos) {
			if (contato.getId().toString().equals(id)){
				return contato;
			}
		}
		
		System.out.println("Contato não encontrado!");
		return null;
	}

	public Contato buscar(Map<String, String[]> parametros){
		List<Contato> contatos = consultar();
		String[] id = parametros.get("id");
		
		for (Contato contato : contatos) {
			if (contato.getId().toString().equals(id[0])){
				return contato;
			}
		}
		
		System.out.println("Contato não encontrado!");
		return null;
	}

	public Resultado cadastrar(Map<String, String[]> parametros) {
		Resultado resultado = new Resultado();
		
		if (isParametrosValidos(parametros)) {
			// Recupera a operadora selecionada, a partir do seu id
			Operadora operadora = null;
			String idOperadora = parametros.get("operadora")[0];
			System.out.println("ID da operadora: " + idOperadora);
			
			if (idOperadora != null) {
				OperadoraDAO opDao = new OperadoraDAO(PersistenceUtil.getCurrentEntityManager());
				operadora = opDao.find(Integer.parseInt(idOperadora));
			}
			System.out.println("Operadora selecionada: " + operadora.getNome());
			contato.setOperadora(operadora);
			
			ContatoDAO dao = new ContatoDAO(PersistenceUtil.getCurrentEntityManager());
			dao.beginTransaction();

			if (this.contato.getId() == null) {
				dao.insert(this.contato);
			} else {
				dao.update(this.contato);
			}
			
			dao.commit();
			resultado.setErro(false);
			resultado.setMensagensErro(Collections.singletonList("Contato criado com sucesso"));
		} else {
			resultado.setEntidade(this.contato);
			resultado.setErro(true);
			resultado.setMensagensErro(this.mensagensErro);
		}

		return resultado;
	}

	private boolean isParametrosValidos(Map<String, String[]> parametros) {
		// nomes dos parâmetros vêm dos atributos 'name' das tags HTML do
		// formulário
		String[] id = parametros.get("id");
		String[] nome = parametros.get("nome");
		String[] fone = parametros.get("fone");
		String[] dataAniv = parametros.get("dataaniv");

		this.contato = new Contato();
		this.mensagensErro = new ArrayList<String>();

		if (id != null && id.length > 0 && !id[0].isEmpty()) {
			contato.setId(Integer.parseInt(id[0]));
		}

		if (nome == null || nome.length == 0 || nome[0].isEmpty()) {
			this.mensagensErro.add("Nome é campo obrigatório!");
		} else {
			contato.setNome(nome[0]);
		}

		if (fone == null || fone.length == 0 || fone[0].isEmpty()) {
			this.mensagensErro.add("Fone é campo obrigatório!");
		} else {
			contato.setFone(fone[0]);
		}

		if (dataAniv == null || dataAniv.length == 0 || dataAniv[0].isEmpty()) {
			this.mensagensErro.add("Data de aniversário é campo obrigatório!");
		} else {
			if (dataAniv[0].matches("(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/(19|20)\\d{2,2}")) {
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					sdf.setLenient(false);
					Date dataIni = sdf.parse(dataAniv[0]);
					contato.setDataAniversario(dataIni);
				} catch (ParseException e) {
					this.mensagensErro.add("Data inválida para a data de aniversário!");
				}
			} else {
				this.mensagensErro.add("Formato inválido para a data de aniversário(use dd/mm/aaaa)!");
			}
		}
		return this.mensagensErro.isEmpty();
	}
}
