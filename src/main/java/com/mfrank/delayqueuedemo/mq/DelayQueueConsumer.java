package com.mfrank.delayqueuedemo.mq;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DelayQueueConsumer {

    // @RabbitListener(queues = DELAY_QUEUEA_NAME)
    // public void receiveA(Message message, Channel channel) throws IOException {
    //     try {
    //         String msg = new String(message.getBody());
    //         log.info("当前时间：{},延时队列A收到消息：{}", new Date().toString(), msg);
    //         if (msg.contains("error")) {
    //             throw new RuntimeException("延时队列A消息消费异常");
    //         }
    //         log.info("正在消费延时队列A的消息···");
    //         channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    //         log.info("消费延时队列A的消息成功");
    //     } catch (Exception e) {
    //         log.error("receiveA_error", e);
    //         channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
    //     }
    // }

}
