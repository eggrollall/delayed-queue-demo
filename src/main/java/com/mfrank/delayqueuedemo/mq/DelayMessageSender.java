package com.mfrank.delayqueuedemo.mq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.mfrank.delayqueuedemo.constants.DelayTypeEnum;
import com.mfrank.delayqueuedemo.dto.MsgDto;

import static com.mfrank.delayqueuedemo.config.DelayedRabbitMQConfig.DELAYED_EXCHANGE_NAME;
import static com.mfrank.delayqueuedemo.config.DelayedRabbitMQConfig.DELAYED_ROUTING_KEY;
import static com.mfrank.delayqueuedemo.config.RabbitMQConfig.DELAY_EXCHANGE_NAME;
import static com.mfrank.delayqueuedemo.config.RabbitMQConfig.DELAY_QUEUEA_ROUTING_KEY;
import static com.mfrank.delayqueuedemo.config.RabbitMQConfig.DELAY_QUEUEB_ROUTING_KEY;
import static com.mfrank.delayqueuedemo.config.RabbitMQConfig.DELAY_QUEUEC_ROUTING_KEY;

@Component
public class DelayMessageSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMsg(String msg, DelayTypeEnum type){
        switch (type){
            case DELAY_10s:
                rabbitTemplate.convertAndSend(DELAY_EXCHANGE_NAME, DELAY_QUEUEA_ROUTING_KEY, msg);
                break;
            case DELAY_60s:
                rabbitTemplate.convertAndSend(DELAY_EXCHANGE_NAME, DELAY_QUEUEB_ROUTING_KEY, msg);
                break;
        }
    }

    public void sendMsg(String msg, Integer delayTime) {
        rabbitTemplate.convertAndSend(DELAY_EXCHANGE_NAME, DELAY_QUEUEC_ROUTING_KEY, msg, a ->{
            a.getMessageProperties().setExpiration(String.valueOf(delayTime));
            return a;
        });
        /*rabbitTemplate.convertAndSend(DELAY_EXCHANGE_NAME, DELAY_QUEUEC_ROUTING_KEY, msg, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setExpiration(String.valueOf(delayTime));
                return message;
            }
        });*/
    }

    public void sendDelayMsg(String msg, Integer delayTime) {
        rabbitTemplate.convertAndSend(DELAYED_EXCHANGE_NAME, DELAYED_ROUTING_KEY, msg, a ->{
            a.getMessageProperties().setDelay(delayTime);
            return a;
        });
    }


    public void sendDtoMsg(MsgDto msg) {
        rabbitTemplate.convertAndSend(DELAYED_EXCHANGE_NAME, DELAYED_ROUTING_KEY, JSON.toJSONString(msg), a ->{
            a.getMessageProperties().setDelay(msg.getDelayTime());
            return a;
        });
    }
}
