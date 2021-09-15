package edu.eci.TDD.service;

import edu.eci.TDD.controller.weather.dto.WeatherReportDto;
import edu.eci.TDD.repository.WeatherReportRepository;
import edu.eci.TDD.repository.document.GeoLocation;
import edu.eci.TDD.repository.document.WeatherReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MongoWeatherService
    implements WeatherService
{

    private final WeatherReportRepository repository;

    public MongoWeatherService( @Autowired WeatherReportRepository repository )
    {
        this.repository = repository;
    }

    @Override
    public WeatherReport report( WeatherReportDto weatherReportDto )
    {
        return null;
    }

    @Override
    public WeatherReport findById( String id )
    {
        throw new RuntimeException( "Implement this method" );
    }

    @Override
    public List<WeatherReport> findNearLocation( GeoLocation geoLocation, float distanceRangeInMeters )
    {
        return null;
    }

    @Override
    public List<WeatherReport> findWeatherReportsByName( String reporter )
    {
        return null;
    }


}
