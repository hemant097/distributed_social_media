package com.example.linkedInProject.connection_service.repository;

import com.example.linkedInProject.connection_service.entity.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends Neo4jRepository<Person,Long> {

    Optional<Person> findByUserId(Long userId);

    @Query("""
            match (personA:Person {userId: $userId}) -[:CONNECTED_TO] -> (personB:Person)
            return personB
            """)
    List<Person> getFirstDegreeConnections(@Param("userId") Long userID);

    @Query("""
            MATCH (personA:Person {userId: $userId}) -[r1:CONNECTED_TO]- (personB:Person) -[r2:CONNECTED_TO]-(personC:Person)
            where personC<>personA and not (personA) -[:CONNECTED_TO]- (personC)
            RETURN personC;
            """)
    List<Person> getSecondDegreeConnections(@Param("userId") Long userID);

    @Query("""
            MATCH (personA:Person {userId: $userId}) -[:CONNECTED_TO*3]- (personC:Person)
            where personC<>personA and not (personA) -[:CONNECTED_TO]- (personC)
            RETURN personC
            """)
    List<Person> getThirdDegreeConnections(@Param("userId") Long userID);

}
