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
            System.out.println("4. Бій команда на команду(кількість дроїдів повинна бути парна)");
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
        for (int i = 0; i < Droids.size(); i++) {
            System.out.println((i + 1) + ". " + Droids.get(i));
        }
    }

    private static void oneVsOne() {
        if (Droids.size() < 2) {
            System.out.println("Створіть хоча б 2 дроїди!");
            return;
        }
        showDroids();
        System.out.print("Оберіть номер 1 дроїда: ");
        int i1 = sc.nextInt() - 1;
        System.out.print("Оберіть номер 2 дроїда: ");
        int i2 = sc.nextInt() - 1;

        DaddyBattle battle = new OneVsOneBattle(Droids.get(i1), Droids.get(i2));
        String log = battle.fight();
        System.out.println(log);
        FileManager.saveBattle(log);
    }

    private static void teamVsTeam() {
        int total = Droids.size();

        if (total < 2) {
            System.out.println("Створіть хоча б 2 дроїди!");
            return;
        }

        // Перевірка на парну кількість дроїдів
        if (total % 2 != 0) {
            int needed = 2 - (total % 2); // скільки дроїдів додати для парності
            System.out.println("Непарна кількість дроїдів. Додайте ще " + needed +
                    " дроїда(ів), щоб у кожній команді було порівну!");
            return;
        }

        // Парна кількість дроїдів
        showDroids();
        int teamSize = total / 2;
        DaddyDroid[] team1 = new DaddyDroid[teamSize];
        DaddyDroid[] team2 = new DaddyDroid[teamSize];
        boolean[] chosen = new boolean[total];

        System.out.println("Виберіть " + teamSize + " дроїдів для команди 1 (вводьте номери з меню):");
        for (int i = 0; i < teamSize; i++) {
            int idx;
            do {
                System.out.print("Номер дроїда: ");
                idx = sc.nextInt() - 1;
                if (idx < 0 || idx >= total || chosen[idx]) {
                    System.out.println("Неправильний вибір або дроїд вже обраний!");
                    idx = -1;
                }
            } while (idx == -1);
            chosen[idx] = true;
            team1[i] = Droids.get(idx);
        }

        // Формуємо команду 2 з решти дроїдів
        int j = 0;
        for (int i = 0; i < total; i++) {
            if (!chosen[i]) {
                team2[j++] = Droids.get(i);
            }
        }

        Battles.TeamBattle battle = new Battles.TeamBattle(team1, team2);
        String log = battle.fight();
        System.out.println(log);
        FileManager.saveBattle(log);
    }

}