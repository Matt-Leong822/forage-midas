package com.jpmc.midascore.component;

import com.jpmc.midascore.foundation.Transaction;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaTransactionListener {

    private final TransactionProcessor transactionProcessor;

    public KafkaTransactionListener(TransactionProcessor transactionProcessor) {
        this.transactionProcessor = transactionProcessor;
    }

    @KafkaListener(topics = "${general.kafka-topic}", groupId = "midas-core")
    public void listen(Transaction transaction) {
        //add a breakpoint in this method to debug incoming kafka transactions for task 2
        transactionProcessor.process(transaction);
    }
    
}
