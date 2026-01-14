package com.jpmc.midascore.component;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRepository;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionProcessor {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRecordRepository;

    public TransactionProcessor(UserRepository userRepository, TransactionRepository transactionRecordRepository) {
        this.userRepository = userRepository;
        this.transactionRecordRepository = transactionRecordRepository;
    }

    @Transactional
    public void process(Transaction transaction) {
        if (transaction == null) 
            return;

        long senderId = transaction.getSenderId();
        long recipientId = transaction.getRecipientId();
        float amount = transaction.getAmount();

        if (amount <= 0) 
            return;

        UserRecord sender = userRepository.findById(senderId);
        if (sender == null) 
            return;

        UserRecord recipient = userRepository.findById(recipientId);
        if (recipient == null) 
            return;

        if (sender.getBalance() < amount) 
            return;

        // record transaction
        transactionRecordRepository.save(new TransactionRecord(sender, recipient, amount));

        // update balances
        sender.setBalance(sender.getBalance() - amount);
        recipient.setBalance(recipient.getBalance() + amount);

        // persist balance updates
        userRepository.save(sender);
        userRepository.save(recipient); //add a breakpoint here to debug user balance updates for task 3
    }
}
