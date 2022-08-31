package com.lishuai.highconcurrentsecondkill.Util;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lishuai
 * @date 2022/8/21
 */

@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean success;

    private String message;

    private Integer code;

    private long timestamp = System.currentTimeMillis();

    private T result;

}
