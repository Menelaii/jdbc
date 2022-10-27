package entities;

public class Item extends AbstractEntity {

    private String name;
    private int cost;
    private Player player;

    public Item() {

    }

    public Item(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public String toString() {
        return "Item [id=" + getId() + ", cost=" + cost + ", name=" + name + ", player_id="
                + (player == null ? "null" : player.getId()) + "]";
    }
}
