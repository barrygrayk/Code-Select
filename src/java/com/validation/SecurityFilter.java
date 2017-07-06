/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.validation;

import com.sun.xml.messaging.saaj.util.Base64;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;

/**
 *
 * @author Barry Gary
 */
public class SecurityFilter implements ContainerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if (requestContext.getUriInfo().getPath().contains(AUTHORIZATION_HEADER_PREFIX)) {
            List<String> authHeader = requestContext.getHeaders().get(AUTHORIZATION_HEADER);
            System.out.println("Yeah-----");
            if (authHeader != null && authHeader.size() > 0) {
                String authToken = authHeader.get(0);
                authToken = authToken.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
                String authDecode = Base64.base64Decode(authToken);
                StringTokenizer tokenizer = new StringTokenizer(authDecode, ":");
                String username = tokenizer.nextToken();

                //authenticate in db 
                if (username.equals("Admin")) {
                    System.out.println("Valid___!!");
                    return;
                }
                Response notAuthorized = Response.status(Response.Status.UNAUTHORIZED).entity("Access denied").build();
                requestContext.abortWith(notAuthorized);

            }
        }
    }

}
