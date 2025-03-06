import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Statistics {
    private long totalTraffic = 0; // Используем long для totalTraffic
    private LocalDateTime minTime = null;
    private LocalDateTime maxTime = null;

    public Statistics() {
        // Конструктор без параметров
    }

    public void addEntry(LogEntry entry) {
        // Добавляем объём трафика
        this.totalTraffic += entry.getResponseSize();

        // Обновляем minTime и maxTime
        LocalDateTime entryTime = entry.getTime();
        if (this.minTime == null || entryTime.isBefore(this.minTime)) {
            this.minTime = entryTime;
        }
        if (this.maxTime == null || entryTime.isAfter(this.maxTime)) {
            this.maxTime = entryTime;
        }
    }

    public double getTrafficRate() {
        if (minTime == null || maxTime == null || minTime.isAfter(maxTime)) {
            System.out.println("Ошибка: minTime или maxTime не инициализированы или minTime > maxTime");
            return 0.0;
        }

        // Вычисляем разницу в часах
        long hours = ChronoUnit.HOURS.between(minTime, maxTime);
        if (hours == 0) {
            System.out.println("Разница между minTime и maxTime меньше часа");
            return 0.0;
        }

        System.out.println("minTime: " + minTime);
        System.out.println("maxTime: " + maxTime);
        System.out.println("Разница в часах: " + hours);
        System.out.println("Общий трафик: " + totalTraffic);

        // Возвращаем средний объём трафика за час
        return (double) totalTraffic / hours;
    }
}