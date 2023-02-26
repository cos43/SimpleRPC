package entity;

import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

@Data
@Getter
public class RpcResponse<T> implements Serializable {
    private Integer statusCode;
    private String message;
    private T data;

    public static <T> RpcResponse<T> success(T data) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setStatusCode(200);
        response.setData(data);
        return response;
    }

    public static <T> RpcResponse<T> fail(Integer code, String message) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setStatusCode(code);
        response.setMessage(message);
        return response;
    }
}
