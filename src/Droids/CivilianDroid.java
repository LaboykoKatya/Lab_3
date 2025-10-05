package Droids;

import java.util.Random;

public class CivilianDroid extends DaddyDroid {
    private int supportLevel;   // кількість HP, яку може відновити союзнику
    private int panicChance;    // шанс паніки у відсотках (0–100)
    private Random random = new Random();

    public CivilianDroid(String name) {
        super(name, 100, 10);
        this.supportLevel = 15;
        this.panicChance = 25; // 25% шанс паніки
    }

    public int getSupportLevel() {
        return supportLevel;
    }

    //Атака або паніка
    @Override
    public String attack(DaddyDroid enemy) {
        if (!isAlive()) {
            return getName() + " мертвий і не може атакувати.";
        }

        // Якщо дроїд панікує — він пропускає хід
        if (random.nextInt(100) < panicChance) {
            return getName() + " панікує і пропускає хід!";
        }

        // Інакше — атакує ворога
        enemy.takeDamage(damage);
        if (!enemy.isAlive()) {
            return getName() + " атакує " + enemy.getName() + " на " + damage +
                    " шкоди. " + enemy.getName() + " помер!";
        } else {
            return getName() + " атакує " + enemy.getName() + " на " + damage +
                    " шкоди (" + enemy.getHealth() + " HP залишилось).";
        }
    }

    @Override
    public String toString() {
        if (!isAlive()) {
            return super.toString();
        }
        return getName() + " [HP: " + getHealth() + ", DMG: " + getDamage() +
                ", Support: " + supportLevel + ", PanicChance: " + panicChance + "%]";
    }
}
