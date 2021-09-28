package com.example.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTable implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId
    private Long id;

    private Long userID;

    private String userName;

    private String userMobile;
}
