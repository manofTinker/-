package com.lishuai.highconcurrentsecondkill.vo;

import lombok.*;

/**
 * @author lishuai
 * @date 2022/8/28
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SecondKillEvent {
    private static final long serialVersionUID = 1L;
    private Integer sekillId;
    private Integer userId;
}
