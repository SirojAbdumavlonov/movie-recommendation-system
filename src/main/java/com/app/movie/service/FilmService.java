package com.app.movie.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


@Service
@RequiredArgsConstructor
public class FilmService {


    public Object getMoviesByYear(int year) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://moviesverse1.p.rapidapi.com/most-popular-movies"))
                .header("X-RapidAPI-Key", "588bdd4f86msh8cfca87f3eca2fcp1e2491jsn44fd2bd7e458")
                .header("X-RapidAPI-Host", "moviesverse1.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

        return response.body();
    }


}
