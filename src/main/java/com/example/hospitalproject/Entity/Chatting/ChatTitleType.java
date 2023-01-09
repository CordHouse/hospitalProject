package com.example.hospitalproject.Entity.Chatting;

import java.util.Arrays;

public enum ChatTitleType {
    SERVICE_CENTER("고객센터"),
    PRIVATE_CHAT("개인채팅");

    private final String value;

    ChatTitleType(String value){
        this.value = value;
    }

    public static ChatTitleType getType(String type){
        return Arrays.stream(ChatTitleType.values())
                .filter(chatTitle -> chatTitle.name().equals(type))
                .findAny()
                .orElseThrow();
    }

    public String getValue(){
        return value;
    }
}
