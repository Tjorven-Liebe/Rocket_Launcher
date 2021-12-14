package de.tjorven.util.logger;

public class ConsoleSender {

    private static final Logger waterLogger = new Logger();

    private static final ConsoleSender waterConsoleSender = new ConsoleSender();

    public static ConsoleSender getInstance() {
        return waterConsoleSender;
    }

    @Deprecated
    public void sendMessageRaw(String... message) {
        for (String string : message)
            waterLogger.raw(string);
    }

    public void sendMessage(String... message) {
        for (String string : message)
            waterLogger.info(string);
    }

}
