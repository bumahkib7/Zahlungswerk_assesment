package com.bbmk.payment_process.mappers;

import com.bbmk.payment_process.Dto.MerchantDTO;
import com.bbmk.payment_process.models.Merchant;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MerchantMapper {
    MerchantMapper INSTANCE = Mappers.getMapper(MerchantMapper.class);

    MerchantDTO toDTO(Merchant merchant);

    List<MerchantDTO> toDTOList(List<Merchant> merchants);
}

