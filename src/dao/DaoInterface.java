package dao;

import model.entity.Product;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DaoInterface<T> {
    public boolean insert(T t) throws SQLException;
    public boolean update(T t) throws SQLException;
    public boolean delete(T t);
    public ArrayList<T> getAll() throws SQLException;
    public  T selectByID(T t) throws SQLException;
    public ArrayList<T> selectByCondition(String condition);

    ArrayList<Product> selectByConditon(String condition);
}
