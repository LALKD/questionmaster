package com.hao.mainshihao;

import com.hao.mainshihao.model.enums.UserRoleEnum;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 主类测试
 *
 *  
 */
@SpringBootTest
class MainApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(UserRoleEnum.getValues());
    }

}
