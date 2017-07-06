package com.validation;

/**
 *
 * @author Barry Gray
 */
public interface AuthTokens {
    String creatJWt (String id, String issuer, String subject, long ttlMillis);
    void verifyToken (String token);
    
    
}
