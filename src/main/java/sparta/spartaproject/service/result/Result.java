package sparta.spartaproject.service.result;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Getter
@SuperBuilder(builderMethodName = "baseBuilder")
public class Result implements Serializable {
    private boolean success;
    private int statusCode;
    private String message;
}
