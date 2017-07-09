package br.edu.ifpb.memoriam.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.edu.ifpb.memoriam.entity.Contato;
import br.edu.ifpb.memoriam.entity.Operadora;
import br.edu.ifpb.memoriam.entity.Usuario;
import br.edu.ifpb.memoriam.facade.ContatoController;
import br.edu.ifpb.memoriam.facade.LoginController;
import br.edu.ifpb.memoriam.facade.OperadoraController;
import br.edu.ifpb.memoriam.facade.Resultado;
import br.edu.ifpb.memoriam.facade.UsuarioController;

/**
 * Servlet implementation class FrontControllerServlet
 */
@WebServlet("/controller.do")
public class FrontControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FrontControllerServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		ContatoController contatoCtrl = new ContatoController();
		OperadoraController operadoraCtrl = new OperadoraController();
		UsuarioController usuarioCtrl = new UsuarioController();
		
		String proxPagina = null;
		this.getServletContext().removeAttribute("msgs");
		String operacao = request.getParameter("op");

		if (operacao == null) {
			this.getServletContext().setAttribute("msgs", "Operação (op) não especificada na requisição!");
			response.sendRedirect(request.getHeader("Referer"));
			return;
		}
		
		// Pega o usuário logado
		HttpSession session= request.getSession();
		Usuario usuario= (Usuario) session.getAttribute("usuario");
		
		System.out.println("Operacao no doget: " + operacao);

		switch (operacao) {
		
			case "conctt":
				List<Contato> contatos = contatoCtrl.consultar(usuario);
				request.setAttribute("contatos", contatos);
				proxPagina = "contato/consulta.jsp";
				break;
				
			case "edtctt":
				Contato contato = contatoCtrl.buscar(request.getParameterMap());
				request.setAttribute("contato", contato);
				proxPagina = "contato/cadastro.jsp";
				break;
				
			case "conoper":
				List<Operadora> operadoras = operadoraCtrl.consultar();
				request.setAttribute("operadoras", operadoras);
				proxPagina = "operadora/consulta.jsp";
				break;
				
			case "edtopr":
				Operadora operadora = operadoraCtrl.buscar(request.getParameterMap());
				request.setAttribute("operadora", operadora);
				proxPagina = "operadora/cadastro.jsp";
				break;
			
			//busctt	
			case "busctt":
				List<Contato> contatosFiltrados = contatoCtrl.buscar(request.getParameterMap(), usuario);
				request.setAttribute("contatos", contatosFiltrados);
				proxPagina = "contato/consulta.jsp";
				break;
				
			case "conuser":
				List<Usuario> usuarios = usuarioCtrl.consultar();
				request.setAttribute("usuarios", usuarios);
				proxPagina = "usuario/consulta.jsp";
				break;
			
			case "edtuser":
				Usuario usuarioEdit = usuarioCtrl.buscar(request.getParameterMap());
				request.setAttribute("usuarioEdit", usuarioEdit);
				proxPagina = "usuario/cadastro.jsp";
				break;
			
				
			case "login":
				proxPagina= "login/login.jsp";
				break;
				
			default:
				request.setAttribute("erro", "Operação não especificada no servlet!");
				proxPagina = "../erro/erro.jsp";
		}
		
		//Caso nenhum usuario tenha efetuado login
		if (usuario == null) {
			proxPagina = "login/login.jsp";
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(proxPagina);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("Teste DoPost!");
		
		this.getServletContext().removeAttribute("msgs");
		String operacao = request.getParameter("op");

		if (operacao == null) {
			this.getServletContext().setAttribute("msgs",
					new String[] { "Operação (op) não especificada na requisição!" });
			response.sendRedirect(request.getHeader("Referer"));
			return;
		}

		ContatoController contatoCtrl = new ContatoController();
		OperadoraController operadoraCtrl = new OperadoraController();
		LoginController loginCtrl= new LoginController();
		UsuarioController usuarioCtrl = new UsuarioController();
		
		Resultado resultado = null;
		String paginaSucesso = "controller.do?op=conctt";
		String paginaErro = "contato/cadastro.jsp";
		String proxPagina = null;
		
		// Pega o usuário logado
		HttpSession session= request.getSession();
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		
		if (usuario != null){
			System.out.println("Usuario: " + usuario.getNome());
		} else {
			System.out.println("Sessao do usuario vai ser iniciada!");
		}
		
		System.out.println("Operacao no dopost: " + operacao);
		
		switch (operacao) {
			// Login/logout
			case "login":
				paginaSucesso= "controller.do?op=conctt";
				paginaErro= "login/login.jsp";
				resultado= loginCtrl.isValido(request.getParameterMap());
				System.out.println(resultado.getMensagens());
				if(resultado.isErro()) {
					request.setAttribute("msgs", resultado.getMensagens());
					proxPagina= paginaErro;
				} else {
					session.setAttribute("usuario", (Usuario) resultado.getEntidade());
					proxPagina= paginaSucesso;
					// trata o lembrar
					String lembrar= request.getParameter("lembrar");
					
					if(lembrar!= null) {
						usuario = (Usuario) session.getAttribute("usuario");
						Cookie c= new Cookie("loginCookie", usuario.getEmail());
						c.setMaxAge(-1);response.addCookie(c);
					} else {
						for(Cookie cookie: request.getCookies()) {
							if(cookie.getName().equals("loginCookie")) {
								cookie.setValue(null);
								cookie.setMaxAge(0);
								response.addCookie(cookie);
							}
						}
					}
				}
				break;
			
			case "logout":
				proxPagina = "login/login.jsp";
				session.invalidate();
				if (resultado == null){
					resultado = new Resultado();
				}
				resultado.setErro(false);
				break;	
							
			case "cadctt":
				System.out.println("Entrou aqui!");
				resultado = contatoCtrl.cadastrar(request.getParameterMap(), usuario);
				if (!resultado.isErro()) {
					proxPagina = paginaSucesso;
					request.setAttribute("msgs", resultado.getMensagens());
				} else {
					request.setAttribute("contato", (Contato) resultado.getEntidade());
					request.setAttribute("msgs", resultado.getMensagens());
					proxPagina = paginaErro;
				}
				break;
				
			case "delctt":
				resultado = contatoCtrl.deletar(request.getParameterMap());
				paginaSucesso = "controller.do?op=conctt";
				paginaErro = proxPagina;
				if (!resultado.isErro()) {
					proxPagina = paginaSucesso;
					request.removeAttribute("selecionarContato");
					request.setAttribute("msgs", resultado.getMensagens());
				} else {
					request.setAttribute("msgs", resultado.getMensagens());
					proxPagina = paginaErro;
				}
				break;
			
			case "cadopr":
				resultado = operadoraCtrl.cadastrar(request.getParameterMap());
				if (!resultado.isErro()) {
					proxPagina = "controller.do?op=conoper";
					request.setAttribute("msgs", resultado.getMensagens());
				} else {
					request.setAttribute("operadora", (Operadora) resultado.getEntidade());
					request.setAttribute("msgs", resultado.getMensagens());
					proxPagina = "operadora/cadastro.jsp";;
				}
				break;
			
			case "delopr":
				System.out.println("Deletar operadora!");
				resultado = operadoraCtrl.deletar(request.getParameterMap());
				paginaSucesso = "controller.do?op=conoper";
				paginaErro = paginaSucesso;
				if (!resultado.isErro()) {
					proxPagina = paginaSucesso;
					request.removeAttribute("selecionarOperadora");
					request.setAttribute("msgs", resultado.getMensagens());
				} else {
					request.setAttribute("msgs", resultado.getMensagens());
					proxPagina = paginaErro;
				}
				break;
			
			case "conoper":
				List<Operadora> operadoras = operadoraCtrl.consultar();
				request.setAttribute("operadoras", operadoras);
				proxPagina = "operadora/consulta.jsp";
				resultado = (Resultado) request.getAttribute("resultado");
				break;	
				
			case "caduser":				
				resultado = usuarioCtrl.cadastrar(request.getParameterMap());
				if (!resultado.isErro()) {
					proxPagina = "controller.do?op=conuser";
					request.setAttribute("msgs", resultado.getMensagens());
				} else {
					request.setAttribute("usuarioEdit", (Usuario) resultado.getEntidade());
					request.setAttribute("msgs", resultado.getMensagens());
					proxPagina = "usuario/cadastro.jsp";
				}
				break;
			
			//deluser	
			case "deluser":				
				resultado = usuarioCtrl.deletar(request.getParameterMap());
				paginaSucesso = "controller.do?op=conuser";
				paginaErro = paginaSucesso;
				if (!resultado.isErro()) {
					proxPagina = "controller.do?op=conuser";
					request.setAttribute("msgs", resultado.getMensagens());
				} else {
					request.setAttribute("usuarioEdit", (Usuario) resultado.getEntidade());
					request.setAttribute("msgs", resultado.getMensagens());
					proxPagina = paginaErro;
				}
				break;
				
			default:
				request.setAttribute("erro", "Operação não especificada no servlet!");
				proxPagina = "../erro/erro.jsp";
		}
		
		System.out.println("Resultado:  " + resultado);
		System.out.println("Existe erro no FrontController? " + resultado.isErro());
		if (resultado.isErro()) {
			request.setAttribute("resultado", resultado);
			RequestDispatcher dispatcher = request.getRequestDispatcher(proxPagina);
			dispatcher.forward(request, response);
		} else {
			response.sendRedirect(proxPagina);
		}
	}

}
