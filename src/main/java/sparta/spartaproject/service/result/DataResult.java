package sparta.spartaproject.service.result;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public class DataResult<T> extends Result {
    private T data;
}