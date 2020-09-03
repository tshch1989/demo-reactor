package problem.p1;

import lombok.Data;

@Data
public class Resp<T> {
    private String code;
    private T data;
}
