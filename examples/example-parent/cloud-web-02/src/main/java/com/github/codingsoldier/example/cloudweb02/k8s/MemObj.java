package com.github.codingsoldier.example.cloudweb02.k8s;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class MemObj  implements Serializable {

    private static final long serialVersionUID = 1L;

    private String uuid;
    private Integer num;
    private byte[]  bytes;



}
