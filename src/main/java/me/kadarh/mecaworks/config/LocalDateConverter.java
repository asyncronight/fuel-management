package me.kadarh.mecaworks.config;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;
import java.time.LocalDate;

/**
 * @author kadarH
 */

@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate,Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDate date) {
        return (date==null)?null:Date.valueOf(date);
    }

    @Override
    public LocalDate convertToEntityAttribute(Date dbData) {
        return (dbData==null?null:dbData.toLocalDate());
    }
}
