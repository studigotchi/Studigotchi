package studigochi.test.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "LoginFilter", value = "/*")
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        if(!(request instanceof HttpServletRequest)) {
            chain.doFilter(request, resp);
            return;
        }



        final HttpServletRequest req = (HttpServletRequest) request;

        if (req.getRequestURI().endsWith("user/login")) {
            chain.doFilter(req, resp);
            return;
        }

        final HttpSession session = req.getSession(true);
        if (session.getAttribute("student") == null) {
            ((HttpServletResponse) resp).sendRedirect(req.getContextPath() + "/user/login");
        } else {
            chain.doFilter(request, resp);
        }
    }

}
