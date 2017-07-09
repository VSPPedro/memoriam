package br.edu.ifpb.memoriam.facade;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.edu.ifpb.memoriam.dao.PersistenceUtil;
import br.edu.ifpb.memoriam.dao.UsuarioDAO;
import br.edu.ifpb.memoriam.entity.Usuario;
import br.edu.ifpb.memoriam.util.PasswordUtil;

public class UsuarioController {
	
	private Usuario usuario;
	private List<String> mensagensErro;
	
	public List<Usuario> consultar() {
		UsuarioDAO dao = new UsuarioDAO();
		List<Usuario> usuarios = dao.findAll();
		return usuarios;
	}
	
	public Usuario buscar(Map<String, String[]> parametros){
		List<Usuario> usuarios = consultar();
		String[] id = parametros.get("id");
		
		for (Usuario usuario : usuarios) {
			if (usuario.getId().toString().equals(id[0])){
				return usuario;
			}
		}
		
		System.out.println("Usuario não encontrado!");
		return null;
	}

	public Resultado cadastrar(Map<String, String[]> parameterMap) {
		Resultado resultado = new Resultado();
		
		if (isParametrosValidos(parameterMap)) {
			
			UsuarioDAO dao = new UsuarioDAO(PersistenceUtil.getCurrentEntityManager());
			dao.beginTransaction();

			if (this.usuario.getId() == null) {
				dao.insert(this.usuario);
			} else {
				dao.update(this.usuario);
			}
			
			dao.commit();
			resultado.setErro(false);
			Mensagem mensagem = new Mensagem("Usuario(s) cadastrado(s) com sucesso!", Categoria.INFO);
			resultado.addMensagem(mensagem);
		} else {
			resultado.setEntidade(this.usuario);
			resultado.setErro(true);
			Mensagem mensagem = new Mensagem("Nenhum usuario foi cadastrado!", Categoria.ERRO);
			resultado.addMensagem(mensagem);
			
			for (String mensagemErro : mensagensErro) {
				resultado.addMensagem(new Mensagem(mensagemErro,Categoria.ERRO));
			}
		}

		return resultado;
	}
	
	private boolean isParametrosValidos(Map<String, String[]> parametros) {
		String[] id = parametros.get("id");
		String[] nome = parametros.get("nome");
		String[] email = parametros.get("email");
		String[] senha = parametros.get("senha");
		String[] senhaConf = parametros.get("senhaConf");
		String[] perfil = parametros.get("perfil");
		
		this.usuario = new Usuario();
		this.mensagensErro = new  ArrayList<String>();
		
		if (id != null && id.length > 0 && !id[0].isEmpty()) {
			this.usuario.setId(Integer.parseInt(id[0]));
		}
		
		if (nome == null || nome.length == 0 || nome[0].isEmpty()) {
			this.mensagensErro.add("Nome é campo obrigatório!");
		} else {
			this.usuario.setNome(nome[0]);
		}
		
		if (email == null || email.length == 0 || email[0].isEmpty()) {
			this.mensagensErro.add("Email é campo obrigatório!");
		} else {
			if (email[0].matches("[A-Za-z0-9\\._-]+@[A-Za-z]+\\.[A-Za-z]+")) {
				this.usuario.setEmail(email[0]);
			} else {
				this.mensagensErro.add("Formato inválido para o email(exemplo@exemplo.exemplo)!");
			}
		}
		
		if (senha != null && senha.length != 0 && !senha[0].isEmpty()) {
			//O admin quer mudar a senha
			
			boolean isEquals = senha[0].equals(senhaConf[0]);
			
			if (isEquals){
				this.usuario.setSenha(PasswordUtil.encryptMD5(senha[0]));
			} else {
				this.mensagensErro.add("Senhas não conferem!");
			}
		} 
		
		return false;
	}
	/*
	 private boolean isParametrosValidos(Map<String, String[]> parametros) {
		// nomes dos parâmetros vêm dos atributos 'name' das tags HTML do
		// formulário
		String[] id = parametros.get("id");
		String[] nome = parametros.get("nome");
		String[] fone = parametros.get("fone");
		String[] dataAniv = parametros.get("dataaniv");
		String idOperadora = parametros.get("operadora")[0];
		
		Operadora operadora = null;
		
		System.out.println("ID da operadora: " + idOperadora);
		
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
					System.out.println("Deu mera!");
				}
			} else {
				this.mensagensErro.add("Formato inválido para a data de aniversário(use dd/mm/aaaa)!");
			}
		}
		
		if (idOperadora != null && idOperadora != "") {
			OperadoraDAO opDao = new OperadoraDAO(PersistenceUtil.getCurrentEntityManager());
			operadora = opDao.find(Integer.parseInt(idOperadora));
			System.out.println("Operadora selecionada: " + operadora.getNome());
			this.contato.setOperadora(operadora);
		} else {
			this.mensagensErro.add("Operadora é campo obrigatório!");
		}
		
		return this.mensagensErro.isEmpty();
	}*/
}
