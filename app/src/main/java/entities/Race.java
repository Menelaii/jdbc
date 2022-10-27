package entities;

import java.util.List;

public class Race extends AbstractEntity {

    private String name;
    private List<Player> players;
    private List<Clan> clans;

    public Race() {

    }

    public Race(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Clan> getClans() {
        return clans;
    }

    public void setClans(List<Clan> clans) {
        this.clans = clans;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    @Override
    public String toString() {
        return "Race [id=" + getId() + ", name=" + name + "]";
    }
}
