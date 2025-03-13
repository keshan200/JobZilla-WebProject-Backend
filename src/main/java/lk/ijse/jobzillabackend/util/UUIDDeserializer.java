package lk.ijse.jobzillabackend.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.UUID;

public class UUIDDeserializer extends JsonDeserializer<UUID> {

    @Override
    public UUID deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String rawUUID = p.getText();
        if (rawUUID.startsWith("0x")) {
            rawUUID = rawUUID.substring(2); // Remove the "0x" prefix
        }
        // Convert to valid UUID format
        String formattedUUID = rawUUID.replaceFirst("(.{8})(.{4})(.{4})(.{4})(.+)", "$1-$2-$3-$4-$5");
        return UUID.fromString(formattedUUID);
    }
}
