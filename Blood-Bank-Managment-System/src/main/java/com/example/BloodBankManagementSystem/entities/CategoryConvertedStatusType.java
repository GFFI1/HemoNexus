package com.example.BloodBankManagementSystem.entities;


import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;
@Converter(autoApply = true)
public class CategoryConvertedStatusType implements AttributeConverter<StatusType,String> {
    @Override
    public String convertToDatabaseColumn(StatusType statusType) {
        if(statusType==null){
            return null;
        }
        return statusType.getCode();
    }

    @Override
    public StatusType convertToEntityAttribute(String code) {
        if(code==null){
            return null;
        }
        return Stream.of(StatusType.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
