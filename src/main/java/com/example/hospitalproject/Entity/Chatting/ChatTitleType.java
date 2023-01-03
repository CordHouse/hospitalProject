package com.example.hospitalproject.Entity.Chatting;

import java.util.Arrays;

public enum ChatTitleType {
    SERVICE_CENTER("SERVICE_CENTER", "고객센터"),
    PRIVATE_CHAT("PRIVATE_CHAT", "개인채팅");

    private final String type;
    private final String value;

    ChatTitleType(String type, String value){
        this.type = type;
        this.value = value;
    }

    public static ChatTitleType getType(String type){
        return Arrays.stream(ChatTitleType.values())
                .filter(chatTitle -> chatTitle.type.equals(type))
                .findAny()
                .orElseThrow();
    }

    public String getValue(){
        return value;
    }
}
