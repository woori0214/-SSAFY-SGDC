package com.ssafy.sgdc.util;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class Message {

    private int status;
    private String message;

}
