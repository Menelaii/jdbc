package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entities.Player;

public class PlayerDAO extends DAO<Player> {

    public static final PlayerDAO Instance = new PlayerDAO();

    @Override
    public List<Player> readAll() {
        List<Player> players = new ArrayList<Player>();

        try {
            Statement statement = Connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM \"Player\"");

            while (resultSet.next()) {
                players.add(toPlayer(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return players;
    }

    @Override
    public Player readOne(int id) {
        Player player = null;
        try {
            PreparedStatement statement = Connection.prepareStatement("SELECT * FROM \"Player\" WHERE id=?");

            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                player = toPlayer(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return player;
    }

    @Override
    public void save(Player entity) {
        try {
            PreparedStatement statement = Connection
                    .prepareStatement("INSERT INTO \"Player\"(name, gold, clan_id, race_id) VALUES(?, ?, ?, ?)");

            statement.setString(1, entity.getName());
            statement.setInt(2, entity.getGold());
            statement.setObject(3, getClanIdFrom(entity));
            statement.setObject(4, getRaceIdFrom(entity));

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(int id, Player updatedEntity) {
        try {
            PreparedStatement statement = Connection
                    .prepareStatement("UPDATE \"Player\" SET name=?, gold=?, race_id=?, clan_id=? WHERE id=?");

            statement.setString(1, updatedEntity.getName());
            statement.setInt(2, updatedEntity.getGold());
            statement.setObject(3, getRaceIdFrom(updatedEntity));
            statement.setObject(4, getClanIdFrom(updatedEntity));
            statement.setInt(5, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement statement = Connection.prepareStatement("DELETE FROM \"Player\" WHERE id=?");

            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Player> readAll(Integer clanId) {
        List<Player> players = new ArrayList<Player>();

        try {
            PreparedStatement statement = Connection.prepareStatement("SELECT * FROM \"Player\" WHERE clan_id=?");
            statement.setObject(1, clanId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                players.add(toPlayer(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return players;
    }

    private Player toPlayer(ResultSet resultSet) throws SQLException {
        Player player = new Player();
        player.setId(resultSet.getInt("id"));
        player.setName(resultSet.getString("name"));
        player.setGold(resultSet.getInt("gold"));
        player.setClan(ClanDAO.Instance.readOne(resultSet.getInt("clan_id")));
        player.setRace(RaceDAO.Instance.readOne(resultSet.getInt("race_id")));

        return player;
    }

    private Integer getClanIdFrom(Player entity) {
        return entity.getClan() == null ? null : entity.getClan().getId();
    }

    private Integer getRaceIdFrom(Player entity) {
        return entity.getRace() == null ? null : entity.getRace().getId();
    }
}
