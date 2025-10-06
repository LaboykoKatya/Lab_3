package Main;

import Droids.*;
import Battles.*;
import File.FileManager;

import java.util.*;

public class Main {
    private static List<DaddyDroid> Droids = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== МЕНЮ ===");
            System.out.println("1. Створити дроїда");
            System.out.println("2. Показати список дроїдів");
            System.out.println("3. Бій 1 на 1");
            System.out.println("4. Бій команда на команду (кількість дроїдів повинна бути парна)");
            System.out.println("5. Записати бій у файл");
            System.out.println("6. Відтворити бій з файлу");
            System.out.println("7. Вийти");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> createDroid();
                case 2 -> showDroids();
                case 3 -> oneVsOne();
                case 4 -> teamVsTeam();
                case 5 -> System.out.println("Спершу проведіть бій.");
                case 6 -> FileManager.readBattle();
                case 7 -> { return; }
            }
        }
    }

    private static void createDroid() {
        System.out.println("Оберіть тип дроїда: 1-Воєнний, 2-Цивільний, 3-Спеціальний");
        int type = sc.nextInt();
        sc.nextLine();
        System.out.print("Введіть ім'я дроїда: ");
        String name = sc.nextLine();

        DaddyDroid d = switch (type) {
            case 1 -> new MilitaryDroid(name);
            case 2 -> new CivilianDroid(name);
            case 3 -> new SpecializedDroid(name);
            default -> null;
        };

        if (d != null) {
            Droids.add(d);
            System.out.println("Створено: " + d);
        }
    }

    private static void showDroids() {
        if (Droids.isEmpty()) {
            System.out.println("Немає створених дроїдів.");
            return;
        }
        System.out.println("Список дроїдів:");
        for (int i = 0; i < Droids.size(); i++) {
            DaddyDroid d = Droids.get(i);
            System.out.print((i + 1) + ". " + d);
            if (d instanceof MilitaryDroid) System.out.print(" -> броня зменшує отриманий урон, ярість збільшує DMG після атак");
            if (d instanceof SpecializedDroid) System.out.print(" -> висока точність і критичний множник");
            if (d instanceof CivilianDroid) System.out.print(" -> лікує союзників, може панікувати");
            System.out.println();
        }
    }

    private static void oneVsOne() {
        if (Droids.size() < 2) {
            System.out.println("Створіть хоча б 2 дроїди!");
            return;
        }
        showDroids();

        int i1 = chooseDroidIndex("Оберіть номер 1 дроїда: ");
        int i2 = chooseDroidIndex("Оберіть номер 2 дроїда: ");

        DaddyBattle battle = new OneVsOneBattle(Droids.get(i1), Droids.get(i2));
        String log = battle.fight();
        System.out.println(log);
        FileManager.saveBattle(log);
    }

    private static void teamVsTeam() {
        int aliveCount = 0;
        for (DaddyDroid d : Droids) {
            if (d.isAlive()) aliveCount++;
        }


        if (aliveCount < 2) {
            System.out.println("Створіть хоча б 2 дроїди!");
            return;
        }

        if (aliveCount % 2 != 0) {
            int needed = 2 - (aliveCount % 2);
            System.out.println("Непарна кількість дроїдів. Додайте ще " + needed +
                    " дроїда(ів), щоб у кожній команді було порівну!");
            return;
        }

        showDroids();
        int teamSize = aliveCount / 2;
        DaddyDroid[] team1 = new DaddyDroid[teamSize];
        DaddyDroid[] team2 = new DaddyDroid[teamSize];
        boolean[] chosen = new boolean[Droids.size()];

        // вибір команди 1 тільки з живих
        System.out.println("Виберіть " + teamSize + " дроїдів для команди 1:");
        for (int i = 0; i < teamSize; i++) {
            int idx = chooseDroidIndex("Номер дроїда: ", chosen);
            chosen[idx] = true;
            team1[i] = Droids.get(idx);
        }

        // 2 команда теж тільки з живих, що лишилися
        int j = 0;
        for (int i = 0; i < Droids.size(); i++) {
            if (!chosen[i] && Droids.get(i).isAlive()) {
                if (j < teamSize) {
                    team2[j++] = Droids.get(i);
                }
            }
        }

        if (j < teamSize) {
            System.out.println("Недостатньо живих дроїдів для другої команди!");
            return;
        }

        System.out.println("Виберіть стратегію бою: 1-SEQUENTIAL, 2-RANDOM, 3-ATTACK_LOWEST_HP");
        int strat = sc.nextInt();
        TeamBattle.BattleStrategy strategy = switch (strat) {
            case 1 -> TeamBattle.BattleStrategy.SEQUENTIAL;
            case 2 -> TeamBattle.BattleStrategy.RANDOM;
            case 3 -> TeamBattle.BattleStrategy.ATTACK_LOWEST_HP;
            default -> TeamBattle.BattleStrategy.SEQUENTIAL;
        };

        String log;
        do {
            Battles.TeamBattle battle = new Battles.TeamBattle(team1, team2, strategy);
            log = battle.fight();
            System.out.println(log);
            if (!log.contains("Бій не завершився")) break;
            System.out.println("Бій завершився без переможця, повторюємо...");
        } while (true);

        FileManager.saveBattle(log);
    }


    private static int chooseDroidIndex(String prompt) {
        return chooseDroidIndex(prompt, new boolean[Droids.size()]);
    }

    private static int chooseDroidIndex(String prompt, boolean[] chosen) {
        while (true) {
            System.out.print(prompt);
            int idx = sc.nextInt() - 1;

            // перевірка діапазону
            if (idx < 0 || idx >= Droids.size()) {
                System.out.println("Неправильний вибір! Введіть номер від 1 до " + Droids.size());
                continue;
            }

            if (chosen[idx]) {
                System.out.println("Цей дроїд вже вибраний!");
                continue;
            }

            if (!Droids.get(idx).isAlive()) {
                System.out.println(Droids.get(idx).getName() + " мертвий і не може брати участь!");
                continue;
            }

            return idx;
        }
    }
}
