package com.example.linkedInProject.connection_service.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
@Getter @Setter
public class Person {

    @Id
    @GeneratedValue
    private Long id; //neo4j needs this id

    private Long userId; //id from our postgres

    private String name;


}
