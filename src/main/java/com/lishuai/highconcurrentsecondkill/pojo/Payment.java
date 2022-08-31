package com.lishuai.highconcurrentsecondkill.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author lishuai
 * @date 2022/8/19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Payment {

    private static final long serialVersionUID = 1L;

    private Integer killid;

    private Integer userid;

    private Integer status;

    private String money;

    private LocalDateTime createTime;
}
