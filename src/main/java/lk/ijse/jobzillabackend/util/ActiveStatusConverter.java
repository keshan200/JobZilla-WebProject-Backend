package lk.ijse.jobzillabackend.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ActiveStatusConverter implements AttributeConverter<Boolean, String> {

    @Override
    public String convertToDatabaseColumn(Boolean active) {
        return (active != null && active) ? "Active" : "Deactivate";
    }

    @Override
    public Boolean convertToEntityAttribute(String status) {
        return "Active".equalsIgnoreCase(status);
    }
}
