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

    public void addHealth(int delta) {
        this.health += delta;
    }

    public void takeDamage(int dmg) {
        health -= dmg;
        if (health < 0) health = 0;
    }

    //метод повертає рядок
    public String attack(DaddyDroid enemy) {
        enemy.takeDamage(damage);
        return name + " атакує " + enemy.getName() + " на " + damage + " урону!\n";
    }

    @Override
    public String toString() {
        if (!isAlive()) {
            return getName() + " [МЕРТВИЙ]";
        }
        return getName() + " [HP: " + getHealth() + ", DMG: " + getDamage() + "]";
    }

}
