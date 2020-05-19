package serializable.persistence;
import java.util.Map;

public interface IPersistence<T> {
	
	Map<String, T> getAll();
	T get(String key);
	void add(String key, T value);
	void update(String key, T value);
	void delete(String key);
	void clear();
}
