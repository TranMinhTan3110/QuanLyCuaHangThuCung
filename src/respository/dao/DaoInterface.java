package respository.dao;

import java.util.ArrayList;

public interface DaoInterface<T> {
	public boolean insert(T t);

	public boolean update(T t);

	public boolean delete(T t); // Giữ nguyên để phù hợp với ProductDAO

	public ArrayList<T> getAll();

	public T selectByID(int id); // Đã chỉnh sửa cho khớp với ProductDAO

}
