package sparta.spartaproject.result;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class DataResult<T> extends Result {
    private T data;
}