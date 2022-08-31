package com.lishuai.highconcurrentsecondkill.Util;

import com.lishuai.highconcurrentsecondkill.service.impl.ShopServiceImpl;
import com.lishuai.highconcurrentsecondkill.vo.SecondKillEvent;
import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lishuai
 * @date 2022/8/28
 */
@Slf4j
public class SecondKillEvetnConsumer implements EventHandler<SecondKillEvent> {

    //    @Autowired
    //    private ShopServiceImpl shopService;

    private ShopServiceImpl shopService = (ShopServiceImpl) SpringUtil.getBean("shopServiceImpl");

    @Override
    public void onEvent(SecondKillEvent seckillEvent, long seq, boolean bool) throws Exception {
        Result result = shopService.kill(seckillEvent.getUserId(), seckillEvent.getSekillId());
        if(result.equals(ResultUtil.success(String.format("用户%s秒杀成功", seckillEvent.getSekillId())))){
            log.info("用户:{}{}",seckillEvent.getUserId(),"秒杀成功");
        }
    }

}
