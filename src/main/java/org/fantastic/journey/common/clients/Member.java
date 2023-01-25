package org.fantastic.journey.common.clients;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Member {
    private String client;
    private int cabinet;

    public Member(String client, int cabinet) {
        this.client = client;
        this.cabinet = cabinet;
    }
}
