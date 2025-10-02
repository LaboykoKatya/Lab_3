package Droids;

public class MilitaryDroid extends DaddyDroid {
    private int armor;  // зменшує отриманий урон
    private int rage;   // кожна атака збільшує damage

    public MilitaryDroid(String name) {
        super(name, 100, 25);
        this.armor = 5;   // фіксована броня
        this.rage = 0;
    }

    public int getArmor() { return armor; }

    @Override
    public void takeDamage(int dmg) {
        super.takeDamage(Math.max(dmg - armor, 0)); // броня зменшує dmg
    }

    @Override
    public void attack(DaddyDroid enemy) {
        enemy.takeDamage(damage + rage);
        System.out.println(name + " атакує " + enemy.getName() + " на " + (damage + rage) + " урону! (Rage +" + rage + ")");
        rage += 5; // після кожної атаки ярість збільшує damage
    }

    @Override
    public String toString() {
        return name + " [HP: " + health + ", DMG: " + damage + ", Armor: " + armor + ", Rage: " + rage + "]";
    }

}
