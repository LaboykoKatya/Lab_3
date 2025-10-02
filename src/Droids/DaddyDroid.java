package Droids;

public abstract class DaddyDroid {
    protected String name;
    protected int health;
    protected int damage;

    public DaddyDroid(String name, int health, int damage) {
        this.name = name;
        this.health = health;
        this.damage = damage;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void takeDamage(int dmg) {
        health -= dmg;
        if (health < 0) health = 0;
    }

    public void attack(DaddyDroid enemy) {
        enemy.takeDamage(damage);
        System.out.println(name + " атакує " + enemy.getName() + " на " + damage + " урону!");
    }

    @Override
    public String toString() {
        return name + " [HP: " + health + ", DMG: " + damage + "]";
    }
}
