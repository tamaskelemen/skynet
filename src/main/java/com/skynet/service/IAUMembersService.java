package com.skynet.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.skynet.dto.GpsCoordinate;
import com.skynet.dto.IAUMembersDTO;
import com.skynet.repositories.IAUMembersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IAUMembersService {

    @Autowired
    private IAUMembersRepository repository;

     @Autowired
     private ObjectMapper mapper;

    public List<IAUMembersDTO> findAll() {
        var members = repository.findAll();
        var result = new ArrayList<IAUMembersDTO>();

        for (String member: members ) {
            try {
                result.add(parse(member));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    private IAUMembersDTO parse(String doc) throws JsonProcessingException {
        JsonNode node = mapper.readTree(doc);
        IAUMembersDTO dto = new IAUMembersDTO();

        ArrayNode coordinatesNode = ((ArrayNode) node.get("location").get("coordinates"));
        GpsCoordinate coordinate = new GpsCoordinate(
                coordinatesNode.get(0).asText(),
                coordinatesNode.get(1).asText());

        dto.setLocation(coordinate);
        dto.setAddress(node.get("address").asText());
        dto.setName(node.get("name").asText());

        return dto;
    }
}
