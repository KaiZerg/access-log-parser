import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
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

            // Чтение содержимого файла
            try (FileReader fileReader = new FileReader(path)) {
                BufferedReader reader = new BufferedReader(fileReader);

                int totalLines = 0;
                int maxLength = 0;
                int minLength = Integer.MAX_VALUE;

                String line;
                while ((line = reader.readLine()) != null) {
                    totalLines++;

                    int currentLength = line.length();
                    if (currentLength > 1024) {
                        throw new LineTooLongException("Строка длиннее 1024 символов!");
                    }

                    if (currentLength > maxLength) {
                        maxLength = currentLength;
                    }

                    if (currentLength < minLength) {
                        minLength = currentLength;
                    }
                }

                System.out.println("Общее количество строк в файле: " + totalLines);
                System.out.println("Длина самой длинной строки в файле: " + maxLength);
                System.out.println("Длина самой короткой строки в файле: " + minLength);

            } catch (FileNotFoundException e) {
                System.err.println("Файл не найден: " + e.getMessage());
            } catch (LineTooLongException e) {
                System.err.println(e.getMessage());
                break; // Прекращаем выполнение программы
            } catch (Exception e) {
                e.printStackTrace(); // Обрабатываем другие возможные исключения
            }
        }
    }
}

// Собственное исключение для слишком длинных строк
class LineTooLongException extends RuntimeException {
    public LineTooLongException(String message) {
        super(message);
    }
}