package com.example.BloodBankManagementSystem.entities;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;
@Converter(autoApply = true)
public class CategoryConvertedBloodType implements AttributeConverter<BloodType,String> {
    @Override
    public String convertToDatabaseColumn(BloodType bloodType) {
        if(bloodType==null){
            return null;
        }
        return bloodType.getCode();
    }

    @Override
    public BloodType convertToEntityAttribute(String code) {
        if(code==null){
            return null;
        }
        return Stream.of(BloodType.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
