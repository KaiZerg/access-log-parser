import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Создаем объект для чтения данных из консоли

        int correctFileCount = 0; // Счетчик для хранения количества правильных путей к файлам

        while (true) { // Бесконечный цикл
            System.out.print("Введите путь к файлу: ");
            String path = scanner.nextLine();

            File file = new File(path);

            boolean fileExists = file.exists();
            boolean isFile = file.isFile();

            if (!fileExists || !isFile) {
                System.out.println("Файл не найден или указанный путь ведет к папке.");
                continue;
            }

            correctFileCount++;

            System.out.printf("Путь указан верно. Это файл номер %d.\n", correctFileCount);
        }
    }
}
