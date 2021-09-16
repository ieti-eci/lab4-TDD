package edu.eci.TDD;
import edu.eci.TDD.controller.weather.dto.NearByWeatherReportsQueryDto;
import edu.eci.TDD.controller.weather.dto.WeatherReportDto;
import edu.eci.TDD.repository.WeatherReportRepository;
import edu.eci.TDD.repository.document.GeoLocation;
import edu.eci.TDD.repository.document.WeatherReport;
import edu.eci.TDD.service.MongoWeatherService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.FormatSchema;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
@TestInstance( TestInstance.Lifecycle.PER_CLASS )
public class WeatherReportControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;



    @MockBean
    WeatherReportRepository repository;

    @Test
    public void createTest() throws Exception {

        WeatherReportDto wrDto = new WeatherReportDto(new GeoLocation(0, 0), 35f, 22f, "tester", new Date());
        WeatherReport wr =this.restTemplate.postForObject("http://localhost:" + port + "/v1/weather", wrDto,WeatherReport.class );
        verify( repository ).save( any( WeatherReport.class ) );
    }
    @Test
    public void findByIdTest() throws Exception {

        String weatherReportId="abcde";
        WeatherReport wr= new WeatherReport(new GeoLocation(0, 0), 35f, 22f, "tester", new Date());
        when( repository.findById( weatherReportId ) ).thenReturn( Optional.of( wr ) );
        WeatherReport wr2 = this.restTemplate.getForObject("http://localhost:" + port + "/v1/weather/abcde", WeatherReport.class);
        Assertions.assertEquals(wr.getHumidity()==wr2.getHumidity() &&
				 wr.getReporter().equals(wr2.getReporter()) && 
				 wr.getTemperature()==wr2.getTemperature() &&
				wr.getGeoLocation().getLat()==wr2.getGeoLocation().getLat() &&
						wr.getGeoLocation().getLng()==wr2.getGeoLocation().getLng() &&
				 wr.getCreated().equals(wr2.getCreated()),true);


    }
    @Test
    public void findNearByReportsTest() throws Exception {
        //5
        List<WeatherReport> listaWeatherReport=new ArrayList<WeatherReport>();
        listaWeatherReport.add(new WeatherReport( new GeoLocation( 0, 0 ), 35f, 22f, "tester", new Date() ));//TRUE
        listaWeatherReport.add(new WeatherReport( new GeoLocation( 5, 7 ), 35f, 22f, "tester2", new Date() ));//TRUE
        listaWeatherReport.add(new WeatherReport( new GeoLocation( 5, -1.5 ), 35f, 22f, "tester3", new Date() ));//FALSE
        when( repository.findAll()).thenReturn( listaWeatherReport );
        NearByWeatherReportsQueryDto nQuery=new NearByWeatherReportsQueryDto(new GeoLocation(3,4),5);

        assertThat(this.restTemplate.postForObject("http://localhost:" + port + 
        		"/v1/weather/nearby", nQuery, List.class).size()).isEqualTo(2);

    }

    @Test
    public void findByReporterNameTest() throws Exception {
        double lat = 4.7110;
        double lng = 74.0721;
        GeoLocation location = new GeoLocation( lat, lng );
        List<WeatherReport> lWR=new ArrayList<>();
        lWR.add(new WeatherReport( location, 35f, 22f, "tester", new Date() ));
        when( repository.findByReporter("tester") ).thenReturn( lWR );
        List<WeatherReport> wrl = this.restTemplate.getForObject("http://localhost:" + port + "/v1/weather/reporter/tester", List.class);
        ObjectMapper mapper = new ObjectMapper(); 
        JsonNode node = mapper.convertValue(wrl.get(0), JsonNode.class);
        Assertions.assertEquals(lWR.get(0).getReporter(),node.findValue("reporter").textValue());
    }

}
