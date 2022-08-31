package com.lishuai.highconcurrentsecondkill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lishuai.highconcurrentsecondkill.pojo.Shop;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.net.Inet4Address;

/**
 * @author lishuai
 * @date 2022/8/19
 */

@Mapper
@Repository
public interface ShopMapper extends BaseMapper<Shop> {

    @Select(value = "SELECT * FROM shop WHERE id = #{shopId} FOR UPDATE")
    Shop querySecondKillForUpdate(@Param("shopId") Integer shopId);


}
