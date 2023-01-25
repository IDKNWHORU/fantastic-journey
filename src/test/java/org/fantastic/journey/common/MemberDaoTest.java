package org.fantastic.journey.common;

import org.fantastic.journey.common.clients.model.Member;
import org.fantastic.journey.common.clients.dao.MemberDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberDaoTest {

    private MemberDao memberDao;

    @Autowired
    public MemberDaoTest(MemberDao memberDao){
        this.memberDao = memberDao;
    }

    @Test
    public void createMember() {
        Member member = new Member("550e8400-e29b-41d4-a716-446655440000", 1);

        assert memberDao.add(member) == 1;
    }
}
