package dao;

import java.util.ArrayList;
import java.util.DatabaseConnectionp;

public interface DaoInterfaceProduct<T> {
    ArrayList<T> selectByCondition(DatabaseConnectionp<String, Object> filters);
}
