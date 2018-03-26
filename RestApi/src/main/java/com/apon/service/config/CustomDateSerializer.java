package com.apon.service.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * This class makes sure that all dates to the frontend can be formatted to 'dd-MM-yyyy'.
 */
@SuppressWarnings("ALL")
public class CustomDateSerializer extends StdSerializer<Date> {

    private SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

    public CustomDateSerializer() {
        this(null);
    }

    public CustomDateSerializer(Class t) {
        super(t);
    }

    @Override
    public void serialize(Date date, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeString(formatter.format(date));
    }
}
