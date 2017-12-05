package com.jagrosh.discordipc.entities;

import org.json.JSONObject;

import java.time.OffsetDateTime;

/**
 *
 * @author John Grosh (john.a.grosh@gmail.com)
 */
public class RichPresence
{
    private final String state;
    private final String details;
    private final OffsetDateTime startTimestamp;
    private final OffsetDateTime endTimestamp;
    private final String largeImageKey;
    private final String largeImageText;
    private final String smallImageKey;
    private final String smallImageText;
    private final String partyId;
    private final int partySize;
    private final int partyMax;
    private final String matchSecret;
    private final String joinSecret;
    private final String spectateSecret;
    private final boolean instance;

    public RichPresence(String state, String details, OffsetDateTime startTimestamp, OffsetDateTime endTimestamp,
                        String largeImageKey, String largeImageText, String smallImageKey, String smallImageText,
                        String partyId, int partySize, int partyMax, String matchSecret, String joinSecret,
                        String spectateSecret, boolean instance)
    {
        this.state = state;
        this.details = details;
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
        this.largeImageKey = largeImageKey;
        this.largeImageText = largeImageText;
        this.smallImageKey = smallImageKey;
        this.smallImageText = smallImageText;
        this.partyId = partyId;
        this.partySize = partySize;
        this.partyMax = partyMax;
        this.matchSecret = matchSecret;
        this.joinSecret = joinSecret;
        this.spectateSecret = spectateSecret;
        this.instance = instance;
    }

    public JSONObject toJson()
    {
        return new JSONObject()
                .put("state", state)
                .put("details", details)
                .put("timestamps", new JSONObject()
                        .put("start", startTimestamp==null ? null : startTimestamp.toEpochSecond())
                        .put("end", endTimestamp==null ? null : endTimestamp.toEpochSecond()))
                .put("assets", new JSONObject()
                        .put("large_image", largeImageKey)
                        .put("large_text", largeImageText)
                        .put("small_image", smallImageKey)
                        .put("small_text", smallImageText))
                .put("secrets", new JSONObject()
                        .put("join", joinSecret)
                        .put("spectate", spectateSecret)
                        .put("match", matchSecret))
                .put("instance", instance);
    }

    public static class Builder
    {
        private String state;
        private String details;
        private OffsetDateTime startTimestamp;
        private OffsetDateTime endTimestamp;
        private String largeImageKey;
        private String largeImageText;
        private String smallImageKey;
        private String smallImageText;
        private String partyId;
        private int partySize;
        private int partyMax;
        private String matchSecret;
        private String joinSecret;
        private String spectateSecret;
        private boolean instance;

        public RichPresence build()
        {
            return new RichPresence(state, details, startTimestamp, endTimestamp,
                    largeImageKey, largeImageText, smallImageKey, smallImageText,
                    partyId, partySize, partyMax, matchSecret, joinSecret,
                    spectateSecret, instance);
        }

        public Builder setState(String state)
        {
            this.state = state;
            return this;
        }

        public Builder setDetails(String details)
        {
            this.details = details;
            return this;
        }

        public Builder setStartTimestamp(OffsetDateTime startTimestamp)
        {
            this.startTimestamp = startTimestamp;
            return this;
        }

        public Builder setEndTimestamp(OffsetDateTime endTimestamp)
        {
            this.endTimestamp = endTimestamp;
            return this;
        }

        public Builder setLargeImage(String largeImageKey, String largeImageText)
        {
            this.largeImageKey = largeImageKey;
            this.largeImageText = largeImageText;
            return this;
        }

        public Builder setLargeImage(String largeImageKey)
        {
            return setLargeImage(largeImageKey, null);
        }

        public Builder setSmallImage(String smallImageKey, String smallImageText)
        {
            this.smallImageKey = smallImageKey;
            this.smallImageText = smallImageText;
            return this;
        }

        public Builder setSmallImage(String smallImageKey)
        {
            return setSmallImage(smallImageKey, null);
        }

        public Builder setParty(String partyId, int partySize, int partyMax)
        {
            this.partyId = partyId;
            this.partySize = partySize;
            this.partyMax = partyMax;
            return this;
        }

        public Builder setMatchSecret(String matchSecret)
        {
            this.matchSecret = matchSecret;
            return this;
        }

        public Builder setJoinSecret(String joinSecret)
        {
            this.joinSecret = joinSecret;
            return this;
        }

        public Builder setSpectateSecret(String spectateSecret)
        {
            this.spectateSecret = spectateSecret;
            return this;
        }

        public Builder setInstance(boolean instance)
        {
            this.instance = instance;
            return this;
        }
    }
}