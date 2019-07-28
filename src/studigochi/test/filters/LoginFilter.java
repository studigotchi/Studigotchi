package studigochi.test.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * {@inheritDoc}<br/>
 * Simple Authentication filter: <br/>
 *     <ol>
 *         <li>Allow calls to graphics or to the login/register page</li>
 *         <li>Everyone who is not logged in is redirected to the login page</li>
 *     </ol>
 */
@WebFilter(filterName = "LoginFilter", value = "/*")
public class LoginFilter implements Filter {

    public void destroy() {
        //We don't need any cleanup here
    }

    /**
     * See {@link LoginFilter}
     */
    public void doFilter(ServletRequest request, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        //If the request is no HttpServletRequest then something is very wrong, so let's just allow them through.
        //Usually this should never be the case but in case some internals change we accept these calls.
        if (!(request instanceof HttpServletRequest)) {
            chain.doFilter(request, resp);
            return;
        }


        //Downcast so that we can access the request path
        final HttpServletRequest req = (HttpServletRequest) request;

        //Allow resources (css, png, ico files) and calls to the login page through.
        final String requestURI = req.getRequestURI();
        if (requestURI.endsWith("/login") || requestURI.endsWith(".css") || requestURI.endsWith(".png") || requestURI.endsWith(".ico")) {
            chain.doFilter(req, resp);
            return;
        }

        //Get session or create it if nonexistent
        final HttpSession session = req.getSession(true);

        //Logged in?
        if (session.getAttribute("student") == null) {
            //If not, redirect to login page
            //Unchecked downcast, because we already know it's a servletRequest,
            // if it were no servletResponse then something would be seriously wrong
            ((HttpServletResponse) resp).sendRedirect(req.getContextPath() + "/login");
        } else {
            //Already logged in, allow through
            chain.doFilter(request, resp);
        }
    }


    @Override
    public void init(FilterConfig filterConfig) {
        //Nothing to do here
    }
}
