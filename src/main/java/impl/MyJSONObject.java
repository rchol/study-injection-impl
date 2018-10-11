package impl;

import api.json.JSONArray;
import api.json.JSONElement;
import api.json.JSONObject;
import api.json.JSONPrimitive;
import java.util.Map.Entry;
import java.util.Set;

public class MyJSONObject extends MyJSONElement implements JSONObject {

    @Override
    public void add(String property, JSONElement value) {

    }

    @Override
    public void addProperty(String property, Boolean value) {

    }

    @Override
    public void addProperty(String property, Number value) {

    }

    @Override
    public void addProperty(String property, String value) {

    }

    @Override
    public Set<Entry<String, JSONElement>> entrySet() {
        return null;
    }

    @Override
    public JSONElement get(String memberName) {
        return null;
    }

    @Override
    public JSONArray getAsJsonArray(String memberName) {
        return null;
    }

    @Override
    public JSONObject getAsJsonObject(String memberName) {
        return null;
    }

    @Override
    public JSONPrimitive getAsJsonPrimitive(String memberName) {
        return null;
    }

    @Override
    public boolean has(String memberName) {
        return false;
    }

    @Override
    public JSONElement remove(String property) {
        return null;
    }
}
