package me.milos.murin.paymentservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class PayerInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "payment_id", nullable = false)
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
