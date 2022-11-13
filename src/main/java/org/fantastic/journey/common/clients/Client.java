package org.fantastic.journey.common.clients;

import lombok.Data;
import lombok.With;
import org.springframework.data.annotation.Id;

import java.sql.Timestamp;

@Data
public class Client {
    public @Id @With String id;
    public String passwd;
    public String phoneNumber;
    public Timestamp createAt;

    public Client(String id, String passwd, String phoneNumber, Timestamp createAt) {
        this.id = id;
        this.passwd = passwd;
        this.phoneNumber = phoneNumber;

        if(createAt == null) this.createAt = new Timestamp(System.currentTimeMillis());
        else this.createAt = createAt;
    }
}
