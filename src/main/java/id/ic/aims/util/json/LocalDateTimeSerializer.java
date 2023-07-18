package id.ic.aims.util.json;

import java.io.IOException;
import java.time.LocalDateTime;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import id.ic.aims.util.date.DateHelper;

public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    public LocalDateTimeSerializer() {
        super();
    }

    @Override
    public void serialize(LocalDateTime dt, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeString(DateHelper.formatDateTime(dt));
    }
}
