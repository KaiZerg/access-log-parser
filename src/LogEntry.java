import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

public class LogEntry {
    private final String ipAddr;
    private final LocalDateTime time;
    private final HttpMethod method;
    private final String path;
    private final int responseCode;
    private final int responseSize;
    private final String referer;
    private final UserAgent userAgent;

    public LogEntry(String logLine) {
        String[] parts = logLine.split(" ");

        // IP-адрес
        this.ipAddr = parts[0];

        // Дата и время
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("dd/MMM/yyyy:HH:mm:ss")
                .appendLiteral(' ')
                .appendOffset("+HHMM", "+0000") // Часовой пояс
                .toFormatter(Locale.ENGLISH); // Месяц на английском

        // Удаляем закрывающую квадратную скобку перед парсингом
        String dateTimeString = parts[3].substring(1) + " " + parts[4];
        dateTimeString = dateTimeString.replace("]", ""); // Удаляем ']'
        this.time = LocalDateTime.parse(dateTimeString, formatter);

        // Метод запроса
        this.method = HttpMethod.valueOf(parts[5].substring(1));

        // Путь запроса
        this.path = parts[6];

        // Код ответа
        this.responseCode = Integer.parseInt(parts[8]);

        // Размер отданных данных
        this.responseSize = Integer.parseInt(parts[9]);

        // Referer
        this.referer = parts[10].equals("\"-\"") ? null : parts[10].substring(1, parts[10].length() - 1);

        // User-Agent
        this.userAgent = new UserAgent(logLine.substring(logLine.indexOf("\"", logLine.indexOf("\"", logLine.indexOf("\"") + 1) + 1)));
    }

    // Геттеры
    public String getIpAddr() {
        return ipAddr;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public int getResponseSize() {
        return responseSize;
    }

    public String getReferer() {
        return referer;
    }

    public UserAgent getUserAgent() {
        return userAgent;
    }
}