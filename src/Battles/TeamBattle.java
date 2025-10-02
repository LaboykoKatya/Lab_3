package Battles;

import Droids.DaddyDroid;
import Droids.CivilianDroid;

public class TeamBattle extends DaddyBattle  {
    public TeamBattle(DaddyDroid[] team1, DaddyDroid[] team2) {
        super(team1, team2);
    }
    @Override
    public String fight() {
        StringBuilder log = new StringBuilder();
        log.append("Бій команда на команду:\n");

        int round = 1;
        int maxRounds = 100; // Страховка від нескінченності

        while (anyAlive(participant1) && anyAlive(participant2) && round <= maxRounds) {
            log.append("Раунд ").append(round++).append(":\n");

            int size = Math.min(participant1.length, participant2.length);

            for (int i = 0; i < size; i++) {
                DaddyDroid d1 = participant1[i];
                DaddyDroid d2 = participant2[i];

                if (d1.isAlive() && d2.isAlive()) {
                    log.append(logAttack(d1, d2));
                    log.append(logAttack(d2, d1));
                }

                // CivilianDroid лікує союзників після атак
                if (d1.isAlive() && d1 instanceof CivilianDroid && i > 0) {
                    ((CivilianDroid) d1).healAlly(participant1[i - 1]);
                }
                if (d2.isAlive() && d2 instanceof CivilianDroid && i > 0) {
                    ((CivilianDroid) d2).healAlly(participant2[i - 1]);
                }
            }

            // Вивід стану команд після раунду
            log.append("Команда 1: ");
            for (DaddyDroid d : participant1) {
                log.append(d.getName()).append("[").append(d.getHealth()).append("] ");
            }
            log.append("\nКоманда 2: ");
            for (DaddyDroid d : participant2) {
                log.append(d.getName()).append("[").append(d.getHealth()).append("] ");
            }
            log.append("\n\n");
        }

        if (round > maxRounds) {
            log.append("Бій не завершився за максимальну кількість раундів.\n");
        }

        String winner = anyAlive(participant1) ? "Команда 1" : "Команда 2";
        log.append("Перемогла ").append(winner).append("!\n");

        return log.toString();
    }

    private boolean anyAlive(DaddyDroid[] team) {
        for (DaddyDroid d : team) {
            if (d.isAlive()) return true;
        }
        return false;
    }
}
