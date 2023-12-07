package com.gcash.deliveryCost.controller;

import com.gcash.deliveryCost.entity.ParcelDetails;
import com.gcash.deliveryCost.entity.RuleCost;
import com.gcash.deliveryCost.service.DeliveryCostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/calculate/")
public class CalculateDeliveryController {

    @Autowired
    DeliveryCostService deliveryCostService;

    @RequestMapping(value = "delivery", method = RequestMethod.GET)
    public RuleCost calculateDeliveryCost(@RequestBody ParcelDetails parcelDetails) {

        return deliveryCostService.calculateDeliveryCost(parcelDetails);
    }
}
