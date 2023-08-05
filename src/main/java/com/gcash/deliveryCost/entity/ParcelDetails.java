package com.gcash.deliveryCost.entity;

import lombok.Data;

@Data
public class ParcelDetails {

    private int weight;
    private int height;
    private int width;
    private int length;
    private String voucher;
}
