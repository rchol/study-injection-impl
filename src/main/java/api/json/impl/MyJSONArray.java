package api.json.impl;

import api.json.JSONArray;
import api.json.JSONElement;
import java.util.Iterator;

public class MyJSONArray extends MyJSONElement implements JSONArray {

    @Override
    public void add(JSONElement element) {

    }

    @Override
    public void addAll(JSONArray array) {

    }

    @Override
    public boolean contains(JSONElement element) {
        return false;
    }

    @Override
    public JSONElement get(int i) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Iterator<JSONElement> iterator() {
        return null;
    }
}
