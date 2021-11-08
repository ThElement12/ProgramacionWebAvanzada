package com.example.backend.Repositories;

import com.example.backend.Models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;

@Repository
public interface SensorRepository extends JpaRepository<Sensor,Integer> {
    String queryBy = "select id,generation_date as generationDate, temperature, humidity,id_device as idDevice from sensor where id_device = :device order by generation_date desc limit 10";
    @Query(value = queryBy,nativeQuery = true)
    List<Sensor> findLastStatusByDevice(Integer device);
}
