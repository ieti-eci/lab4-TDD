package edu.eci.TDD.service;

import edu.eci.TDD.controller.weather.dto.WeatherReportDto;
import edu.eci.TDD.repository.document.GeoLocation;
import edu.eci.TDD.repository.document.WeatherReport;

import java.util.List;

public interface WeatherService
{
	
	
    WeatherReport report( WeatherReportDto weatherReportDto );

    WeatherReport findById( String id );

    List<WeatherReport> findNearLocation( GeoLocation geoLocation, float distanceRangeInMeters );

    List<WeatherReport> findWeatherReportsByName( String reporter );

}
