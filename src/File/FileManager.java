package File;

import java.io.*;

public class FileManager {
    private static final String FILE_NAME = "battle_log.txt";

    public static void saveBattle(String log) {
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            writer.write(log);
            System.out.println("Бій збережено у файл!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readBattle() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            System.out.println("Відтворення бою:");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Файл бою не знайдено!");
        }
    }

}
