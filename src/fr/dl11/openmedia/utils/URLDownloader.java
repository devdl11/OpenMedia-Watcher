package fr.dl11.openmedia.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Utility class for downloading content from a URL.
 *
 * <p>The {@code URLDownloader} class provides a method to fetch the content
 * of a given URL as a string. It uses an HTTP GET request to retrieve the data.</p>
 */
public class URLDownloader {

    /**
     * Downloads the content of the specified URL.
     *
     * <p>This method establishes an HTTP connection to the given URL, sends a GET request,
     * and reads the response content line by line. The content is returned as a single string.</p>
     *
     * @param urlString The URL to download content from. Must not be null or empty.
     * @return A string containing the content of the URL.
     * @throws Exception If an error occurs while connecting to the URL or reading the content.
     */
    public static String downloadContent(String urlString) throws Exception {
        StringBuilder content = new StringBuilder();
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }

        return content.toString();
    }
}