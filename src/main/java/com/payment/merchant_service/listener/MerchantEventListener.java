package com.payment.merchant_service.listener;

import com.google.cloud.spring.autoconfigure.bigquery.GcpBigQueryAutoConfiguration;
import com.google.cloud.spring.pubsub.support.BasicAcknowledgeablePubsubMessage;
import com.google.cloud.spring.pubsub.support.GcpPubSubHeaders;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
public class MerchantEventListener {

    @ServiceActivator(inputChannel="pubSubInputChannel")
    public void messageReceiver(String payload,
                                @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE)BasicAcknowledgeablePubsubMessage message){

        System.out.println("✅ AUTO RECEIVED: " + payload);
        message.ack();
    }

}
