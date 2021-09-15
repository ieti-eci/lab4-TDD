package edu.eci.TDD.repository;

import edu.eci.TDD.repository.document.WeatherReport;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WeatherReportRepository
    extends MongoRepository<WeatherReport, String>
{
}
