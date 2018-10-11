package api.json;

public interface Mapper<T> {
    T map(JSONElement e) throws NoSuchFieldException, IllegalAccessException;
}
