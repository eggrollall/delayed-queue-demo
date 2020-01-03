package com.mfrank.delayqueuedemo.controller;

import java.util.Date;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.mfrank.delayqueuedemo.constants.DelayTypeEnum;
import com.mfrank.delayqueuedemo.dto.MsgDto;
import com.mfrank.delayqueuedemo.mq.DelayMessageSender;

import lombok.extern.slf4j.Slf4j;
/**
 * http://rrd.me/f2LWv
 *
 * http://rrd.me/f2LYR
 */
@Slf4j
@RequestMapping("rabbitmq")
@RestController
public class RabbitMQMsgController {

    @Autowired
    private DelayMessageSender sender;
    @Value("${spring.rabbitmq.host}")
    public String name;

    @RequestMapping("sendmsg")
    public void sendMsg(String msg, Integer delayType){
        log.info("当前时间：{},收到请求，msg:{},delayType:{}", new Date(), msg, delayType);
        sender.sendMsg(msg, Objects.requireNonNull(DelayTypeEnum.getDelayTypeEnumByValue(delayType)));
    }

    @RequestMapping("delayMsg")
    public void delayMsg(String msg, Integer delayTime) {
        log.info("当前时间：{},收到请求，msg:{},delayTime:{}", new Date(), msg, delayTime);
        sender.sendMsg(msg, delayTime);
    }

    @RequestMapping("delayMsg2")
    public void delayMsg2(String msg, Integer delayTime) {
        log.info("当前时间：{},收到请求，msg:{},delayTime:{}", new Date(), msg, delayTime);
        sender.sendDelayMsg(msg, delayTime);
    }

    @RequestMapping("delayMsg3")
    public void delayMsg3(@RequestBody MsgDto dto) {
        log.info("当前时间：{},收到请求，dto:{}", new Date(), JSON.toJSONString(dto));
        sender.sendDtoMsg(dto);
    }

}
