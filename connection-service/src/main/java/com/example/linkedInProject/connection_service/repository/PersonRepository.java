package com.example.linkedInProject.connection_service.repository;

import com.example.linkedInProject.connection_service.dto.PersonDto;
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
            match (personA:Person {userId: $userId}) -[:CONNECTED_TO] - (personB:Person)
            RETURN personB.userId AS userId, personB.name AS name
            """)
    List<PersonDto> getFirstDegreeConnections(@Param("userId") Long userID);

    @Query("""
            MATCH (personA:Person {userId: $userId}) -[:CONNECTED_TO]- (:Person) -[:CONNECTED_TO]-(personC:Person)
            WHERE personC<>personA
            AND NOT (personA) -[:CONNECTED_TO]- (personC)
            RETURN personC.userId AS userId, personC.name AS name
            """)
    List<PersonDto> getSecondDegreeConnections(@Param("userId") Long userID);

    @Query("""
            MATCH (personA:Person {userId: $userId}) -[:CONNECTED_TO*3]- (personD:Person)
            where personD<>personA and not (personA) -[:CONNECTED_TO]- (personD)
                and not (personA) -[:CONNECTED_TO*2]-(personD)
            RETURN personD.userId AS userId, personD.name AS name
            """)
    List<PersonDto> getThirdDegreeConnections(@Param("userId") Long userID);


    //whether already sent a connection request
    @Query("MATCH (p1:Person {userId: $senderId} ) -[r:REQUESTED_TO]-> (p2:Person {userId: $receiverId} ) " +
            "RETURN count(r) > 0")
    boolean connectionRequestExists(Long senderId, Long receiverId);

    //whether already connected
    @Query("MATCH (p1:Person {userId: $senderId} ) -[r:CONNECTED_TO]- (p2:Person {userId: $receiverId} ) " +
            "RETURN count(r) > 0")
    boolean alreadyConnected(Long senderId, Long receiverId);

    @Query("MATCH (p1:Person {userId: $senderId} ) MATCH (p2:Person {userId: $receiverId} ) " +
            "CREATE (p1) -[:REQUESTED_TO]-> (p2)")
    void addConnectionRequest(Long senderId, Long receiverId);

    //delete the REQUESTED_TO relationship and create the CONNECTED_TO
    @Query("MATCH (p1:Person {userId: $senderId} ) -[r:REQUESTED_TO]-> (p2:Person {userId: $receiverId} ) " +
            "DELETE r CREATE (p1) -[:CONNECTED_TO]-> (p2)")
    void acceptConnectionRequest(Long senderId, Long receiverId);

    //delete the connection request
    @Query("MATCH (p1:Person {userId: $senderId} )-[r:REQUESTED_TO]-> (p2:Person {userId: $receiverId} ) " +
            "DELETE r")
    void rejectConnectionRequest(Long senderId, Long receiverId);

}
