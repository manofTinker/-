package com.lishuai.highconcurrentsecondkill.pojo;

import ch.qos.logback.core.util.InvocationGate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.swing.*;
import java.time.LocalDateTime;

/**
 * @author lishuai
 * @date 2022/8/19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Successkill {

    private static final long serialVersionUID = 1L;

    private Integer killid;

    private Integer userid;

    private Integer status;

    private LocalDateTime create_time;

    private Integer shopnum;

}
