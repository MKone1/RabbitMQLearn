package com.example.common.vo;

import com.example.common.entity.OrderEntity;
import com.example.common.entity.WareHouseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderWareHouseVo {
    private OrderEntity order;
    private WareHouseEntity wareHouse;
}
