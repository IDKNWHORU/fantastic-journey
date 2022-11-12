package org.fantastic.journey.common.users;

import java.sql.Timestamp;

public class User {
    public String id;
    public String passwd;
    public String phoneNumber;
    public Timestamp createAt;

    public User(String id, String passwd, String phoneNumber, Timestamp createAt) {
        this.id = id;
        this.passwd = passwd;
        this.phoneNumber = phoneNumber;
        this.createAt = createAt;
    }
}
