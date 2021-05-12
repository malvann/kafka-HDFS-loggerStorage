package com.kafka.kafkaWithTwitter.listener;

import twitter4j.*;

import java.util.Arrays;

public class KafkaStatusListener implements StatusListener {
    public static final Logger LOGGER = Logger.getLogger(KafkaStatusListener.class);

    @Override
    public void onStatus(Status status) {
//        queue.offer(status);
         LOGGER.info("@" + status.getUser().getScreenName() + " - " + status.getText());
         for(URLEntity url : status.getURLEntities()) LOGGER.info(url.getDisplayURL());
         for(HashtagEntity hashTage : status.getHashtagEntities()) LOGGER.info(hashTage.getText());
    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
        LOGGER.info("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
    }

    @Override
    public void onTrackLimitationNotice(int numBerOfLimitedStatuses) {
        LOGGER.info("Got track limitation notice:" + numBerOfLimitedStatuses);
    }

    @Override
    public void onScrubGeo(long userId, long upToStatusId) {
        LOGGER.info("Got scrub_geo event userId:" + userId + "upToStatusId:" + upToStatusId);
    }

    @Override
    public void onStallWarning(StallWarning warning) {
        LOGGER.warn("Got stall warning:" + warning);
    }

    @Override
    public void onException(Exception e) {
        LOGGER.error(Arrays.toString(e.getStackTrace()));
    }
}
