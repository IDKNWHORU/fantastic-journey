package org.fantastic.journey.common;

import org.fantastic.journey.common.clients.Cabinet;
import org.fantastic.journey.common.clients.CabinetDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ClientServiceTest {
    private CabinetDao cabinetDao;

    @Autowired
    public ClientServiceTest(CabinetDao cabinetDao) {
        this.cabinetDao = cabinetDao;
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
}
