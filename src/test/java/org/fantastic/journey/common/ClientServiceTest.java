package org.fantastic.journey.common;

import org.fantastic.journey.common.clients.Cabinet;
import org.fantastic.journey.common.clients.CabinetDao;
import org.fantastic.journey.common.clients.Member;
import org.fantastic.journey.common.clients.MemberDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ClientServiceTest {
    private CabinetDao cabinetDao;
    private MemberDao memberDao;

    @Autowired
    public ClientServiceTest(CabinetDao cabinetDao, MemberDao memberDao){
        this.cabinetDao = cabinetDao;
        this.memberDao = memberDao;
    }

    @Test
    public void createNewCabinet() {
        Cabinet cabinet = new Cabinet();

        cabinet.setId(1);
        cabinet.setStartAt("20230101");
        cabinet.setExpireAt("20231231");

        int addedCabinetCount = cabinetDao.add(cabinet);

        assert addedCabinetCount == 1;
    }

    @Test
    public void createMember() {
        Member member = new Member("550e8400-e29b-41d4-a716-446655440000", 1);

        assert memberDao.add(member) == 1;
    }
}
