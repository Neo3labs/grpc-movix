package com.sb.grpcmovix.aggregator.service;

import com.sb.grpcmovix.aggregator.dto.RecomendedMovie;
import com.sb.grpcmovix.aggregator.dto.UserGenre;
import com.sb.grpcmovix.common.Genre;
import com.sb.grpcmovix.movie.MovieSearchRequest;
import com.sb.grpcmovix.movie.MovieSearchResponse;
import com.sb.grpcmovix.movie.MovieServiceGrpc;
import com.sb.grpcmovix.user.UserGenreUpdateRequest;
import com.sb.grpcmovix.user.UserResponse;
import com.sb.grpcmovix.user.UserSearchRequest;
import com.sb.grpcmovix.user.UserServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMovieService {

    @GrpcClient("user-service")
    private UserServiceGrpc.UserServiceBlockingStub userStub;

    @GrpcClient("movie-service")
    private MovieServiceGrpc.MovieServiceBlockingStub movieStub;

    public List<RecomendedMovie> getUserMovieSuggestions(String loginId){

        UserSearchRequest userSearchRequest = UserSearchRequest.newBuilder().setLoginId(loginId).build();
        UserResponse userResponse = this.userStub.getUserGenre(userSearchRequest);
        MovieSearchRequest movieSearchRequest = MovieSearchRequest.newBuilder().setGenre(userResponse.getGenre()).build();
        MovieSearchResponse movieSearchResponse = this.movieStub.getMovies(movieSearchRequest);
        return movieSearchResponse.getMovieList()
                .stream()
                .map(movieDto -> new RecomendedMovie(movieDto.getTitle(), movieDto.getYear(), movieDto.getRating())).collect(Collectors.toList());
    }

    public void setUserGenre(UserGenre userGenre){
        UserGenreUpdateRequest userGenreUpdateRequest = UserGenreUpdateRequest.newBuilder().setLoginId(userGenre.getLoginId()).setGenre(Genre.valueOf(userGenre.getGenre().toUpperCase())).build();
        UserResponse userResponse = this.userStub.updateUserGenre(userGenreUpdateRequest);
    }

}
