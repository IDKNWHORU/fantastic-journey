package org.fantastic.journey.common.clients;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Cabinet {
    private int id;
    private String startAt;
    private String expireAt;
}