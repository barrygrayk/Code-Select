package com.validation;

import java.io.IOException;
import javax.faces.application.NavigationHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
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
@WebFilter(filterName = "AuthFilter", urlPatterns = {"*.xhtml"})
public class UrlFilter implements Filter {

    public UrlFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        System.out.println("FILTERING__");
        try {

            HttpServletRequest reqt = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;
            HttpSession ses = reqt.getSession(false);
            String reqURI = reqt.getRequestURI();
            System.out.println("==========" + reqURI);
            boolean isRegister = reqt.getRequestURI().contains("/register.xhtml");
            System.out.println(")))))))reg))))))))" + isRegister);
            if (isRegister) {
               /* System.out.println("contains  rg___");
                System.out.println("length___" + reqt.getContextPath().length());
                System.out.println(reqt.getContextPath().indexOf("/eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2NiIsImlhdCI6MTQ5OTI3OTcxOCwic3ViIjoiYmFrYXA4MCIsImlzcyI6Ik90aGFudGlsZVdlYkFwcGxpY2F0aW9uIn0.uOtX5JlQvMb0DjYb8Yx3qSsEAQa_r1SFiSukMJC3U5M"));
                resp.sendRedirect("/OnthatileWebApplication/faces/register.xhtml");
                String replaceAll = reqt.getRequestURI().replaceAll(reqt.getContextPath(), "/OnthatileWebApplication/faces/register.xhtml");
                reqURI = "/OnthatileWebApplication/faces/register.xhtml";*/
                //if (reqURI.contains("/register.xhtml") || (ses != null && ses.getAttribute("username") != null) || reqURI.contains("/public/") || reqURI.contains("javax.faces.resource")) {
                    System.err.println("When garnting___" + reqURI);
                    System.out.println("ganting register__");
                    System.out.println(" Req " + request.toString() + " respose " + response.toString());

                    //chain.doFilter(request, response);
                    FacesContext context = FacesContext.getCurrentInstance();
                    NavigationHandler myNav = context.getApplication().getNavigationHandler();
                    myNav.handleNavigation(context, null, "/OnthatileWebApplication/faces/register.xhtml");
                    /*response = (HttpServletResponse) context.getExternalContext().getResponse();
                    resp.sendRedirect("/OnthatileWebApplication/faces/register.xhtml");*/
                    UIViewRoot vr = context.getViewRoot();
                    if (vr != null) {
                        // Get the URL where to redirect the user
                        String url = context.getExternalContext().getRequestContextPath();
                        url = url + "/" + vr.getViewId().replace(".xhtml", ".jsf");
                        Object obj = context.getExternalContext().getResponse();
                        if (obj instanceof HttpServletResponse) {
                            resp = (HttpServletResponse) obj;
                            try {
                                // Redirect the user now.
                                resp.sendRedirect(resp.encodeURL(url));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    //}
                } else {
                    System.out.println("reg redirect__");

                    resp.sendRedirect(reqt.getContextPath() + "/faces/register.xhtml");

                }

            }

            System.out.println("_____________Seesion_____________________" + reqURI);
            if (reqURI.contains("/login.xhtml") || (ses != null && ses.getAttribute("username") != null) || reqURI.contains("/public/") || reqURI.contains("javax.faces.resource")) {
                System.err.println("When garnting___" + reqURI);
                System.out.println("ganting login__");
                System.out.println(" Req " + request.toString() + " respose " + response.toString());

                chain.doFilter(request, response);
            } else {
                System.out.println("login redirect__");
                //if(reqURI.contains(""))
                if (isRegister) {
                    resp.sendRedirect("/OnthatileWebApplication/faces/register.xhtml");

                }

                resp.sendRedirect(reqt.getContextPath() + "/faces/login.xhtml");

            }


            /*     if (isRegister){
                System.out.println("Replacing__");
                // String replaceAll = reqt.getRequestURI().replaceAll(reqt.getContextPath() , "/OnthatileWebApplication/faces/register.xhtml");
                 //resp.sendRedirect(replaceAll); 
                 chain.doFilter(request, response);
                       
            }*/
 /*         String regUrl = reqt.getRequestURI();
            if (reqURI.indexOf("/register.xhtml") >= 0 || reqURI.indexOf("/public/") >= 0 || reqURI.contains("javax.faces.resource")) {
                System.err.println("hrererererre))))))))))))");
                chain.doFilter(request, response);
                //resp.sendRedirect(reqt.getContextPath() + "/faces/register.xhtml");
            }else{
                 resp.sendRedirect("/OnthatileWebApplication/faces/register.xhtml");
            }
             */
        } catch (IOException | ServletException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void destroy() {

    }
}
