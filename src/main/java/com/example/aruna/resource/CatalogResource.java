package com.example.aruna.resource;

import com.example.aruna.model.CatalogItem;
import com.example.aruna.model.Movie;
import com.example.aruna.model.UserRatings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
@PropertySource("classpath:endpoint-urls.properties")
public class CatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${protocol}")
    private String protocol;

    @Value("${movie-ratings-service-name}")
    private String movieRatingServiceName;

    @Value("${movie-details-service-name}")
    private String movieDetailServiceName;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getUserRatedMovieDetails(@PathVariable int userId) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme(protocol).host(movieRatingServiceName).pathSegment("/userRatings/users/" + userId).build();
        UserRatings userRatings = restTemplate.getForObject(uriComponents.toUriString(), UserRatings.class);

        List<CatalogItem> catalogItemList = userRatings.getUserRatings().stream().map(rating -> {
            UriComponents movieUriComponents = UriComponentsBuilder.newInstance().scheme(protocol).host(movieDetailServiceName).pathSegment("/movies/" + rating.getMovieId()).build();
            Movie movie = restTemplate.getForObject(movieUriComponents.toUriString(), Movie.class);
            return new CatalogItem(movie.getMovieName(), movie.getMovieDescription(), rating.getRating());
        }).collect(Collectors.toList());

        return catalogItemList;

    }
}
