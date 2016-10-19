package com.theironyard.charlotte.services;

import com.theironyard.charlotte.entities.TeamIdentifier;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by mfahrner on 10/10/16.
 */
public interface TeamRepository extends CrudRepository<TeamIdentifier, Integer> {
    TeamIdentifier findFirstByNameIgnoreCase(String teamName);
    TeamIdentifier findByAbbreviation(String abrv);
}
