package com.example.smp.notification.configurations.properties;

import lombok.Getter;
import lombok.Setter;
import com.example.smp.dto.response.AppStatus;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class ErrorMessages extends HashMap<String, AppStatus> {

    public ErrorMessages() {
        super();
    }

    public ErrorMessages(Map<? extends String, ? extends AppStatus> m) {
        super(m);
    }
}
