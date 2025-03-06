import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) {
        Statistics statistics = new Statistics();

        try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/resources/access.log")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                LogEntry entry = new LogEntry(line);
                statistics.addEntry(entry);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Выводим средний объём трафика за час
        System.out.printf("Средний объём трафика за час: %.2f байт/час\n", statistics.getTrafficRate());
    }
}