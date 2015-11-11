package lwt.lab;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lwt.lab.utils.ResponseUtils;

import static lwt.lab.utils.CookiesUtils.*;

/**
 * Servlet implementation class SessionServlet
 */
@WebServlet("/SessionServlet")
public class SessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SessionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie[] cookies = request.getCookies();
		if(cookies!=null && cookies.length>0) {
		    for(Cookie cookie:cookies) {
			if(VISITOR.equals(cookie.getName())) {
			    String visitorCookieValue = cookie.getValue();
			    if(visitorCookieValue == null 
				    || "".equals(visitorCookieValue) 
				    || NEW_VISITOR.equals(visitorCookieValue)) {
				handleNewVisitor(response, cookie);
			    } else {
				handleRegularVisitor(response);
			    }
			}
		    }
		} else {
		    handleNewVisitor(response, new Cookie(VISITOR,NEW_VISITOR));
		}
	}

	private void handleRegularVisitor(HttpServletResponse response) throws IOException {
	    response.getWriter().append(ResponseUtils.titleBodyStyleHTML("LWT04-1", "<p>Welcome back!</p>", "./css/styles.css"));
	}

	private void handleNewVisitor(HttpServletResponse response, Cookie cookie) throws IOException {
	    response.getWriter().append(ResponseUtils.titleBodyStyleHTML("LWT04-1", "<p>Welcome!</p>", "./css/styles.css"));
	    cookie.setValue(REGULAR_VISITOR);
	    response.addCookie(cookie);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
