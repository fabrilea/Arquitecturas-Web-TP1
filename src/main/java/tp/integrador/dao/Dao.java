package tp.integrador.dao;

import java.sql.SQLException;

public interface Dao<T> {
    Object select(long id);
    void insert(Object obj) throws SQLException;
    void update(long id, Object obj);
    void delete(long id);
    Object find(long id);
}
