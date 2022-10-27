package dao;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Race;

public class RaceDAO extends DAO<Race> {

    public static final RaceDAO Instance = new RaceDAO();

    @Override
    public void delete(int id) {
        try {
            PreparedStatement statement = Connection.prepareStatement("DELETE FROM \"Race\" WHERE id=?");

            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Race> readAll() {
        List<Race> races = new ArrayList<Race>();

        try {
            Statement statement = Connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM \"Race\"");

            while (resultSet.next()) {
                races.add(toRace(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return races;
    }

    @Override
    public void save(Race entity) {
        try {
            PreparedStatement statement = Connection.prepareStatement("INSERT INTO \"Race\"(name) VALUES(?)");
            statement.setString(1, entity.getName());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Race readOne(int id) {
        Race race = null;
        try {
            Connection.setSchema("public");
            PreparedStatement statement = Connection.prepareStatement("SELECT * FROM \"Race\" WHERE id=?");

            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                race = toRace(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return race;
    }

    @Override
    public void update(int id, Race updatedEntity) {
        try {
            PreparedStatement statement = Connection.prepareStatement("UPDATE \"Race\" SET name=? WHERE id=?");

            statement.setString(1, updatedEntity.getName());
            statement.setInt(2, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Race toRace(ResultSet resultSet) throws SQLException {
        Race race = new Race();
        race.setId(resultSet.getInt("id"));
        race.setName(resultSet.getString("name"));
        
        return race;
    }
}
