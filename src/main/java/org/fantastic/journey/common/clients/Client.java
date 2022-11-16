package org.fantastic.journey.common.clients;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.With;
import org.springframework.data.annotation.Id;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class Client {
    private @Id @With String id;
    private String passwd;
    private String name;
    private String phoneNumber;
    private LocalDateTime birth;
    private String portraitShot;
    @Builder.Default
    private Timestamp createAt = new Timestamp(System.currentTimeMillis());
    @Builder.Default
    private Timestamp expiredAt = new Timestamp(System.currentTimeMillis());
}
