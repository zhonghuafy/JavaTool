/**
 * 
 */
package fy.eagle.finance.door;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;

/**
 * @author Joshua
 *
 */
public class SessionFilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
//		HttpServletRequest httprequest = (HttpServletRequest)request;
//		HttpSession session = httprequest.getSession(false);
//		String requesturi = httprequest.getRequestURI();
//		if (session == null || session.getAttribute(SessionName.USER) == null) {
//			response.setContentType(SParam.RESPONSE_UTF8);
//			PrintWriter writer = response.getWriter();
//			System.out.println(requesturi);
//			writer.write("{success: false,\"msg\":\"会话已过期，请重新登录系统！\"}");
//			writer.flush();
//			writer.close();
////			RequestDispatcher dispatcher = httprequest.getRequestDispatcher("login.jsp");
////			dispatcher.forward(request, response);
//			return;
//		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
}
