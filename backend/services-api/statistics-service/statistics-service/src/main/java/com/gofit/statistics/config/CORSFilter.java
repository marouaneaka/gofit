package com.gofit.statistics.config;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;

@WebFilter("/*") // Applique le filtre à toutes les routes
public class CORSFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialisation si nécessaire
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        
        // Ajouter les en-têtes CORS pour toutes les requêtes
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:8000");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        response.setHeader("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        
        // Gérer les requêtes préflight (OPTIONS)
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            // Optionnel : définir un délai de pré-vol
            response.setHeader("Access-Control-Max-Age", "3600");
            // Répondre avec 200 OK sans passer la requête au reste de la chaîne
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        
        // Passer la requête au prochain filtre ou au servlet cible
        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {
        // Nettoyage si nécessaire
    }
}
