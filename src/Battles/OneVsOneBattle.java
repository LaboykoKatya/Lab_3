package Battles;

import Droids.DaddyDroid;

public class OneVsOneBattle extends DaddyBattle {

    private DaddyDroid d1;
    private DaddyDroid d2;

    public OneVsOneBattle(DaddyDroid d1, DaddyDroid d2) {
        super(new DaddyDroid[]{d1}, new DaddyDroid[]{d2});
    }

    @Override
    public String fight() {
        StringBuilder log = new StringBuilder();
        DaddyDroid d1 = participant1[0];
        DaddyDroid d2 = participant2[0];

        log.append("Бій 1 на 1: ").append(d1.getName()).append(" VS ").append(d2.getName()).append("\n");

        while (d1.isAlive() && d2.isAlive()) {
            log.append(logAttack(d1, d2));
            if (!d2.isAlive()) break;
            log.append(logAttack(d2, d1));
        }

        String winner = d1.isAlive() ? d1.getName() : d2.getName();
        log.append("Переміг ").append(winner).append("!\n");

        return log.toString();
    }
}
