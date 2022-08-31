package com.lishuai.highconcurrentsecondkill;

import com.lishuai.highconcurrentsecondkill.mapper.ShopMapper;
import com.lishuai.highconcurrentsecondkill.pojo.Shop;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HighConcurrentSecondKillApplicationTests {

    @Autowired
    private ShopMapper shopMapper;

    @Test
    void contextLoads() {

        Shop shop = shopMapper.querySecondKillForUpdate(3);

        System.out.println(shop);


    }

}
