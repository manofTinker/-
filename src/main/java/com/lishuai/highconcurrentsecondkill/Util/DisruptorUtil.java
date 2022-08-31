package com.lishuai.highconcurrentsecondkill.Util;

import com.lishuai.highconcurrentsecondkill.vo.SecondKillEvent;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.concurrent.ThreadFactory;

/**
 * @author lishuai
 * @date 2022/8/28
 */
public class DisruptorUtil {

    static Disruptor<SecondKillEvent> disruptor;

    static{
        SecondKillEventFactory factory = new SecondKillEventFactory();
        int ringBufferSize = 2048;
        ThreadFactory threadFactory = runnable -> new Thread(runnable);
        disruptor = new Disruptor<>(factory, ringBufferSize, threadFactory);
//        由于是new 方式创建的 SecondKillEventConsumer 所以在 SecondKillEventConsumer 中使用 @Autowried注解就无效了
//        两种方式 将SecondKillEventConsumer 作为 Conpoment注入 或者使用 SpringUtil普通方法获取
        disruptor.handleEventsWith(new SecondKillEvetnConsumer());
        disruptor.start();
    }

    public static void producer(SecondKillEvent kill){
        RingBuffer<SecondKillEvent> ringBuffer = disruptor.getRingBuffer();
        SecondKillEventProducer producer = new SecondKillEventProducer(ringBuffer);
        producer.secondKill(kill.getUserId(),kill.getSekillId());
    }

}
