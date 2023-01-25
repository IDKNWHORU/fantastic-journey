package org.fantastic.journey.common.clients.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class Client {
    private String id;
    private String name;
    private String phoneNumber;
    private String birthAt;
    private String memberId;

    private Cabinet cabinet;
    private List<Product> products;
}