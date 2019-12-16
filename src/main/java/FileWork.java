import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


class FileWork {
    private static String buffer_file = System.getProperty("user.dir") + "/src/main/resources/buffer.txt";
    private static String api_file = System.getProperty("user.dir") + "/src/main/resources/api_key.txt";
    private static Map<String, String> buffer = new HashMap<>();

    static String readApiKey() throws IOException {
        return Files.readAllLines(Paths.get(api_file)).get(0);
    }

    static void addToBuffer(String weather, String city) throws java.lang.Exception {
        FileWriter writer = new FileWriter(buffer_file, true);
        writer.append(String.valueOf('\n')).append(city).append(String.valueOf('\n')).append(weather);
        writer.flush();
        writer.close();
    }

    static void readBufferFile() {
        try {
            FileReader fr = new FileReader(buffer_file);
            BufferedReader reader = new BufferedReader(fr);
            String line;
            int i = 0;
            StringBuilder weather = new StringBuilder();
            String city = "";
            while ((line = reader.readLine()) != null) {
                if ((i % 42) == 1) {
                    city = line;
                }
                weather.append('\n').append(line);
                if (i % 42 == 41) {
                    buffer.put(city, weather.toString());
                    weather.delete(0,weather.capacity());
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String checkBuffer(String city) {
        if (buffer.containsKey(city))
            return buffer.get(city);
        return "";
    }
}
