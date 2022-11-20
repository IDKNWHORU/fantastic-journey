package org.fantastic.journey.common.clients;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.With;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@Builder
public class Client {
    private @Id @With String id;
    private String name;
    private String phoneNumber;
    private String birth;
    private String photo;
    private Number memberId;
    private Number cabinet;
    private String expiredAt;
}
