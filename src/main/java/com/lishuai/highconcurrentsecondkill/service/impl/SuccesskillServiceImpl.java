package com.lishuai.highconcurrentsecondkill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lishuai.highconcurrentsecondkill.mapper.SuccesskillMapper;
import com.lishuai.highconcurrentsecondkill.pojo.Successkill;
import com.lishuai.highconcurrentsecondkill.service.ISuccesskillService;
import org.springframework.stereotype.Service;

/**
 * @author lishuai
 * @date 2022/8/19
 */
@Service
public class SuccesskillServiceImpl extends ServiceImpl<SuccesskillMapper, Successkill> implements ISuccesskillService {
}
