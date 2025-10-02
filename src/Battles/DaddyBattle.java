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

    protected String logAttack(DaddyDroid attacker, DaddyDroid defender) {
        attacker.attack(defender);
        return attacker.getName() + " атакує " + defender.getName() + " (HP " + defender.getHealth() + ")\n";
    }
}
