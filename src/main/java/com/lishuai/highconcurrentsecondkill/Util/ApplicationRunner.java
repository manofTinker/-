package com.lishuai.highconcurrentsecondkill.Util;

import com.lishuai.highconcurrentsecondkill.pojo.Successkill;
import com.lishuai.highconcurrentsecondkill.service.impl.ShopServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;

/**
 * @author lishuai
 * @date 2022/8/28
 */
@Slf4j
public class ApplicationRunner implements org.springframework.boot.ApplicationRunner {

    @Autowired
    private ShopServiceImpl shopService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        new Thread(()->{

            log.info("队列启动成功");
            while (true){
                try {
                    Successkill kill = SecondKillQueue.getSkillQueue().consume();
                    if(kill != null){
                        Result re = shopService.kill(kill.getUserid(), kill.getKillid());
                        if(re !=null && re.equals(ResultUtil.success(String.format("用户%s秒杀成功", kill.getUserid())))){
                            log.info("TaskRunner,result:{}",re);
                            log.info("TaskRunner从消息队列中取出用户,用户{}",kill.getUserid(),"秒杀成功");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }).start();

    }
}
