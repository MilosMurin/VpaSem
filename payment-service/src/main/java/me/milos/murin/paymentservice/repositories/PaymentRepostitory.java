package me.milos.murin.paymentservice.repositories;

import me.milos.murin.paymentservice.models.Payment;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepostitory extends CrudRepository<Payment, Long> {


}
