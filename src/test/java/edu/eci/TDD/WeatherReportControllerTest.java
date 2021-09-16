package edu.eci.TDD;
import edu.eci.TDD.repository.document.GeoLocation;
import edu.eci.TDD.repository.document.WeatherReport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
public class WeatherReportControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void createTest() throws Exception {
        //WeatherReport wr = new WeatherReport(new GeoLocation(0, 0), 35f, 22f, "tester", new Date());
        assertThat(
                this.restTemplate.getForObject("http://localhost:" + port + "/v1/weather", WeatherReport.class));
    }
    @Test
    public void findByIdTest() throws Exception {
        assertThat(
                this.restTemplate.getForObject( "http://localhost:" + port + "/v1/weather/123ff", WeatherReport.class ) );
    }
    @Test
    public void findNearByReportsTest() throws Exception {
        List<WeatherReport> ls=new ArrayList<WeatherReport>();
        assertThat(
                this.restTemplate.getForObject( "http://localhost:" + port + "/v1/weather/nearby", List.class ) ).isEqualTo(ls);
    }

    @Test
    public void findByReporterIdTest() throws Exception {
        List<WeatherReport> ls=new ArrayList<WeatherReport>();
        assertThat(
                this.restTemplate.getForObject( "http://localhost:" + port + "/v1/weather/reporter/123ff", List.class ) ).isEqualTo(ls);
    }

}
