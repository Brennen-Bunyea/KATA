package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@SpringBootApplication
@RestController
public class DemoApplication
{

	private static final String API_KEY = "b8576bc538156e4266d27dca7fef89eb";

	// Method to fetch weather data from the OpenWeatherMap API
	private String fetchWeather(String city) throws Exception {
		// Construct the API URL with the city name and API key
		String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + API_KEY + "&units=metric";

		// Create a connection to the API
		URL url = new URL(apiUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET"); // Set the request method to GET

		// Read the API response
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		StringBuilder response = new StringBuilder(); // Used to build the response string
		String line;

		while ((line = reader.readLine()) != null) { // Read each line of the response
			response.append(line); // Add the line to the response
		}
		reader.close(); // Close the reader

		return response.toString(); // Return the full API response as a string
	}

	// Runs the application on local server
	public static void main(String[] args)
	{
		SpringApplication.run(DemoApplication.class, args);
	}

	//Fetches weather data
	//Allows frontend to talk to backend
	@CrossOrigin(origins = "*")
	@GetMapping("/weather")
	public String weather(@RequestParam(value = "city", defaultValue = "Milwaukee") String city) throws Exception {
		return fetchWeather(city);
	}
}