package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import entities.AbstractEntity;
import utils.DataBaseConfigReaderUtil;

public abstract class DAO<T extends AbstractEntity> {

    protected static final String URL;
    protected static final String USER_NAME;
    protected static final String PASSWORD;
    
    protected static Connection Connection;

    static {
        URL = DataBaseConfigReaderUtil.getValueOf("URL");
        USER_NAME = DataBaseConfigReaderUtil.getValueOf("USERNAME");
        PASSWORD = DataBaseConfigReaderUtil.getValueOf("PASSWORD");

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            Connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public abstract List<T> readAll();
    
    public abstract T readOne(int id);

    public abstract void save(T entity);

    public abstract void update(int id, T updatedEntity);

    public abstract void delete(int id);

    public void saveAbstract(AbstractEntity entity) {
        save((T)entity);
    }

    public void updateAbstract(int id, AbstractEntity updatedEntity) {
        update(id, (T)updatedEntity);
    }
}
