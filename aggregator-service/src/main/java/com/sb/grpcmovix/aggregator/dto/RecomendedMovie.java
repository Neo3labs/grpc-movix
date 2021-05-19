package com.sb.grpcmovix.aggregator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecomendedMovie {

    private String title;
    private int year;
    private double rating;

    public RecomendedMovie(String title, int year, double rating) {
        this.title = title;
        this.year = year;
        this.rating = rating;
    }
}
