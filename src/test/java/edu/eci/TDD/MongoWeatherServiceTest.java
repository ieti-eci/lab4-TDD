package edu.eci.TDD;

import edu.eci.TDD.controller.weather.dto.WeatherReportDto;
import edu.eci.TDD.exception.WeatherReportNotFoundException;
import edu.eci.TDD.repository.WeatherReportRepository;
import edu.eci.TDD.repository.document.GeoLocation;
import edu.eci.TDD.repository.document.WeatherReport;
import edu.eci.TDD.service.MongoWeatherService;
import edu.eci.TDD.service.WeatherService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestInstance( TestInstance.Lifecycle.PER_CLASS )
class MongoWeatherServiceTest
{
    WeatherService weatherService;

    @Mock
    WeatherReportRepository repository;

    @BeforeAll()
    public void setup()
    {
        weatherService = new MongoWeatherService( repository );
    }

    @Test
    void createWeatherReportCallsSaveOnRepository()
    {
        weatherService = new MongoWeatherService( repository );
        double lat = 4.7110;
        double lng = 74.0721;
        GeoLocation location = new GeoLocation( lat, lng );
        WeatherReportDto weatherReportDto = new WeatherReportDto( location, 35f, 22f, "tester", new Date() );
        weatherService.report( weatherReportDto );

        verify( repository ).save( any( WeatherReport.class ) );
    }

    @Test
    void weatherReportIdFoundTest()
    {
        weatherService = new MongoWeatherService( repository );
        String weatherReportId = "awae-asd45-1dsad";
        double lat = 4.7110;
        double lng = 74.0721;
        GeoLocation location = new GeoLocation( lat, lng );
        WeatherReport weatherReport = new WeatherReport( location, 35f, 22f, "tester", new Date() );
        when( repository.findById( weatherReportId ) ).thenReturn( Optional.of( weatherReport ) );
        WeatherReport foundWeatherReport = weatherService.findById( weatherReportId );
        Assertions.assertEquals( weatherReport, foundWeatherReport );
    }

    @Test
    void weatherReportIdNotFoundTest()
    {
        weatherService = new MongoWeatherService( repository );
        String weatherReportId = "dsawe1fasdasdoooq123";
        when( repository.findById( weatherReportId ) ).thenReturn( Optional.empty() );
        Assertions.assertThrows( WeatherReportNotFoundException.class, () -> {
            weatherService.findById( weatherReportId );
        } );
    }

    @Test
    void weatherReportNameFoundTest()
    {weatherService = new MongoWeatherService( repository );
        String weatherReportId = "awae-asd45-1dsad";
        double lat = 4.7110;
        double lng = 74.0721;
        GeoLocation location = new GeoLocation( lat, lng );
        List<WeatherReport> listaWeatherReport=new ArrayList<WeatherReport>();
        listaWeatherReport.add(new WeatherReport( location, 35f, 22f, "tester", new Date() ));
        when( repository.findByReporter("tester") ).thenReturn( listaWeatherReport );
        List<WeatherReport> wrta = weatherService.findWeatherReportsByName("tester");
        Assertions.assertEquals( wrta, listaWeatherReport );
    }

    @Test
    void weatherReportNearLocationTest()
    {
        weatherService = new MongoWeatherService( repository );
        String weatherReportId = "awae-asd45-1dsad";
        //5
        List<WeatherReport> listaWeatherReport=new ArrayList<WeatherReport>();
        listaWeatherReport.add(new WeatherReport( new GeoLocation( 0, 0 ), 35f, 22f, "tester", new Date() ));//TRUE
        listaWeatherReport.add(new WeatherReport( new GeoLocation( 5, 7 ), 35f, 22f, "tester2", new Date() ));//TRUE
        listaWeatherReport.add(new WeatherReport( new GeoLocation( 5, -1.5 ), 35f, 22f, "tester3", new Date() ));//FALSE
        when( repository.findAll()).thenReturn( listaWeatherReport );
        List<WeatherReport> wrta = weatherService.findNearLocation(new GeoLocation(3,4),5);
        Assertions.assertEquals( wrta.size(), 2 );
    }

}
