import java.io.IOException;
import java.util.List;

public interface Manageable<T> {
    void add(T t) throws IOException;

    void update(T t) throws IOException;

    void delete(String id) throws IOException;

    T search(String id) throws IOException;

    List<T> getAll() throws IOException;
}
