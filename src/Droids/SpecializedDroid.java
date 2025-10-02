package Droids;

public class SpecializedDroid extends DaddyDroid {
    private int precisionBonus;
    private int criticalMultiplier;

    public SpecializedDroid(String name) {
            super(name, 100, 30);
            this.precisionBonus = 10;
            this.criticalMultiplier = 2;
        }

    @Override
    public void attack(DaddyDroid enemy) {
        int totalDamage = (damage + precisionBonus) * criticalMultiplier;
        enemy.takeDamage(totalDamage);
        System.out.println(name + " атакує " + enemy.getName() + " критично на " + totalDamage + " урону!");
    }

    @Override
    public String toString() {
        return name + " [HP: " + health + ", DMG: " + damage + ", PrecisionBonus: " + precisionBonus +
                ", CritMultiplier: " + criticalMultiplier + "]";
    }

}

