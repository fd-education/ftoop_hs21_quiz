package ch.ffhs.quiz.messages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

/**
 * A helper class for messages, mostly providing methods to convert messages to json and back.
 */
public class MessageUtils {
    private static final Gson gson = getGson();

    // To prevent an instantiation of this helper class
    private MessageUtils() {}

    // Creates a customized gson instance
    private static Gson getGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantAdapter());
        builder.registerTypeAdapter(Duration.class, new DurationAdapter());

        return builder.create();
    }

    /**
     * Parse a message of a given type from a given string containing the JSON formatted message.
     *
     * @param text        the text containing the message
     * @param messageType the message type
     * @return the parsed message
     */
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

    /**
     * Serialize a message to a JSON formatted string.
     *
     * @param message the message
     * @return the JSON formatted string
     */
    public static String serialize(Message message) {
        return gson.toJson(message);
    }

    // This adapter is needed because an Instant cannot be properly serialized by GSON
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
                if (name.equals("epochSecond")) {
                    epochSecond = jsonReader.nextLong();
                } else if (name.equals("nano")) {
                    nano = jsonReader.nextInt();
                }
            }
            jsonReader.endObject();
            return Instant.ofEpochSecond(epochSecond, nano);
        }
    }

    // This adapter is needed because a Duration object cannot be properly serialized by GSON
    private static class DurationAdapter extends TypeAdapter<Duration> {
        @Override
        public void write(JsonWriter jsonWriter, Duration duration) throws IOException {
            if (duration == null) {
                jsonWriter.nullValue();
                return;
            }
            jsonWriter.beginObject();
            jsonWriter.name("seconds").value(duration.getSeconds());
            jsonWriter.name("nanoseconds").value(duration.getNano());
            jsonWriter.endObject();
        }

        @Override
        public Duration read(JsonReader jsonReader) throws IOException {
            jsonReader.beginObject();
            int nano = 0;
            long seconds = 0;
            while (jsonReader.hasNext()) {
                String name = jsonReader.nextName();
                if (name.equals("seconds")) {
                    seconds = jsonReader.nextLong();
                } else if (name.equals("nanoseconds")) {
                    nano = jsonReader.nextInt();
                }
            }
            jsonReader.endObject();
            return Duration.ofSeconds(seconds, nano);
        }
    }
}
