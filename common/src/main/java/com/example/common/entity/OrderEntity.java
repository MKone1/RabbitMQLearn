package com.example.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity  implements Serializable {
    private Long OrderId;
    private Long ProductId;
    private Long UserId;
    private String ProductName;
}
