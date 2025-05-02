package dao;

import java.util.ArrayList;
import java.util.Map;

public interface DaoInterfaceProduct<T> {
    ArrayList<T> selectByCondition(Map<String, Object> filters);
}