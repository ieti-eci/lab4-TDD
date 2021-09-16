package edu.eci.TDD.service;

import edu.eci.TDD.controller.weather.dto.WeatherReportDto;
import edu.eci.TDD.exception.WeatherReportNotFoundException;
import edu.eci.TDD.repository.WeatherReportRepository;
import edu.eci.TDD.repository.document.GeoLocation;
import edu.eci.TDD.repository.document.WeatherReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
//aa
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
    	WeatherReport weatherReport = new WeatherReport(weatherReportDto);
        return repository.save(weatherReport);
    }

    @Override
    public WeatherReport findById( String id ) {
    	Optional<WeatherReport> optional = repository.findById( id );
        if ( optional.isPresent() ){
            return optional.get();
        } else {
            throw new WeatherReportNotFoundException();
        }
    }

    @Override
    public List<WeatherReport> findNearLocation( GeoLocation geoLocation, float distanceRangeInMeters )
    {
        List<WeatherReport> rta=new ArrayList<>();
        List<WeatherReport> weatherRpts=repository.findAll();
        for(WeatherReport rp:weatherRpts){
            if(pitagoras(rp.getGeoLocation().getLat(),rp.getGeoLocation().getLng(),geoLocation.getLat(),
                    geoLocation.getLng())<=distanceRangeInMeters){
                rta.add(rp);
            }
        }
        return rta;
    }

    @Override
    public List<WeatherReport> findWeatherReportsByName( String reporter )
    {


        return repository.findByReporter(reporter);
    }
    private double pitagoras(double lat1, double lng1,double lat2, double lng2){
        return Math.pow(Math.pow((lat1-lat2),2)+Math.pow((lng1-lng2),2),0.5);
    }


}
