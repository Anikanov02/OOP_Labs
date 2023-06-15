package com.anikanov.domain.converter;

import com.anikanov.config.GlobalConstants;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.LocalDateTime;

@Converter
public class DateConverter implements AttributeConverter<LocalDateTime, String> {
    @Override
    public String convertToDatabaseColumn(LocalDateTime attribute) {
        return attribute.format(GlobalConstants.formatter);
    }

    @Override
    public LocalDateTime convertToEntityAttribute(String dbData) {
        return LocalDateTime.parse(dbData, GlobalConstants.formatter);
    }
}
