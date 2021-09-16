package edu.eci.TDD.repository;

import edu.eci.TDD.repository.document.WeatherReport;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface WeatherReportRepository
    extends MongoRepository<WeatherReport, String>
{
    List<WeatherReport> findByReporter(String reporter);

}
