package ru.ATM_Project.client.messages;

import lombok.Value;

@Value
public class RequestDTO {
    private final int id;
    private final String data;
//    private final RequestTypes type;
}