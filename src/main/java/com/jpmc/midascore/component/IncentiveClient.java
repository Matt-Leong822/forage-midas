package com.jpmc.midascore.component;

import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.foundation.Transaction;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class IncentiveClient {

    private final RestTemplate restTemplate;

    public IncentiveClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public float getIncentiveAmount(Transaction transaction) {
        Incentive incentive = restTemplate.postForObject(
                "http://localhost:8080/incentive",
                transaction,
                Incentive.class
        );

        if (incentive == null) 
            return 0f;

        float amt = incentive.getAmount();
        return Math.max(0f, amt); //protects against negative incentives
    }
}
