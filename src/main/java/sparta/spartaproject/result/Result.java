package sparta.spartaproject.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Getter
@SuperBuilder(builderMethodName = "baseBuilder")
@AllArgsConstructor
@NoArgsConstructor
public class Result implements Serializable {
    private boolean success;
    private int statusCode;
    private String message;
}
