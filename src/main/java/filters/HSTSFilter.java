package filters;


import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class HSTSFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) res;

        if (!req.isSecure()){
            HttpServletRequest req1 = (HttpServletRequest)req;
            HttpServletResponse resp1 = (HttpServletResponse)res;
                String url = "https://" + req.getServerName()
                        + req1.getContextPath() + req1.getServletPath();
                if (req1.getPathInfo() != null) {
                    url += req1.getPathInfo();
                }
                resp1.sendRedirect(url);
            } else {
            resp.setHeader("Strict-Transport-Security", "max-age=31622400; includeSubDomains");
            chain.doFilter(req, resp);
        }
    }

    @Override
    public void destroy() {

    }
}
