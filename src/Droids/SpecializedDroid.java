package Droids;

import java.util.Random;

public class SpecializedDroid extends DaddyDroid {

    private boolean bonusActive;

    public SpecializedDroid(String name, int health, int damage) {
        super(name, health, damage);
        this.bonusActive = false;
    }

    public SpecializedDroid(String name) {
        this(name, 100, 25); // стандартні значення HP і урону
    }

    @Override
    public String attack(DaddyDroid enemy) {
        if (!isAlive()) {
            return getName() + " мертвий і не може атакувати.";
        }

        Random random = new Random();

        // 30% шанс активувати спеціальну здатність
        boolean specialBoost = random.nextInt(100) < 30;
        int actualDamage = this.damage;

        StringBuilder log = new StringBuilder();

        if (specialBoost) {
            actualDamage *= 2;
            log.append(getName()).append(" активував спеціальну здатність! Подвійну шкоду!\n");
        }

        enemy.takeDamage(actualDamage);

        if (!enemy.isAlive()) {
            log.append(getName()).append(" атакує ").append(enemy.getName())
                    .append(" на ").append(actualDamage).append(" шкоди.\n")
                    .append(enemy.getName()).append(" помер!");
        } else {
            log.append(getName()).append(" атакує ").append(enemy.getName())
                    .append(" на ").append(actualDamage).append(" шкоди (")
                    .append(enemy.getHealth()).append(" HP залишилось).");
        }

        return log.toString();
    }

    @Override
    public String toString() {
        if (!isAlive()) {
            return super.toString();
        }
        return getName() + " [HP: " + getHealth() + ", DMG: " + getDamage() + ", Critical Bonus]";
    }
}

