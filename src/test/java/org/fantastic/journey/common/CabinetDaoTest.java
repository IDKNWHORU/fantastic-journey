package org.fantastic.journey.common;

import org.fantastic.journey.common.clients.model.Cabinet;
import org.fantastic.journey.common.clients.dao.CabinetDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

@SpringBootTest
public class CabinetDaoTest {
    private CabinetDao cabinetDao;

    @Autowired
    public CabinetDaoTest(CabinetDao cabinetDao){
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

    @Test
    public void deleteCabinet() {
        int deletedCabinetCount = cabinetDao.delete(1);

        assert deletedCabinetCount == 1;
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            cabinetDao.get(1);
        });
    }

    @Test
    public void notExistDeleteCabinetWillZero() {
        int deletedCabinetCount = cabinetDao.delete(100);

        assert deletedCabinetCount == 0;
    }
}
