package com.example.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WareHouseEntity {
    private Long wId;
    private String wareHouseName;
    private String wareHouseAddress;
}
