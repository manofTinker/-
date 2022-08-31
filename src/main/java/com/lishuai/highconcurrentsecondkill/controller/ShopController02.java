package com.lishuai.highconcurrentsecondkill.controller;

import com.lishuai.highconcurrentsecondkill.Util.Result;
import com.lishuai.highconcurrentsecondkill.Util.ResultUtil;
import com.lishuai.highconcurrentsecondkill.service.impl.ShopServiceImpl;
import com.lishuai.highconcurrentsecondkill.service.impl.SuccesskillServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lishuai
 * @date 2022/8/28
 */

@RestController
@RequestMapping("/shop1")
public class ShopController02 {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ShopServiceImpl shopService;

    @Autowired
    private SuccesskillServiceImpl successkillService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    ReentrantLock lock = new ReentrantLock();


    @RequestMapping("/kill/{shopid}")
    public Result localkill(@PathVariable Integer shopid){

        lock.lock();

        final Integer userId = (int) (new Random().nextDouble() * (99999 - 10000 + 1)) + 10000;

        try {
            return shopService.kill(userId,shopid);
        } catch (Exception e) {
            logger.info("商品{}暂无或用户{}秒杀失败",shopid,userId);
            return ResultUtil.error(String.format("用户%s秒杀失败",userId));
        } finally {
            lock.unlock();
        }
    }

}
