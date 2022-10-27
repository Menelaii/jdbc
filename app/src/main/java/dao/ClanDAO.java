package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entities.Clan;

public class ClanDAO extends DAO<Clan> {

    public static final ClanDAO Instance = new ClanDAO();

    @Override
    public List<Clan> readAll() {
        List<Clan> clans = new ArrayList<Clan>();
        try {
            Statement statement = Connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM \"Clan\"");

            while (resultSet.next()) {
                clans.add(toClan(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clans;
    }

    @Override
    public Clan readOne(int id) {
        Clan clan = null;
        try {
            PreparedStatement statement = Connection.prepareStatement("SELECT * FROM \"Clan\" WHERE id=?");

            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            
            if(resultSet.next()) {
                clan = toClan(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clan;
    }

    @Override
    public void save(Clan entity) {
        try {
            PreparedStatement statement = Connection.prepareStatement("INSERT INTO \"Clan\"(name, race_id) VALUES(?, ?)");
            statement.setString(1, entity.getName());
            statement.setObject(2, getRaceIdFrom(entity));
            
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(int id, Clan updatedEntity) {
        try {
            PreparedStatement statement = Connection.prepareStatement("UPDATE \"Clan\" SET name=?, race_id=? WHERE id=?");

            statement.setString(1, updatedEntity.getName());
            statement.setObject(2, getRaceIdFrom(updatedEntity));
            statement.setInt(3, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement statement = Connection.prepareStatement("DELETE FROM \"Clan\" WHERE id=?");

            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Clan> readAll(Integer raceId) {
        List<Clan> clans = new ArrayList<Clan>();
        try {
            PreparedStatement statement = Connection.prepareStatement("SELECT * FROM \"Clan\" WHERE race_id=?");
            statement.setObject(1, raceId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                clans.add(toClan(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clans;
    }

    private Clan toClan(ResultSet resultSet) throws SQLException {
        Clan clan = new Clan();
        clan.setId(resultSet.getInt("id"));
        clan.setName(resultSet.getString("name"));
        clan.setRace(RaceDAO.Instance.readOne(resultSet.getInt("race_id")));

        return clan;
    }

    private Integer getRaceIdFrom(Clan updatedEntity) {
        return updatedEntity.getRace() == null ? null : updatedEntity.getRace().getId();
    }
}
