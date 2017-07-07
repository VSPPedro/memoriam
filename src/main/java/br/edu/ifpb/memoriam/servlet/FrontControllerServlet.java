package br.edu.ifpb.memoriam.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.edu.ifpb.memoriam.entity.Contato;
import br.edu.ifpb.memoriam.entity.Operadora;
import br.edu.ifpb.memoriam.entity.Usuario;
import br.edu.ifpb.memoriam.facade.ContatoController;
import br.edu.ifpb.memoriam.facade.OperadoraController;
import br.edu.ifpb.memoriam.facade.Resultado;

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
				
			case "conopr":
				List<Operadora> operadoras = operadoraCtrl.consultar();
				request.setAttribute("operadoras", operadoras);
				proxPagina = "operadora/consulta.jsp";
				break;
				
			case "edtopr":
				Operadora operadora = operadoraCtrl.buscar(request.getParameterMap());
				request.setAttribute("operadora", operadora);
				proxPagina = "operadora/cadastro.jsp";
				break;
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
		
		Resultado resultado = null;
		String paginaSucesso = "controller.do?op=conctt";
		String paginaErro = "contato/cadastro.jsp";
		String proxPagina = null;
		
		// Pega o usuário logado
		HttpSession session= request.getSession();
		Usuario usuario= (Usuario) session.getAttribute("usuario");
		
		switch (operacao) {
			// Login/logout
			case "login":
				paginaSucesso= "controller.do?op=conctt";
				paginaErro= "controller.do?op=login";
				resultado= loginCtrl.isValido(request.getParameterMap());
				if(resultado.isErro()) {
					request.setAttribute("msgs", resultado.getMensagens());
					proxPagina= paginaErro;
				} else {
					session.setAttribute("usuario", (Usuario) resultado.getEntidade());
					proxPagina= paginaSucesso;
				}
				break;
			
			case"logout":
				proxPagina = "login/login.jsp";
				session.invalidate();
				if (resultado == null){
					resultado = new Resultado();
				}
				
				resultado.setErro(false);
				
				break;	
			
			case "cadctt":
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
				
			case "exlctt":
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
					proxPagina = "controller.do?op=conopr";
					request.setAttribute("msgs", resultado.getMensagens());
				} else {
					request.setAttribute("operadora", (Operadora) resultado.getEntidade());
					request.setAttribute("msgs", resultado.getMensagens());
					proxPagina = "operadora/cadastro.jsp";;
				}
				break;
			
			case "exlopr":
				resultado = operadoraCtrl.deletar(request.getParameterMap());
				paginaSucesso = "controller.do?op=conopr";
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

			default:
				request.setAttribute("erro", "Operação não especificada no servlet!");
				proxPagina = "../erro/erro.jsp";
		}
		
		if (resultado.isErro()) {
			RequestDispatcher dispatcher = request.getRequestDispatcher(proxPagina);
			dispatcher.forward(request, response);
		} else {
			response.sendRedirect(proxPagina);
		}
	}

}
