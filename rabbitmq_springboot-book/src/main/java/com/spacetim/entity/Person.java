package com.spacetim.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author spacetim
 * @date 2021/9/22
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    private Integer id;
    private String name;
    private String username;
}
