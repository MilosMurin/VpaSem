package me.milos.murin.paymentservice.repositories;

import me.milos.murin.paymentservice.models.PayerInfo;
import me.milos.murin.paymentservice.models.Payment;
import org.springframework.data.repository.Repository;

public interface PayerInfoRepository extends Repository<PayerInfo, Payment> {

    PayerInfo save(PayerInfo entity);

}
