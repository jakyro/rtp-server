package model.receive;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MyLegacySensorReceiveWrapper {
    @JsonProperty("message")
    String message;

    public String getMessage() {
        return message;
    }
}
