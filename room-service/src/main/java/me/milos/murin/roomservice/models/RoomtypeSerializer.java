package me.milos.murin.roomservice.models;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class RoomtypeSerializer extends JsonSerializer<Roomtype> {

    @Override
    public void serialize(Roomtype roomtype, JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider) throws IOException,
            JsonProcessingException {

        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", roomtype.getId());
        jsonGenerator.writeNumberField("bedAmount", roomtype.getBedAmount());
        jsonGenerator.writeNumberField("extraBeds", roomtype.getExtraBeds());
        jsonGenerator.writeNumberField("price", roomtype.getPrice());
        jsonGenerator.writeNumberField("size", roomtype.getSize());
        jsonGenerator.writeStringField("name", roomtype.getName());
        jsonGenerator.writeStringField("info", roomtype.getInfo());
        jsonGenerator.writeEndObject();
    }

}
