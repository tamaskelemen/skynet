package com.skynet.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.skynet.dto.ClubsDTO;
import com.skynet.dto.GpsCoordinate;
import com.skynet.dto.IAUMembersDTO;
import com.skynet.repositories.ClubsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClubsService {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ClubsRepository repository;

    public List<ClubsDTO> getAll() {
        List<String> clubs = repository.getAll();
        var result = new ArrayList<ClubsDTO>();

        for (String club: clubs) {
            try {
                result.add(parse(club));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    private ClubsDTO parse(String doc) throws JsonProcessingException {
        JsonNode node = mapper.readTree(doc);
        ClubsDTO dto = new ClubsDTO();

        ArrayNode coordinatesNode = ((ArrayNode) node.get("location").get("coordinates"));
        GpsCoordinate coordinate = new GpsCoordinate(
                coordinatesNode.get(0).asText(),
                coordinatesNode.get(1).asText());

        dto.setLocation(coordinate);
        dto.setNote(node.get("note").asText());
        dto.setName(node.get("name").asText());

        return dto;
    }
}
