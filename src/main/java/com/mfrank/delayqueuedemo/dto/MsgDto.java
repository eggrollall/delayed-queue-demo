package com.mfrank.delayqueuedemo.dto;

import java.util.Date;

import lombok.Data;

@Data
public class MsgDto {

    private Long msgId;
    private String msg;
    private Date sendDate;
    private Integer delayTime;
    private Boolean successFlag;
}
