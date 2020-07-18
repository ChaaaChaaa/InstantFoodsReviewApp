package com.myapp.instantfoodsreviewapp.model;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;


import java.lang.reflect.Type;

public class MyDeserializer implements JsonDeserializer<User> {
    @Override
    public User deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
           String userString = jsonElement.getAsString();
            JsonElement userJson = new JsonParser().parse(userString);

            return new Gson().fromJson(userJson, User.class);
    }
}
