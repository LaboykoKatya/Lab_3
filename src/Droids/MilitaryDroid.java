package Droids;

public class MilitaryDroid extends DaddyDroid {
    private int armor;  // зменшує отриманий урон
    private int rage;   // кожна атака збільшує damage

    public MilitaryDroid(String name) {
        super(name, 100, 25);
        this.armor = 5;   // фіксована броня
        this.rage = 0;
    }

    @Override
    public void takeDamage(int dmg) {
        super.takeDamage(Math.max(dmg - armor, 0)); // броня зменшує dmg
    }

    @Override
    public String attack(DaddyDroid enemy) {
        int totalDamage = damage + rage;
        enemy.takeDamage(totalDamage);
        String result = name + " атакує " + enemy.getName() + " на " + totalDamage +
                " шкоди! (Rage +" + rage + ")\n";
        rage += 5; // після кожної атаки ярість збільшує damage
        return result;
    }

    @Override
    public String toString() {
        if (!isAlive()) {
            return super.toString();
        }
        return getName() + " [HP: " + getHealth() + ", DMG: " + getDamage() +
                ", Armor: " + armor + ", Rage: " + rage + "]";
    }

}
