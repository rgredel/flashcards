package pl.gredel.flashcards.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AuthenticationFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest)req;
		if(request.getRequestURI().startsWith("/flashcards")||
				request.getRequestURI().startsWith("/decks")||
				request.getRequestURI().startsWith("/home")){
			HttpSession session = request.getSession();
			if(session.getAttribute("username")==null){
				request.getRequestDispatcher("/html/index.html").forward(request, res);
			}
			
		}

		if(request.getRequestURI().startsWith("/register")||
				request.getRequestURI().startsWith("/login")){
			HttpSession session = request.getSession();
			if(session.getAttribute("username")!=null){
				request.getRequestDispatcher("/html/home.jsp").forward(request, res);
			}

		}

		chain.doFilter(request, res);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
