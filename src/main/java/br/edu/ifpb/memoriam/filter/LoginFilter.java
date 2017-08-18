package br.edu.ifpb.memoriam.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.edu.ifpb.memoriam.entity.Usuario;

@WebFilter(filterName = "LoginFilter", urlPatterns = { "/contato/*", "/operadora/*", "/usuario/*" })
public class LoginFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession httpSession = httpRequest.getSession(false);

		if (httpSession != null) {
			Usuario loginUser = (Usuario) httpSession.getAttribute("usuario");
			if (loginUser == null) {
				redirectLogin(httpRequest, httpResponse);
				return;
			}
		} else {
			redirectLogin(httpRequest, httpResponse);
			return;
		}
		chain.doFilter(request, response);

	}

	private void redirectLogin(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException {
		String baseUrl = httpRequest.getContextPath();
		String paginaLogin = baseUrl + "/login/login.jsp";
		httpResponse.sendRedirect(httpResponse.encodeRedirectURL(paginaLogin));
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	@Override
	public void destroy() {
	}

}
