package com.example;

import com.utils.Support;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * NOTE !!
 * It requires zookeeper and kafka up on 0.0.0.0:32807
 */

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Support.class)
public class QuickTest
{
    @Autowired
    private Support support;

    @Test
    public void loadTest()
    {
        assertNotNull(support);
    }

    @Test
    public void testAutoCommit() throws Exception
    {
        ContainerProperties containerProps = new ContainerProperties("topic1", "topic2");
        final CountDownLatch latch = new CountDownLatch(4);
        containerProps.setMessageListener((MessageListener<Integer, String>) message -> latch.countDown());
        KafkaMessageListenerContainer<Integer, String> container = support.createContainer(containerProps);
        container.setBeanName("testAuto");
        container.start();
        Thread.sleep(1000); // wait a bit for the container to start
        KafkaTemplate<Integer, String> template = support.createTemplate();
        template.setDefaultTopic("topic1");
        template.sendDefault(0, "foo");
        template.sendDefault(2, "bar");
        template.sendDefault(0, "baz");
        template.sendDefault(2, "qux");
        template.flush();
        assertTrue(latch.await(60, TimeUnit.SECONDS));
        container.stop();

    }
}
