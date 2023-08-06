package com.gcash.deliveryCost.entity;

import lombok.Data;

import java.util.Date;

@Data
public class VoucherItem {

    private String code;
    private float discount;
    private Date expiry;
}
