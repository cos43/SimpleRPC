package entity;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RpcRequest implements Serializable {
    private String interfaceName;
    private String methodName;
    private Object[] parameters;

    private Class<?>[] paramTypes;
}
