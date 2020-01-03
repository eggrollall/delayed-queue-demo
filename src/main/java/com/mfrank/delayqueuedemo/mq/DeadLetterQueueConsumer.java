package com.mfrank.delayqueuedemo.mq;

import java.io.IOException;
import java.util.Date;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.mfrank.delayqueuedemo.dto.MsgDto;
import com.rabbitmq.client.Channel;

import lombok.extern.slf4j.Slf4j;

import static com.mfrank.delayqueuedemo.config.DelayedRabbitMQConfig.DELAYED_QUEUE_NAME;
import static com.mfrank.delayqueuedemo.config.RabbitMQConfig.DEAD_LETTER_QUEUEA_NAME;
import static com.mfrank.delayqueuedemo.config.RabbitMQConfig.DEAD_LETTER_QUEUEB_NAME;
import static com.mfrank.delayqueuedemo.config.RabbitMQConfig.DEAD_LETTER_QUEUEC_NAME;

@Slf4j
@Component
public class DeadLetterQueueConsumer {

    /**
     *
     * @param message
     * @param channel
     * @throws IOException
     */
    @RabbitListener(queues = DEAD_LETTER_QUEUEA_NAME)
    public void receiveA(Message message, Channel channel) throws IOException {
        try {
            String msg = new String(message.getBody());
            log.info("当前时间：{},死信队列A收到消息：{}", new Date().toString(), msg);
            if (msg.contains("error")) {
                throw new RuntimeException("死信队列A消息消费异常");
            }
            log.info("正在消费死信队列A的消息···");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info("消费死信队列A的消息成功");
        } catch (Exception e) {
            log.error("receiveA_error", e);
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        }
    }

    @RabbitListener(queues = DEAD_LETTER_QUEUEB_NAME)
    public void receiveB(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        log.info("当前时间：{},死信队列B收到消息：{}", new Date().toString(), msg);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    @RabbitListener(queues = DEAD_LETTER_QUEUEC_NAME)
    public void receiveC(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        log.info("当前时间：{},死信队列C收到消息：{}", new Date().toString(), msg);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    @RabbitListener(queues = DELAYED_QUEUE_NAME)
    public void receiveD(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        log.info("当前时间：{},延时队列D收到消息：{}", new Date().toString(), msg);
        MsgDto msgDto = JSON.parseObject(msg, MsgDto.class);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

}
