package Battles;

import Droids.DaddyDroid;

public class TeamBattle extends DaddyBattle  {
    public TeamBattle(DaddyDroid[] team1, DaddyDroid[] team2) {
        super(team1, team2);
    }
    @Override
    public String fight() {
        StringBuilder log = new StringBuilder();
        log.append("Бій команда на команду:\n");

        int size = Math.min(participant1.length, participant2.length);

        for (int i = 0; i < size; i++) {
            DaddyDroid d1 = participant1[i];
            DaddyDroid d2 = participant2[i];

            log.append("Пара: ").append(d1.getName()).append(" VS ").append(d2.getName()).append("\n");

            while (d1.isAlive() && d2.isAlive()) {
                log.append(logAttack(d1, d2));
                if (!d2.isAlive()) break;
                log.append(logAttack(d2, d1));
            }

            String winner = d1.isAlive() ? d1.getName() : d2.getName();
            log.append("Переміг у парі: ").append(winner).append("\n\n");
        }

        return log.toString();
    }

}
