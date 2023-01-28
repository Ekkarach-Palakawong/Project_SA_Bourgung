package ku.cs.services;

public interface DataSource<T> {
    T readData();
    void insertData(T t);
}
