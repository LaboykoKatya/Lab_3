package Battles;

import Droids.DaddyDroid;

public abstract class DaddyBattle {
    protected DaddyDroid[] participant1;
    protected DaddyDroid[] participant2;

    public DaddyBattle(DaddyDroid[] participant1, DaddyDroid[] participant2) {
        this.participant1 = participant1;
        this.participant2 = participant2;
    }

    public abstract String fight();

    //показує скільки HP втрачено
    protected String logAttack(DaddyDroid attacker, DaddyDroid defender) {
        int before = defender.getHealth();
        String action = attacker.attack(defender); // сам текст дроїда
        int after = defender.getHealth();
        return action + " (HP " + defender.getName() + ": " + before + " → " + after + ")\n";
    }

    // для лікування
    protected String logHeal(DaddyDroid healer, DaddyDroid ally, int healedHP) {
        int before = ally.getHealth();
        ally.addHealth(healedHP);
        int after = ally.getHealth();
        return healer.getName() + " лікує " + ally.getName() +
                " на " + healedHP + " HP (HP " + ally.getName() + ": " + before + " → " + after + ")\n";
    }

}
