public class UserAgent {
    private final String osType;
    private final String browser;

    public UserAgent(String userAgentString) {
        // Определение типа операционной системы
        if (userAgentString.contains("Windows")) {
            this.osType = "Windows";
        } else if (userAgentString.contains("Mac OS")) {
            this.osType = "macOS";
        } else if (userAgentString.contains("Linux")) {
            this.osType = "Linux";
        } else {
            this.osType = "Other";
        }

        // Определение браузера
        if (userAgentString.contains("Edge")) {
            this.browser = "Edge";
        } else if (userAgentString.contains("Firefox")) {
            this.browser = "Firefox";
        } else if (userAgentString.contains("Chrome")) {
            this.browser = "Chrome";
        } else if (userAgentString.contains("Opera")) {
            this.browser = "Opera";
        } else {
            this.browser = "Other";
        }
    }

    // Геттеры
    public String getOsType() {
        return osType;
    }

    public String getBrowser() {
        return browser;
    }
}