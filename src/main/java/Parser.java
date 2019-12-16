import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


class Parser {
    private static final String API_CALL_TEMPLATE = "https://api.openweathermap.org/data/2.5/forecast?q=";
    private static final String USER_AGENT = "Mozilla/5.0";

    private static String downloadJsonRawData(String city) throws Exception {
        String api_key = FileWork.readApiKey();
        String urlString = API_CALL_TEMPLATE + city + api_key;
        URL urlObject = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = connection.getResponseCode();
        if (responseCode == 404) {
            throw new IllegalArgumentException();
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    private static List<String> makeWeatherList(String data) throws Exception {
        List<String> weatherList = new ArrayList<>();

        JsonNode arrNode = new ObjectMapper().readTree(data).get("list");
        if (arrNode.isArray()) {
            for (final JsonNode objNode : arrNode)
                weatherList.add(objNode.toString());
        }
        return weatherList;
    }

    private static String operator(String city) throws java.lang.Exception {
        String buffer_data = FileWork.checkBuffer(city);
        if (!buffer_data.isEmpty())
            return buffer_data;
        String data = downloadJsonRawData(city);
        List<String> convertData = makeWeatherList(data);
        String weatherList = JsonParser.jsonParse(convertData);
        FileWork.addToBuffer(weatherList, city);
        return weatherList;
    }

    static String getWeather(String city) throws Exception {
        FileWork.readBufferFile();
        return operator(city);
    }
}