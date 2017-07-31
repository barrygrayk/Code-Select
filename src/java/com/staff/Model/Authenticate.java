package com.staff.Model;

/**
 *
 * @author gray
 */
public interface Authenticate {

    int authId();

    boolean setAuthId(int id);

    String getUsername();

    boolean createUsername(String firstname, String lastname);

    boolean sethashPassword(String  hash);

    String getHashedPassword();

    boolean setSalt(byte[] salt);

    byte [] getSalt();

    boolean setStatus(int status);

    String getStatus();

    boolean setUsername(String username);

    String getToken();
    
    boolean setToken (String token);
    

}
