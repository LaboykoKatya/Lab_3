package Battles;

import Droids.*;
import java.util.*;

public class TeamBattle extends DaddyBattle {

    public enum BattleStrategy { SEQUENTIAL, RANDOM, ATTACK_LOWEST_HP }

    private BattleStrategy strategy;

    public TeamBattle(DaddyDroid[] team1, DaddyDroid[] team2, BattleStrategy strategy) {
        super(team1, team2);
        this.strategy = strategy;
    }

    @Override
    public String fight() {
        StringBuilder log = new StringBuilder();
        log.append("=== Бій команда на команду ===\n");

        log.append("Команда 1:\n");
        for (DaddyDroid d : participant1) {
            log.append(d).append("\n");
        }
        log.append("Команда 2:\n");
        for (DaddyDroid d : participant2) {
            log.append(d).append("\n");
        }

        int round = 1;
        int maxRounds = 20; // Обмеження на один цикл
        Random random = new Random();

        while (anyAlive(participant1) && anyAlive(participant2)) {
            log.append("Раунд ").append(round++).append(":\n");

            // Визначаємо порядок атак залежно від стратегії
            List<DaddyDroid> attackers1 = selectAttackers(participant1, strategy, random);
            List<DaddyDroid> attackers2 = selectAttackers(participant2, strategy, random);

            int size = Math.min(attackers1.size(), attackers2.size());

            for (int i = 0; i < size; i++) {
                DaddyDroid d1 = attackers1.get(i);
                DaddyDroid d2 = attackers2.get(i);

                if (d1.isAlive() && d2.isAlive()) {
                    log.append(logAttack(d1, d2));
                    if (!d2.isAlive()) continue;
                    log.append(logAttack(d2, d1));
                }

                // CivilianDroid лікує союзників
                if (d1.isAlive() && d1 instanceof CivilianDroid && i > 0) {
                    log.append(logHeal(d1, attackers1.get(i - 1), ((CivilianDroid) d1).getSupportLevel()));
                }
                if (d2.isAlive() && d2 instanceof CivilianDroid && i > 0) {
                    log.append(logHeal(d2, attackers2.get(i - 1), ((CivilianDroid) d2).getSupportLevel()));
                }

            }

            // Вивід стану команд
            log.append("Команда 1: ");
            for (DaddyDroid d : participant1) log.append(d.getName()).append("[").append(d.getHealth()).append("] ");
            log.append("\nКоманда 2: ");
            for (DaddyDroid d : participant2) log.append(d.getName()).append("[").append(d.getHealth()).append("] ");
            log.append("\n\n");

            if (round > maxRounds) {
                log.append("Бій не завершився за ").append(maxRounds).append(" раундів!\n");
                break;
            }
        }

        String winner = anyAlive(participant1) ? "Команда 1" : "Команда 2";
        log.append("Перемогла ").append(winner).append("!\n");
        return log.toString();
    }

    private boolean anyAlive(DaddyDroid[] team) {
        for (DaddyDroid d : team) if (d.isAlive()) return true;
        return false;
    }

    private List<DaddyDroid> selectAttackers(DaddyDroid[] team, BattleStrategy strat, Random random) {
        List<DaddyDroid> alive = new ArrayList<>();
        for (DaddyDroid d : team) if (d.isAlive()) alive.add(d);

        switch (strat) {
            case RANDOM -> Collections.shuffle(alive, random);
            case ATTACK_LOWEST_HP -> alive.sort(Comparator.comparingInt(DaddyDroid::getHealth));
            case SEQUENTIAL -> { /* залишаємо у порядку */ }
        }
        return alive;
    }
}

