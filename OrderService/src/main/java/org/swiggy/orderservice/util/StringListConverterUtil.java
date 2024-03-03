package org.swiggy.orderservice.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.swiggy.orderservice.model.MenuItem;

import java.util.List;

@Converter(autoApply = true)
public class StringListConverterUtil implements AttributeConverter<List<MenuItem>, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public String convertToDatabaseColumn(List<MenuItem> strings) {
        try {
            return objectMapper.writeValueAsString(strings);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<MenuItem> convertToEntityAttribute(String s) {
        if(s == null) {
            return null;
        }
        try {
            return objectMapper.readValue(s, new TypeReference<List<MenuItem>>() {});
        } catch (Exception ex) {
            return null;
        }
    }
}
