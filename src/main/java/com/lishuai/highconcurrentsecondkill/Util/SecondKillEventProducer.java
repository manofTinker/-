package com.lishuai.highconcurrentsecondkill.Util;

import com.lishuai.highconcurrentsecondkill.vo.SecondKillEvent;
import com.lmax.disruptor.EventTranslatorVararg;
import com.lmax.disruptor.RingBuffer;

/**
 * @author lishuai
 * @date 2022/8/28
 */
public class SecondKillEventProducer {

    private final static EventTranslatorVararg<SecondKillEvent> translator = (seckillEvent, seq, objs) -> {
        seckillEvent.setSekillId((Integer) objs[0]);
        seckillEvent.setUserId((Integer) objs[1]);
    };

    private final RingBuffer<SecondKillEvent> ringBuffer;

    public SecondKillEventProducer(RingBuffer<SecondKillEvent> ringBuffer){
        this.ringBuffer = ringBuffer;
    }

    public void secondKill(Integer userId, Integer seckillId){
        this.ringBuffer.publishEvent(translator, userId, seckillId);
    }
}
