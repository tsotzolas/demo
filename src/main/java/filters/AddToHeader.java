package filters;


import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class AddToHeader implements Filter {
    //    https://localhost:8443/pearl/*; https://localhost:8443/pearl/resources/assets_aerial/*  https://fonts.googleapis.com https://localhost:8443/pearl/*  https://www.youtube.com/* https://fonts.gstatic.com/* https://fonts.googleapis.com/*  *.googleapis.com;img-src *;
//    public static final String POLICY = "default-src 'unsafe-inline';img-src 'unsafe-inline'; style-src 'unsafe-inline';style-src-elem 'unsafe-inline';script-src 'unsafe-inline' ;media-src 'unsafe-inline'" ;
//    public static final String POLICY = "default-src *  data: blob: filesystem: about: ws: wss: 'unsafe-inline' 'unsafe-eval' 'unsafe-dynamic'; 'script-src * data: blob: 'unsafe-inline' 'unsafe-eval'; connect-src * data: blob: 'unsafe-inline'; img-src * data: blob: 'unsafe-inline'; frame-src * data: blob: ; style-src * data: blob: 'unsafe-inline'; font-src * data: blob: 'unsafe-inline';";
//    public static final String POLICY = "script-src 'self' 'unsafe-inline';";
//    public static final String POLICY = "script-src 'self' 'unsafe-inline';";script-src
//    public static final String POLICY = "style-src *";


    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {
        if (response instanceof HttpServletResponse) {
            HttpServletRequest httpReq = (HttpServletRequest) request;
            HttpServletResponse httpRes = (HttpServletResponse) response;


            //Content Security Policy
//            ((HttpServletResponse) response).setHeader("Content-Security-Policy", AddToHeader.POLICY);
//            ((HttpServletResponse) response).setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains; preload");

            //Add clickjacking header
            ((HttpServletResponse) response).setHeader("X-FRAME-OPTIONS", "DENY");
            chain.doFilter(httpReq, httpRes);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

}
