/**
 * 
 */
package fy.eagle.finance.door;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import fy.eagle.finance.cache.SParam;


/**
 * session listener<br>
 * used to record who's online and kick somebody's ass
 * @author 60874
 *
 */
public class SessionListener implements HttpSessionListener {

	public static Map<String, HttpSession> sessionMap = new HashMap<String, HttpSession>();
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
	 */
	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		HttpSession session = arg0.getSession();
		sessionMap.put((String) session.getAttribute(SParam.SESSION_USER), session);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		HttpSession session = arg0.getSession();
		if (session.getAttribute(SParam.SESSION_USER) != null) {
			sessionMap.remove(session.getAttribute(SParam.SESSION_USER));
			session.invalidate();
		}
	}

}
