package org.fantastic.journey.common.clients;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Product {
    private String name;
    private String start_at;
    private String expire_at;
}
