package app;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Callable;

import dao.ClanDAO;
import dao.DAO;
import dao.ItemDAO;
import dao.PlayerDAO;
import dao.RaceDAO;
import entities.AbstractEntity;
import entities.Clan;
import entities.Item;
import entities.Player;
import entities.Race;

public class CRUDDialog {

    private final Scanner scanner;
    private final HashMap<String, DAO<? extends AbstractEntity>> daos;
    private final HashMap<String, Callable<? extends AbstractEntity>> factoryFuncs;

    public CRUDDialog(Scanner scanner) {
        this.scanner = scanner;

        factoryFuncs = new HashMap<>(
                Map.of(Clan.class.getSimpleName(), () -> createClan(),
                        Item.class.getSimpleName(), () -> createItem(),
                        Player.class.getSimpleName(), () -> createPlayer(),
                        Race.class.getSimpleName(), () -> createRace()));

        daos = new HashMap<>(
                Map.of(
                        Clan.class.getSimpleName(), ClanDAO.Instance,
                        Item.class.getSimpleName(), ItemDAO.Instance,
                        Player.class.getSimpleName(), PlayerDAO.Instance,
                        Race.class.getSimpleName(), RaceDAO.Instance));
    }

    public void create(String entitySimpleName) {
        try {
            var entity = factoryFuncs.get(entitySimpleName).call();
            daos.get(entitySimpleName).saveAbstract(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(String entitySimpleName) {
        int id = getId();
        if (id == -1) {
            return;
        }

        var dao = daos.get(entitySimpleName);
        System.out.println(dao.readOne(id));

        try {
            System.out.println(Thread.currentThread().getName());
            var updatedEntity = factoryFuncs.get(entitySimpleName).call();
            dao.updateAbstract(id, updatedEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void read(String entitySimpleName) {
        var dao = daos.get(entitySimpleName);

        int id = getId();
        if (id == -1) {
            var entities = dao.readAll();
            for (var entity : entities) {
                System.out.println(entity);
            }

            if(entities.size() == 0) {
                System.out.println("empty table");
            }

            return;
        }

        System.out.println(dao.readOne(id));
    }

    public void delete(String entitySimpleName) {
        var dao = daos.get(entitySimpleName);

        int id = getId();
        if (id == -1) {
            return;
        }

        dao.delete(id);
    }

    private int getId() {
        System.out.println("enter id or -1:");
        return nextInt();
    }

    private Race createRace() {
        System.out.println(Thread.currentThread().getName());
        var race = new Race();

        System.out.println("enter name:");
        race.setName(scanner.nextLine());

        return race;
    }

    private Player createPlayer() {
        var player = new Player();

        System.out.println("enter name:");
        player.setName(scanner.nextLine());

        System.out.println("enter gold:");
        player.setGold(nextInt());

        System.out.println("enter race_id:");
        player.setRace(RaceDAO.Instance.readOne(getId()));

        System.out.println("enter clan_id:");
        player.setClan(ClanDAO.Instance.readOne(getId()));

        return player;
    }

    private Item createItem() {
        var item = new Item();

        System.out.println("enter name:");
        item.setName(scanner.nextLine());

        System.out.println("enter cost:");
        item.setCost(nextInt());

        System.out.println("enter player_id:");
        item.setPlayer(PlayerDAO.Instance.readOne(getId()));

        return item;
    }

    private Clan createClan() {
        var clan = new Clan();

        System.out.println("enter name:");
        clan.setName(scanner.nextLine());

        System.out.println("enter race_id:");
        clan.setRace(RaceDAO.Instance.readOne(getId()));

        return clan;
    }

    private int nextInt() {
        int number = scanner.nextInt();
        scanner.nextLine();
        return number;
    }
}
