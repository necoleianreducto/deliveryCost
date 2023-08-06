package com.gcash.deliveryCost.service;

import com.gcash.deliveryCost.constant.ConstantMessageAndRule;
import com.gcash.deliveryCost.entity.ParcelDetails;
import com.gcash.deliveryCost.entity.RuleCost;
import com.gcash.deliveryCost.entity.VoucherItem;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Slf4j
@Service
public class DeliveryCostService {

    @Autowired
    VoucherService voucherService;

    public RuleCost calculateDeliveryCost(ParcelDetails parcel) {
        float weight = parcel.getWeight();
        boolean isVoucherExpired = false;
        float discount = 0;

        VoucherItem voucherItem = voucherService.getVoucherDiscount(parcel.getVoucher());
        if (voucherItem != null) {
            LocalDate currentDate = LocalDate.now();
            Date expiryDate = voucherItem.getExpiry();
            LocalDate expiryLocalDate = expiryDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if (expiryLocalDate.isBefore(currentDate)) {
                isVoucherExpired = true;
                return createRuleCost(ConstantMessageAndRule.REJECT, "", "", isVoucherExpired);
            }
            discount = voucherItem.getDiscount();
        }

        if (weight > ConstantMessageAndRule.WEIGHT_50) {
            return createRejectRule(isVoucherExpired);
        }

        if (weight > ConstantMessageAndRule.WEIGHT_10) {
            return CreateHeavyRule(parcel, discount, isVoucherExpired);
        }

        double volume = calculateVolume(parcel.getHeight(), parcel.getWidth(), parcel.getLength());
        log.info("VOLUME : " + volume);

        if (volume < ConstantMessageAndRule.VOL_LESS_1500) {
            return createSmallRule(volume, discount, isVoucherExpired);
        }

        if (volume < ConstantMessageAndRule.VOL_LESS_2500) {
            return createMediumRule(volume, discount, isVoucherExpired);
        }

        return createLargeRule(volume, discount, isVoucherExpired);
    }

    private double calculateVolume(float height, float width, float length) {
        return height * width * length;
    }

    private RuleCost createRejectRule(boolean isVoucherExpired) {
        return createRuleCost(ConstantMessageAndRule.REJECT, null, ConstantMessageAndRule.LARGE_CONDITION, isVoucherExpired);
    }

    private RuleCost CreateHeavyRule(ParcelDetails parcel, float discount, boolean isVoucherExpired) {
        float cost = 20 * parcel.getWeight();
        float finalCost = cost - discount;
        return createRuleCost(ConstantMessageAndRule.HEAVY, Double.toString(finalCost),
                ConstantMessageAndRule.HEAVY_CONDITION, isVoucherExpired);
    }

    private RuleCost createSmallRule(double volume, float discount, boolean isVoucherExpired) {
        double cost = 0.03 * volume;
        double finalCost = cost - discount;
        return createRuleCost(ConstantMessageAndRule.SMALL,
                Double.toString(finalCost), ConstantMessageAndRule.SMALL_CONDITION,
                isVoucherExpired);
    }

    private RuleCost createMediumRule(double volume, float discount, boolean isVoucherExpired) {
        double cost = 0.04 * volume;
        double finalCost = cost - discount;
        return createRuleCost(ConstantMessageAndRule.MEDIUM,
                Double.toString(finalCost), ConstantMessageAndRule.MEDIUM_CONDITION,
                isVoucherExpired);
    }

    private RuleCost createLargeRule(double volume, float discount, boolean isVoucherExpired) {
        double cost = 0.05 * volume;
        double finalCost = cost - discount;
        return createRuleCost(ConstantMessageAndRule.LARGE, Double.toString(finalCost), null, isVoucherExpired);
    }

    private RuleCost createRuleCost(String rule, String cost, String condition, boolean isVoucherExpired) {
        RuleCost ruleCost = new RuleCost();
        ruleCost.setRule(rule);
        ruleCost.setCost(cost);
        ruleCost.setCondition(condition);
        ruleCost.setExpiredVoucher(isVoucherExpired);
        return ruleCost;
    }
}
