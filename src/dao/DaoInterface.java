package dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DaoInterface<T>{
    public boolean insert(T t);
    public boolean update(T t);
    public boolean delete(T t);
    public ArrayList<T> getAll() throws SQLException;
    public T selectByID(T t);
    public ArrayList<T> selectByConditon(String condition);
}
