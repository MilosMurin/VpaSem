package me.milos.murin.paymentservice.repositories;

import me.milos.murin.paymentservice.models.PayerInfo;
import org.springframework.data.repository.Repository;

public interface PayerInfoRepository extends Repository<PayerInfo, Long> {

    PayerInfo save(PayerInfo entity);

}
