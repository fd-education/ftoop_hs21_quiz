package ch.ffhs.quiz.messages;

import ch.ffhs.quiz.connectivity.Connection;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.Instant;
import java.util.Objects;

public class MessageUtils {
    private static final Gson gson = getGson();

    private static Gson getGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantAdapter());

        return builder.create();
    }

    public static <T extends Message> T parse(String text, Class<T> messageType) {
        Objects.requireNonNull(text);
        Objects.requireNonNull(messageType);

        try {
            final T message = gson.fromJson(text, messageType);
            if (message == null)
                throw new IllegalArgumentException("%s does not contain a message of type %s".formatted(text, messageType));
            return message;
        } catch (JsonSyntaxException e) {
            throw new IllegalArgumentException("%s could not be parsed as %s".formatted(text, messageType));
        }
    }

    public static String serialize(Message message) {
        return gson.toJson(message);
    }

    private static class InstantAdapter extends TypeAdapter<Instant> {
        @Override
        public void write(JsonWriter jsonWriter, Instant instant) throws IOException {
            if (instant == null) {
                jsonWriter.nullValue();
                return;
            }
            jsonWriter.beginObject();
            jsonWriter.name("epochSecond").value(instant.getEpochSecond());
            jsonWriter.name("nano").value(instant.getNano());
            jsonWriter.endObject();
        }

        @Override
        public Instant read(JsonReader jsonReader) throws IOException {
            jsonReader.beginObject();
            int nano = 0;
            long epochSecond = 0;
            while (jsonReader.hasNext()) {
                String name = jsonReader.nextName();
                if (name.equals("epochSecond")){
                    epochSecond = jsonReader.nextLong();
                } else if (name.equals("nano")) {
                    nano = jsonReader.nextInt();
                }
            }
            jsonReader.endObject();
            return Instant.ofEpochSecond(epochSecond, nano);
        }
    }
}
