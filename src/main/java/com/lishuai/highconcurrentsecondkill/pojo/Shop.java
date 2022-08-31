package com.lishuai.highconcurrentsecondkill.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.time.LocalDateTime;

/**
 * @author lishuai
 * @date 2022/8/19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Shop {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private String name;

    private Integer number;

    @TableField("start_time")
    private LocalDateTime starttime;

    @TableField("end_time")
    private LocalDateTime endtime;

    @Version
    private Integer version;





}
