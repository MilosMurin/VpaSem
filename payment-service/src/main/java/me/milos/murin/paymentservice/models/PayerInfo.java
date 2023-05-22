package me.milos.murin.paymentservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class PayerInfo {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "payment_id", nullable = false)
    @Id
    private Payment payment;

    private String cardNumber;

    private String expire;

    private String cvv;

    public PayerInfo() {
    }

    public PayerInfo(Payment payment, String cardNumber, String expire, String cvv) {
        this.payment = payment;
        this.cardNumber = cardNumber;
        this.expire = expire;
        this.cvv = cvv;
    }
}
