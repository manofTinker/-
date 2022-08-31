package com.lishuai.highconcurrentsecondkill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lishuai.highconcurrentsecondkill.mapper.PaymentMapper;
import com.lishuai.highconcurrentsecondkill.pojo.Payment;
import com.lishuai.highconcurrentsecondkill.service.IPaymentService;
import org.springframework.stereotype.Service;

/**
 * @author lishuai
 * @date 2022/8/19
 */
@Service
public class PaymentServiceImpl extends ServiceImpl<PaymentMapper, Payment> implements IPaymentService {
}
