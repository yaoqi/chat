package com.mercury.chat.user.entity;

import java.io.Serializable;
import java.util.Date;

public class ChatMessage implements Serializable {
    private Long id;

    private String chatFrom;

    private String chatTo;

    private Long shopId;

    private Date createTs;

    private String message;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChatFrom() {
        return chatFrom;
    }

    public void setChatFrom(String chatFrom) {
        this.chatFrom = chatFrom;
    }

    public String getChatTo() {
        return chatTo;
    }

    public void setChatTo(String chatTo) {
        this.chatTo = chatTo;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ChatMessage other = (ChatMessage) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getChatFrom() == null ? other.getChatFrom() == null : this.getChatFrom().equals(other.getChatFrom()))
            && (this.getChatTo() == null ? other.getChatTo() == null : this.getChatTo().equals(other.getChatTo()))
            && (this.getShopId() == null ? other.getShopId() == null : this.getShopId().equals(other.getShopId()))
            && (this.getCreateTs() == null ? other.getCreateTs() == null : this.getCreateTs().equals(other.getCreateTs()))
            && (this.getMessage() == null ? other.getMessage() == null : this.getMessage().equals(other.getMessage()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getChatFrom() == null) ? 0 : getChatFrom().hashCode());
        result = prime * result + ((getChatTo() == null) ? 0 : getChatTo().hashCode());
        result = prime * result + ((getShopId() == null) ? 0 : getShopId().hashCode());
        result = prime * result + ((getCreateTs() == null) ? 0 : getCreateTs().hashCode());
        result = prime * result + ((getMessage() == null) ? 0 : getMessage().hashCode());
        return result;
    }
}