package com.lishuai.highconcurrentsecondkill.Util;

import com.lishuai.highconcurrentsecondkill.vo.SecondKillEvent;
import com.lmax.disruptor.EventFactory;

/**
 * @author lishuai
 * @date 2022/8/28
 */
public class SecondKillEventFactory implements EventFactory<SecondKillEvent> {
    @Override
    public SecondKillEvent newInstance() {
        return new SecondKillEvent();
    }
}
