package com.theironyard.charlotte.services;

import com.theironyard.charlotte.entities.Schedule;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

/**
 * Created by mfahrner on 10/12/16.
 */
public interface ScheduleRepository extends CrudRepository<Schedule, Integer> {
    List<Schedule> findByHomeOrAway(String teamAbreviation, String something);
}
