package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entities.Item;

public class ItemDAO extends DAO<Item> {

    public static final ItemDAO Instance = new ItemDAO();

    @Override
    public List<Item> readAll() {
        List<Item> items = new ArrayList<Item>();

        try {
            Statement statement = Connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM \"Item\"");

            while (resultSet.next()) {
                items.add(toItem(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    @Override
    public Item readOne(int id) {
        Item item = null;
        try {
            PreparedStatement statement = Connection.prepareStatement("SELECT * FROM \"Item\" WHERE id=?");

            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                item = toItem(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return item;
    }

    @Override
    public void save(Item entity) {
        try {
            PreparedStatement statement = Connection
                    .prepareStatement("INSERT INTO \"Item\" (name, cost, player_id) VALUES(?, ?, ?)");

            statement.setString(1, entity.getName());
            statement.setInt(2, entity.getCost());
            statement.setObject(3, getPlayerIdFrom(entity));

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(int id, Item updatedEntity) {
        try {
            PreparedStatement statement = Connection
                    .prepareStatement("UPDATE \"Item\" SET name=?, cost=?, player_id=? WHERE id=?");

            statement.setString(1, updatedEntity.getName());
            statement.setInt(2, updatedEntity.getCost());
            statement.setObject(3, getPlayerIdFrom(updatedEntity));
            statement.setInt(4, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement statement = Connection.prepareStatement("DELETE FROM \"Item\" WHERE id=?");

            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Item> readAll(Integer playerId) {
        List<Item> items = new ArrayList<Item>();

        try {
            PreparedStatement statement = Connection.prepareStatement("SELECT * FROM \"Item\" WHERE player_id=?");
            statement.setObject(1, playerId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                items.add(toItem(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    private Item toItem(ResultSet resultSet) throws SQLException {
        Item item = new Item();
        item.setId(resultSet.getInt("id"));
        item.setName(resultSet.getString("name"));
        item.setCost(resultSet.getInt("cost"));
        item.setPlayer(PlayerDAO.Instance.readOne(resultSet.getInt("player_id")));

        return item;
    }

    private Integer getPlayerIdFrom(Item updatedEntity) {
        return updatedEntity.getPlayer() == null ? null : updatedEntity.getPlayer().getId();
    }
}
