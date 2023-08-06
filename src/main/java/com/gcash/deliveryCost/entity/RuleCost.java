package com.gcash.deliveryCost.entity;

import lombok.Data;

@Data
public class RuleCost {
    private String rule;
    private String condition;
    private String cost;
    private boolean isExpiredVoucher;
}
