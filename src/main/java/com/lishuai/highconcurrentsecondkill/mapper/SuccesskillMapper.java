package com.lishuai.highconcurrentsecondkill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lishuai.highconcurrentsecondkill.pojo.Successkill;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author lishuai
 * @date 2022/8/19
 */

@Mapper
@Repository
public interface SuccesskillMapper extends BaseMapper<Successkill> {
}
