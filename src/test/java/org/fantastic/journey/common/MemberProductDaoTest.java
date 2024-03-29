package org.fantastic.journey.common;

import org.fantastic.journey.common.clients.model.Product;
import org.fantastic.journey.common.clients.dao.MemberProductDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberProductDaoTest {
    private final MemberProductDao productDao;

    @Autowired
    public MemberProductDaoTest(MemberProductDao productDao) {
        this.productDao = productDao;
    }

    @Test
    public void createProduct() {
        Product product = new Product();
        product.setName("yoga");
        product.setStartAt("20230101");
        product.setExpireAt("20230101");

        assert productDao.add("550e8400-e29b-41d4-a716-446655440000", product) == 1;
        productDao.deleteAll();
    }
}
