package com.bbmk.payment_process.mappers;

import com.bbmk.payment_process.Dto.PaymentTransactionDTO;
import com.bbmk.payment_process.models.PaymentTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {MerchantMapper.class})
public interface PaymentTransactionMapper {
    PaymentTransactionMapper INSTANCE = Mappers.getMapper(PaymentTransactionMapper.class);

    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "merchant", source = "merchant")
    PaymentTransactionDTO toDTO(PaymentTransaction paymentTransaction);

    List<PaymentTransactionDTO> toDTOList(List<PaymentTransaction> paymentTransactions);

    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "merchant", source = "merchant")
    PaymentTransaction toEntity(PaymentTransactionDTO paymentTransactionDTO);
}

