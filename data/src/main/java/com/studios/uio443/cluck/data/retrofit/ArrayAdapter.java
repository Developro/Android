package com.studios.uio443.cluck.data.retrofit;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zundarik
 */

class ArrayAdapter<T> extends TypeAdapter<List<T>> {

    private Class<T> adapterclass;
    private Gson gson;

    ArrayAdapter(Gson gson, Class<T> adapterclass) {
        this.adapterclass = adapterclass;
        this.gson = gson;
    }

    @Override
    public List<T> read(JsonReader reader) throws IOException {

        List<T> list = new ArrayList<>();

        final JsonToken token = reader.peek();
        System.out.println(token);
        // Handling of Scenario 2( Check JavaDoc for the class) :
        if (token == JsonToken.STRING || token == JsonToken.NUMBER ||
                token == JsonToken.BOOLEAN) {
            @SuppressWarnings("unchecked")
            T inning = gson.fromJson(reader, adapterclass);
            list.add(inning);
        } else if (token == JsonToken.BEGIN_OBJECT) {
            // Handling of Scenario 1(Check JavaDoc for the class) :
            @SuppressWarnings("unchecked")
            T inning = gson.fromJson(reader, adapterclass);
            list.add(inning);
        } else if (token == JsonToken.BEGIN_ARRAY) {
            reader.beginArray();
            while (reader.hasNext()) {
                @SuppressWarnings("unchecked")
                T inning = gson.fromJson(reader, adapterclass);
                list.add(inning);
            }
            reader.endArray();
        }

        return list;
    }

    @Override
    public void write(JsonWriter writer, List<T> value) throws IOException {

    }
}