package com.gcash.deliveryCost.service;

import com.gcash.deliveryCost.entity.VoucherItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class VoucherService {

    private static final String BASE_URL = "https://mynt-exam.mocklab.io";
    private static final String API_KEY = "apikey";
    @Autowired
    private RestTemplate restTemplate;

    public VoucherItem getVoucherDiscount(String voucherCode) {
//        String apiUrl = BASE_URL + "/voucher/{voucherCode}";
//
//        apiUrl += "?key=" + API_KEY;
//        log.info("API URL: " + apiUrl);
//        ResponseEntity<VoucherItem> response = restTemplate.getForEntity(apiUrl, VoucherItem.class, voucherCode);
//
//        if (response.getStatusCode().is2xxSuccessful()) {
//            return response.getBody();
//        } else {
//            return null;
//        }
        return null;
    }
}
