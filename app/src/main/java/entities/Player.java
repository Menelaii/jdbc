package entities;

import java.util.List;

public class Player extends AbstractEntity {

    private String name;
    private int gold;
    private Race race;
    private Clan clan;
    private List<Item> items;

    public Player() {

    }

    public Player(String name, int gold) {
        this.name = name;
        this.gold = gold;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public Clan getClan() {
        return clan;
    }

    public void setClan(Clan clan) {
        this.clan = clan;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Player [id=" + getId() + ", name=" + name + ", gold=" + gold + ", race_id="
                + (race == null ? "null" : race.getId()) + ", clan_id="
                + (clan == null ? "null" : clan.getId()) + "]";
    }
}
