package com.jpmc.midascore.entity;

import jakarta.persistence.*;

@Entity
public class TransactionRecord {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY) //defines a many-to-one relationship between transactions and their respective senders
    @JoinColumn(name = "sender_id", nullable = false) //tells JPA to map the sender relationship using a foreign key column named sender_id in the TransactionRecord table
    private UserRecord sender;

    @ManyToOne(optional = false, fetch = FetchType.LAZY) //defines a many-to-one relationship between transactions and their respective recipients
    @JoinColumn(name = "recipient_id", nullable = false) //tells JPA to map the recipient relationship using a foreign key column named recipient_id in the TransactionRecord table
    private UserRecord recipient;

    @Column(nullable = false)
    private float amount;

    @Column(nullable = false)
    private float incentive;

    protected TransactionRecord() {
    }

    public TransactionRecord(UserRecord sender, UserRecord recipient, float amount, float incentive) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
        this.incentive = incentive;
    }

    public long getId() { 
        return id; 
    }

    public UserRecord getSender() { 
        return sender; 
    }

    public UserRecord getRecipient() { 
        return recipient; 
    }

    public float getAmount() { 
        return amount; 
    }

    public float getIncentive() { 
        return incentive; 
    }
}
