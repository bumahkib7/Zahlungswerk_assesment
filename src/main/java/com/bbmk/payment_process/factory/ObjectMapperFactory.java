package com.bbmk.payment_process.factory;

import com.bbmk.payment_process.Dto.MerchantDTO;
import com.bbmk.payment_process.mixin.MerchantDTOMixin;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.module.SimpleSerializers;

public class ObjectMapperFactory {

    public static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        SimpleModule module = new SimpleModule();
        module.setSerializers(new SimpleSerializers());
        module.setMixInAnnotation(MerchantDTO.class, MerchantDTOMixin.class);
        mapper.registerModule(module);

        return mapper;
    }
}
