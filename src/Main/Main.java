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
            System.out.println("4. Бій команда на команду");
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
        if (Droids.size() < 4) {
            System.out.println("Створіть хоча б 4 дроїди!");
            return;
        }

        List<DaddyDroid> team1 = new ArrayList<>();
        List<DaddyDroid> team2 = new ArrayList<>();
        for (int i = 0; i < Droids.size(); i++) {
            if (i % 2 == 0) team1.add(Droids.get(i));
            else team2.add(Droids.get(i));
        }

        DaddyBattle battle = new TeamBattle(team1.toArray(new DaddyDroid[0]),
                team2.toArray(new DaddyDroid[0]));
        String log = battle.fight();
        System.out.println(log);
        FileManager.saveBattle(log);
    }
}