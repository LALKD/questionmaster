package com.hao.mianshihao;

import com.hao.mianshihao.esdao.QuestionEsDao;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class QuestionEsDaoTest {

    @Resource
    private QuestionEsDao questionEsDao;

    @Test
    void findByUserId() {
        System.out.println(questionEsDao.findByUserId(1L));
    }
}
