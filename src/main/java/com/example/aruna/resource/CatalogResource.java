package com.example.aruna.resource;

import com.example.aruna.model.CatalogItem;
import com.example.aruna.model.Movie;
import com.example.aruna.model.UserRatings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class CatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getUserRatedMovieDetails (@PathVariable int userId) {
        UserRatings userRatings = restTemplate.getForObject("http://movie-ratings-service/userRatings/users/" + userId, UserRatings.class);

        List<CatalogItem> catalogItemList = userRatings.getUserRatings().stream().map(rating -> {
           Movie movie = restTemplate.getForObject("http://movie-details-service/movies/" + rating.getMovieId(), Movie.class);
           return new CatalogItem(movie.getMovieName(), movie.getMovieDescription(), rating.getRating());
        }).collect(Collectors.toList());

        return catalogItemList;

    }
}
