package pl.foodflow.infrastructure.database.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Converter(autoApply = true)
public class OffsetDateTimeAttributeConverter implements AttributeConverter<OffsetDateTime, Timestamp> {
    @Override
    public Timestamp convertToDatabaseColumn(OffsetDateTime attribute) {
        if (attribute == null) {
            return null;
        }
        return Timestamp.valueOf(attribute.toLocalDateTime());
    }

    @Override
    public OffsetDateTime convertToEntityAttribute(Timestamp dbData) {
        if (dbData == null) {
            return null;
        }
        return dbData.toLocalDateTime().atOffset(ZoneOffset.UTC);
    }
}
