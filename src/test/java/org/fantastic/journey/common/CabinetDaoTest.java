package org.fantastic.journey.common;

import org.fantastic.journey.common.clients.dao.CabinetDao;
import org.fantastic.journey.common.clients.model.Cabinet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

@SpringBootTest
public class CabinetDaoTest {
    private final CabinetDao cabinetDao;

    @Autowired
    public CabinetDaoTest(CabinetDao cabinetDao){
        this.cabinetDao = cabinetDao;
    }

    @Test
    public void createNewCabinet() {
        try {
            Cabinet cabinet1 = cabinetDao.get(1);

            int deletedCabinetCount = cabinetDao.delete(1);
            assert deletedCabinetCount == 1;
        } catch(Exception ignored) { }


        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            cabinetDao.get(1);
        });

        Cabinet cabinet = new Cabinet();

        cabinet.setId(1);
        cabinet.setStartAt("20230101");
        cabinet.setExpireAt("20231231");

        int addedCabinetCount = cabinetDao.add(cabinet);

        assert addedCabinetCount == 1;
        cabinetDao.deleteAll();
    }

    @Test
    public void notExistDeleteCabinetWillZero() {
        int deletedCabinetCount = cabinetDao.delete(100);

        assert deletedCabinetCount == 0;
    }
}
