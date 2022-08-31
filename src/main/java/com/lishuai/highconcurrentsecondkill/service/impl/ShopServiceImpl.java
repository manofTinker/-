package com.lishuai.highconcurrentsecondkill.service.impl;

import com.lishuai.highconcurrentsecondkill.Util.Result;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lishuai.highconcurrentsecondkill.Util.ResultUtil;
import com.lishuai.highconcurrentsecondkill.aspect.ServerLock;
import com.lishuai.highconcurrentsecondkill.mapper.ShopMapper;
import com.lishuai.highconcurrentsecondkill.mapper.SuccesskillMapper;
import com.lishuai.highconcurrentsecondkill.pojo.Shop;
import com.lishuai.highconcurrentsecondkill.pojo.Successkill;
import com.lishuai.highconcurrentsecondkill.service.IShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lishuai
 * @date 2022/8/19
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements IShopService {

    private Integer shopnum = 0;

    ReentrantLock reentrantLock = new ReentrantLock();

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private SuccesskillMapper successkillMapper;

    @Autowired
    private SuccesskillServiceImpl successkillService;

    @Autowired
    private ShopServiceImpl shopService;

    @Override
    public Result kill(Integer userId, Integer shopid) throws Exception {

        //reentrantLock.lock();
        try {

            Shop sid = shopService.getById(shopid);

            Successkill sucid = successkillService.getById(userId);

            if (sid.getNumber() >= 1 && sucid == null) {

                sid.setNumber(sid.getNumber() - 1);
                boolean update = shopService.updateById(sid);

                if (!update) {
                    throw new Exception("库存更新异常");
                }
                //模拟订单产生
                boolean save = successkillService.save(new Successkill().setKillid(shopid).setUserid(userId).setCreate_time(LocalDateTime.now()).setStatus(1).setShopnum(this.shopnum++));

                if (!save) {
                    throw new Exception("秒杀失败,创建订单异常");
                }

            } else {

                logger.info("商品{}暂无或用户{}已秒杀", shopid, userId);
                throw new Exception("秒杀失败");
            }

        } catch (Exception e) {

            throw new Exception("秒杀失败");

        } finally {
            //reentrantLock.unlock();
        }
        return ResultUtil.success(String.format("用户%s秒杀成功", userId));
    }

    @ServerLock
    public Result AopKill(Integer userId,Integer shopId) throws Exception {

        try {
            Successkill payid = successkillService.getById(shopId);
            Shop shop = shopService.getById(shopId);
            if(shop.getNumber() >=1  && payid == null){

                shop.setNumber(shop.getNumber()-1);
                boolean b = shopService.updateById(shop);

                if(!b){
                    throw new Exception("修改库存失败");
                }

                boolean save = successkillService.save(
                        new Successkill()
                                .setKillid(shopId)
                                .setUserid(userId)
                                .setCreate_time(LocalDateTime.now())
                                .setStatus(1)
                                .setShopnum(this.shopnum++));
                
                if(!save){
                    throw new Exception("创建顶大失败");
                }
            }else {
                logger.info("商品{}暂无或用户{}已秒杀", shopId, userId);
                throw new Exception("秒杀失败");
            }
        } catch (Exception e) {
            throw new Exception("秒杀失败");
        }
        return ResultUtil.success(String.format("用户%s秒杀成功",userId));
    }


}
