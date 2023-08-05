package com.gcash.deliveryCost.service;

import com.gcash.deliveryCost.constant.ConstantMessageAndRule;
import com.gcash.deliveryCost.entity.ParcelDetails;
import com.gcash.deliveryCost.entity.RuleCost;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DeliveryCostService {

    public RuleCost calculateDeliveryCost(ParcelDetails parcel) {
        float weight = parcel.getWeight();

        if (weight> ConstantMessageAndRule.WEIGHT_50) {
            return createRejectRule();
        }

        if (weight > ConstantMessageAndRule.WEIGHT_10) {
            return CreateHeavyRule(parcel);
        }

        double volume = calculateVolume(parcel.getHeight(), parcel.getWidth(), parcel.getLength());
        log.info("VOLUME : " + volume);

        if (volume < ConstantMessageAndRule.VOL_LESS_1500) {
            return createSmallRule(volume);
        }

        if (volume < ConstantMessageAndRule.VOL_LESS_2500) {
            return createMediumRule(volume);
        }

        return createLargeRule(volume);
    }

    private double calculateVolume(float height, float width, float length) {
        return height * width * length;
    }

    private RuleCost createRejectRule() {
        return createRuleCost(ConstantMessageAndRule.REJECT, null, ConstantMessageAndRule.LARGE_CONDITION);
    }

    private RuleCost CreateHeavyRule(ParcelDetails parcel) {
        float cost = 20 * parcel.getWeight();
        return createRuleCost(ConstantMessageAndRule.HEAVY, Double.toString(cost), ConstantMessageAndRule.HEAVY_CONDITION);
    }

    private RuleCost createSmallRule(double volume) {
        double cost = 0.03 * volume;
        return createRuleCost(ConstantMessageAndRule.SMALL, Double.toString(cost), ConstantMessageAndRule.SMALL_CONDITION);
    }

    private RuleCost createMediumRule(double volume) {
        double cost = 0.04 * volume;
        return createRuleCost(ConstantMessageAndRule.MEDIUM, Double.toString(cost), ConstantMessageAndRule.MEDIUM_CONDITION);
    }

    private RuleCost createLargeRule(double volume) {
        double cost = 0.05 * volume;
        return createRuleCost(ConstantMessageAndRule.LARGE, Double.toString(cost), null);
    }

    private RuleCost createRuleCost(String rule, String cost, String condition) {
        RuleCost ruleCost = new RuleCost();
        ruleCost.setRule(rule);
        ruleCost.setCost(cost);
        ruleCost.setCondition(condition);
        return ruleCost;
    }
}
