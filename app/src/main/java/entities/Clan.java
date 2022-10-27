package entities;

import java.util.List;

public class Clan extends AbstractEntity {

    private String name;
    private Race race;
    private List<Player> players;

    public Clan() {

    }

    public Clan(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    @Override
    public String toString() {
        return "Clan [id=" + getId() + ", name=" + name + ", race_id=" + (race == null ? "null" : race.getId()) + "]";
    }
}
