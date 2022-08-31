package com.lishuai.highconcurrentsecondkill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lishuai.highconcurrentsecondkill.Util.Result;
import com.lishuai.highconcurrentsecondkill.pojo.Shop;

/**
 * @author lishuai
 * @date 2022/8/19
 */
public interface IShopService extends IService<Shop> {

    Result kill(Integer userId, Integer shopid) throws Exception;

}
