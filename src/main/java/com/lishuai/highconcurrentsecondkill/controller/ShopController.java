package com.lishuai.highconcurrentsecondkill.controller;

import com.lishuai.highconcurrentsecondkill.Util.*;
import com.lishuai.highconcurrentsecondkill.vo.SecondKillEvent;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lishuai.highconcurrentsecondkill.pojo.Shop;
import com.lishuai.highconcurrentsecondkill.pojo.Successkill;
import com.lishuai.highconcurrentsecondkill.service.impl.ShopServiceImpl;
import com.lishuai.highconcurrentsecondkill.service.impl.SuccesskillServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lishuai
 * @date 2022/8/19
 */
@RestController
@RequestMapping("/shop")
public class ShopController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ShopServiceImpl shopService;

    @Autowired
    private SuccesskillServiceImpl successkillService;

    //重入锁
    ReentrantLock lock = new ReentrantLock();

    @Autowired
    private StringRedisTemplate redisTemplate;

    @RequestMapping("/a")
    public String fallback(){
        return "121";
    }

    //获取所有商品
    @RequestMapping("/getAll")
    public Map<String, Object> getall() {
        return shopService.getMap(new QueryWrapper<Shop>());
    }

    @RequestMapping("/kill/{shopid}")
    public Result kill(@PathVariable Integer shopid) {
        //随机生成一个userid
        final Integer userId = (int) (new Random().nextDouble() * (99999 - 10000 + 1)) + 10000;

        lock.lock();

        try {
            Successkill killid = successkillService.getById(userId);
            Shop shop = shopService.getById(shopid);
            if (shop.getNumber() >= 1 && killid == null) {
                shopService.kill(userId, shopid);
            }else {
                logger.info("商品{}暂无或用户 {} 秒杀失败",shopid ,userId);
                return ResultUtil.error(String.format("用户%s秒杀失败", userId));
            }
            return ResultUtil.success(String.format("用户%s秒杀成功", userId));
        } catch (Exception e) {
            logger.info("商品{}暂无或用户 {} 秒杀失败",shopid ,userId);
            return ResultUtil.error(String.format("用户%s秒杀失败", userId));
        } finally {
            lock.unlock();
        }

    }

    @PostMapping("/start/aop/{shopid}")
    public com.lishuai.highconcurrentsecondkill.common.Result startAop(@PathVariable Integer shopid){

        try {
            logger.info("秒杀方式二");
            final Integer userId = (int) (new Random().nextDouble() * (99999 - 10000 + 1)) + 10000;
            Result result = null;
            result = shopService.AopKill(shopid, userId);
            if(result != null){
                logger.info("用户:{}--{}",userId,result.getMessage());
            }else {
                logger.info("用户:{}--{}",userId,"人太多了,抵不住");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return com.lishuai.highconcurrentsecondkill.common.Result.ok();
    }

    //悲观锁数据库
    @RequestMapping("/kill3/{shopid}")
    public Result lockkill(@PathVariable Integer shopid){

        final Integer userId = (int) (new Random().nextDouble() * (99999 - 10000 + 1)) + 10000;

        try {
            return shopService.kill(userId,shopid);
        } catch (Exception e) {
            logger.info("商品{}暂无或用户{}已秒杀", shopid, userId);
            return ResultUtil.error(String.format("用户%s秒杀失败",userId));
        }
    }

    //阻塞队列
    @RequestMapping("/kill4/{shopid}")
    public Result QueueKill(@PathVariable Integer shopid){

        Successkill successkill = new Successkill();

        try {
            final Integer userId = (int) (new Random().nextDouble() * (99999 - 10000 + 1)) + 10000;

            successkill.setKillid(shopid);
            successkill.setUserid(userId);

            Boolean produce = SecondKillQueue.getSkillQueue().produce(successkill);

            //虽然进入队列但是不一定秒杀成功
            if(produce){
                logger.info("用户:{}{}",successkill.getUserid(),"秒杀成功");
                }else{
                    logger.info("用户:{}{}",userId,"秒杀失败");
                }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResultUtil.success(String.format("用户%s秒杀成功", successkill.getUserid()));

    }

    @RequestMapping("/kill5/{shopid}")
    public Result lockKill(@PathVariable Integer shopid){
        final Integer userId = (int) (new Random().nextDouble() * (99999 - 10000 + 1)) + 10000;
        try {
            logger.info("开始秒杀方式七...");
            SecondKillEvent kill = new SecondKillEvent();
            kill.setSekillId(userId);
            kill.setUserId(shopid);
            DisruptorUtil.producer(kill);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.success(String.format("用户%s秒杀成功", userId));
    }

}
