package Droids;

public class CivilianDroid extends DaddyDroid  {
    private int supportLevel; // лікує союзників
    private int panicChance;  // пропускає хід

    public CivilianDroid (String name) {
        super(name, 100, 10);
        this.supportLevel = 10;
        this.panicChance = 0; // постійно не панікує
    }

    public int getSupportLevel() { return supportLevel; }

    public void healAlly(DaddyDroid ally) {
        ally.takeDamage(-supportLevel); // відновлює здоров’я
        System.out.println(name + " лікує " + ally.getName() + " на " + supportLevel + " HP!");
    }

    @Override
    public void attack(DaddyDroid enemy) {
        enemy.takeDamage(damage);
        System.out.println(name + " атакує " + enemy.getName() + " на " + damage + " урону!");
    }

    @Override
    public String toString() {
        return name + " [HP: " + health + ", DMG: " + damage + ", Support: " + supportLevel + "]";
    }

}
