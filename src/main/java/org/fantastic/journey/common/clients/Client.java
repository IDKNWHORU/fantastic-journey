package org.fantastic.journey.common.clients;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Client {
    private String id;
    private String name;
    private String phoneNumber;
    private String birthAt;
    private String memberId;

    private Cabinet cabinet;
    private List<Product> products;
}