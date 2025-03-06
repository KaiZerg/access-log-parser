import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Создаем объект для чтения данных из консоли

        System.out.print("Введите путь к файлу: ");  // Пример: src/resources/access.log
        String path = scanner.nextLine();

        File file = new File(path);

        if (!file.exists() || !file.isFile()) {
            System.out.println("Файл не найден или указанный путь ведет к папке.");
            return;
        }

        int totalRequests = 0; // Общее количество запросов
        int yandexBotCount = 0; // Количество запросов от YandexBot
        int googleBotCount = 0; // Количество запросов от Googlebot

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                totalRequests++;

                // Извлекаем User-Agent (последний элемент в строке, заключенный в кавычки)
                String userAgent = extractUserAgent(line);
                if (userAgent == null || userAgent.isEmpty()) {
                    continue; // Пропускаем строки, где User-Agent не найден
                }

                // Обрабатываем User-Agent
                String botName = extractBotName(userAgent);
                if (botName != null) {
                    if (botName.equals("YandexBot")) {
                        yandexBotCount++;
                    } else if (botName.equals("Googlebot")) {
                        googleBotCount++;
                    }
                }
            }

            // Вычисляем доли запросов
            double yandexBotPercentage = (double) yandexBotCount / totalRequests * 100;
            double googleBotPercentage = (double) googleBotCount / totalRequests * 100;

            // Выводим результаты
            System.out.printf("Общее количество запросов: %d\n", totalRequests);
            System.out.printf("Доля запросов от YandexBot: %.2f%%\n", yandexBotPercentage);
            System.out.printf("Доля запросов от Googlebot: %.2f%%\n", googleBotPercentage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод для извлечения User-Agent из строки.
     *
     * @param line Строка из файла.
     * @return User-Agent или null, если не удалось извлечь.
     */
    private static String extractUserAgent(String line) {
        // Ищем предпоследнюю и последнюю кавычки
        int lastQuoteIndex = line.lastIndexOf('"');
        int secondLastQuoteIndex = line.lastIndexOf('"', lastQuoteIndex - 1);

        if (lastQuoteIndex == -1 || secondLastQuoteIndex == -1) {
            return null; // Если кавычки не найдены, возвращаем null
        }

        // Извлекаем User-Agent (часть строки между предпоследней и последней кавычками)
        String userAgent = line.substring(secondLastQuoteIndex + 1, lastQuoteIndex).trim();

        // Удаляем лишние кавычки, если они есть
        if (userAgent.startsWith("\"") && userAgent.endsWith("\"")) {
            userAgent = userAgent.substring(1, userAgent.length() - 1);
        }

        return userAgent.isEmpty() ? null : userAgent;
    }

    /**
     * Метод для извлечения имени бота из User-Agent.
     *
     * @param userAgent Строка User-Agent.
     * @return Имя бота (YandexBot или Googlebot) или null, если бот не обнаружен.
     */
    private static String extractBotName(String userAgent) {
        // Ищем часть User-Agent в скобках, содержащую "compatible"
        int startIndex = userAgent.indexOf("(compatible;");
        if (startIndex == -1) {
            return null; // Если "compatible" не найдено, возвращаем null
        }

        int endIndex = userAgent.indexOf(')', startIndex);
        if (endIndex == -1) {
            return null; // Если закрывающая скобка не найдена, возвращаем null
        }

        // Извлекаем содержимое скобок
        String compatiblePart = userAgent.substring(startIndex + 1, endIndex);

        // Разделяем по точке с запятой
        String[] parts = compatiblePart.split(";");
        if (parts.length < 2) {
            return null; // Если недостаточно частей, возвращаем null
        }

        // Очищаем второй фрагмент от пробелов и извлекаем имя бота
        String fragment = parts[1].trim();
        String[] botParts = fragment.split("/");
        if (botParts.length == 0) {
            return null;
        }

        String botName = botParts[0].trim();
        if (botName.equals("YandexBot") || botName.equals("Googlebot")) {
            return botName;
        }

        return null;
    }
}