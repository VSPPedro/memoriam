package br.edu.ifpb.memoriam.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.edu.ifpb.memoriam.bean.UtilBean;
import br.edu.ifpb.memoriam.dao.ContatoDAO;
import br.edu.ifpb.memoriam.dao.PersistenceUtil;
import br.edu.ifpb.memoriam.dao.UsuarioDAO;
import br.edu.ifpb.memoriam.entity.Contato;
import br.edu.ifpb.memoriam.entity.Perfil;
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
		
		boolean isOldUser = (id != null && id.length > 0 && !id[0].isEmpty());
		
		if (isOldUser) {
			//O usuario é um usuario ja inserido no banco
			//O admin quer editar tal usuario
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
			if (email[0].matches("[A-Za-z0-9\\._-]+@[A-Za-z0-9]+(\\.[A-Za-z]+)*")) {
				this.usuario.setEmail(email[0]);
			} else {
				this.mensagensErro.add("Formato inválido para o email(exemplo@exemplo.exemplo)!");
			}
		}
		
		boolean isPassword = (senha != null && senha.length != 0 && !senha[0].isEmpty());
		
		if (isPassword && isOldUser) {
			//O admin deseja mudar a senha de um usuario antigo
			//Mudar a senha é opcional no caso de usuario antigo
			boolean isEquals = senha[0].equals(senhaConf[0]);
			
			if (isEquals){
				this.usuario.setSenha(PasswordUtil.encryptMD5(senha[0]));
			} else {
				this.mensagensErro.add("Senhas não conferem!");
			}
			
		} else if (!isOldUser){
			//Novo usario
			if (isPassword) {
				
				boolean isEquals = senha[0].equals(senhaConf[0]);
				
				if (isEquals){
					this.usuario.setSenha(PasswordUtil.encryptMD5(senha[0]));
				} else {
					this.mensagensErro.add("Senhas não conferem!");
				}
				
			} else {
				this.mensagensErro.add("Campo senha e campo nova Senha são obrigatorios, pois se trata de um usuário novo!");
			}
		} else if (isOldUser) {
			//Recuperar senha antiga do usuario e inserir
			//O usuario é antigo e o admin não quer mudar a senha
			Usuario usuarioEdit = buscar(id[0]);
			this.usuario.setSenha(usuarioEdit.getSenha());
		}
		
		if (perfil == null || perfil.length == 0 || perfil[0].isEmpty()) {
			this.mensagensErro.add("Perfil é campo obrigatório!");
		} else {
			UtilBean utilBean = new UtilBean();
			Perfil[] perfis = utilBean.getPerfisEnum();
			
			for (Perfil perfilArray : perfis) {
				
				if (perfil[0].equals(perfilArray.getNome())){
					this.usuario.setPerfil(perfilArray);
				}
			}
			
			//Problemas com enconde no mommento de 'parametros.get'
			if (perfil[0].equals("BÃ¡sico")) {
				this.usuario.setPerfil(Perfil.BASIC);
			}
			
			//Validar
			if (this.usuario.getPerfil() == null) {
				this.mensagensErro.add("Perfil é campo obrigatório!");
			}
		}
		
		this.usuario.setAtivo(true);
		
		return this.mensagensErro.isEmpty();
	}

	public Resultado deletar(Map<String, String[]> parameterMap) {
		Resultado resultado = new Resultado();
		String[] idsDosUsuariosSelecionados = parameterMap.get("delids");
		
		if (idsDosUsuariosSelecionados.length > 0) {
			
			for (String id : idsDosUsuariosSelecionados) {
				
				Usuario usuario = buscar(id);
				
				ContatoDAO daoContato = new ContatoDAO(PersistenceUtil.getCurrentEntityManager());
				List<Contato> contatos = daoContato.findAllFromUser(usuario);
				
				daoContato.beginTransaction();
				
				for (Contato contato : contatos) {
					daoContato.delete(contato);
				}
				
				daoContato.commit();
				
				UsuarioDAO dao = new UsuarioDAO(PersistenceUtil.getCurrentEntityManager());
				dao.beginTransaction();
				
				//Agora pode deletar usuario
				dao.delete(usuario);
				dao.commit();
				
			}
			
			resultado.setErro(false);
			Mensagem mensagem = new Mensagem("Contato(s) deletado(s) com sucesso!", Categoria.INFO);
			resultado.addMensagem(mensagem);
		} else {
			resultado.setErro(true);
			Mensagem mensagem = new Mensagem("Nenhum contato foi selecionado!", Categoria.ERRO);
			resultado.addMensagem(mensagem);
		}
		
		return resultado;
	}
	
	public Usuario buscar(String id){
		List<Usuario> usuarios = consultar();
		
		for (Usuario usuario : usuarios) {
			if (usuario.getId().toString().equals(id)){
				return usuario;
			}
		}
		
		return null;
	}
}
