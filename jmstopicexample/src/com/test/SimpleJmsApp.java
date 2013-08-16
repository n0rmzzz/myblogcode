package com.test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SimpleJmsApp
{
    private static final String BROKER_URL = "tcp://localhost:61616?jms.prefetchPolicy.all=1000";
    private static final int CONSUME_LIFE_TIME_IN_MS = 3600 * 1000;
    private static final boolean START_PRODUCERS = false;
    private static final Set<String> TOPICS = new HashSet<>(Arrays.asList("test_topic"));

    public static void main(String[] args) throws Exception
    {
        if (args.length > 0)
        {
            TOPICS.clear();
            TOPICS.addAll(Arrays.asList(args));
        }

        System.out.println("Now starting consumers...");
        for (String topic : TOPICS)
        {
            SimpleTopicConsumer consumer = new SimpleTopicConsumer(BROKER_URL, topic, CONSUME_LIFE_TIME_IN_MS);
            thread(consumer, false);
        }

        if (START_PRODUCERS)
        {
            Thread.sleep(1000);
            System.out.println("starting producers...");
            for (String topic : TOPICS)
            {
                SimpleTopicProducer producer = new SimpleTopicProducer(BROKER_URL, topic);
                thread(producer, false);
            }
        }
    }

    public static void thread(Runnable runnable, boolean daemon)
    {
        Thread brokerThread = new Thread(runnable);
        brokerThread.setDaemon(daemon);
        brokerThread.start();
    }
}
