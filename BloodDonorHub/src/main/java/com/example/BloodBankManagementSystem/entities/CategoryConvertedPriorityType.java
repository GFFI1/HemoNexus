package com.example.BloodBankManagementSystem.entities;

import javax.persistence.AttributeConverter;
import java.util.stream.Stream;


import javax.persistence.Converter;
@Converter(autoApply = true)
public class CategoryConvertedPriorityType implements AttributeConverter<PriorityType,String> {

    @Override
    public String convertToDatabaseColumn(PriorityType priorityType) {
        if(priorityType==null){
            return null;
        }
        return priorityType.getCode();
    }

    @Override
    public PriorityType convertToEntityAttribute(String code) {
        if(code==null){
            return null;
        }
        return Stream.of(PriorityType.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
