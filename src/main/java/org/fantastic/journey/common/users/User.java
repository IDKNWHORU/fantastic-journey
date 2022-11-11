package org.fantastic.journey.common.users;

import java.sql.Timestamp;

public class User {
    public int seq;
    public String email;
    public String passwd;
    public int loginCount;
    public Timestamp lastLoginAt;
    public Timestamp createAt;

    public User(String email, String passwd) {
        this.email = email;
        this.passwd = passwd;
    }
}
