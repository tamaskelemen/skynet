package com.skynet.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.mongodb.client.MongoClient;
import com.skynet.dto.CompanyConnectionDto;
import com.skynet.dto.ContractDTO;
import com.skynet.dto.GpsCoordinate;
import com.skynet.dto.ObservationDTO;
import com.skynet.repositories.ObservationRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ObservationService {

    private ObservationRepository repository;
    private MongoClient mongoClient;
    private ObjectMapper mapper;

    public ObservationService(
            ObservationRepository repository,
            MongoClient mongoClient,
            ObjectMapper mapper
    ) {
        this.repository = repository;
        this.mongoClient = mongoClient;
        this.mapper = mapper;
    }

    public List<ObservationDTO> getAll(LocalDate startDate, LocalDate endDate) throws JsonProcessingException {
        var result = repository.findAll(
                Date.from(startDate.atStartOfDay().toInstant(ZoneOffset.UTC)),
                Date.from(endDate.atStartOfDay().toInstant(ZoneOffset.UTC)));
        List<ObservationDTO> observationDTOS = new ArrayList<>();

        for (String item: result) {
            ObservationDTO observationDTO = parse(item);
            observationDTOS.add(observationDTO);
        }

        return observationDTOS;
    }

    private ObservationDTO parse(String doc) {
        if (doc == null ) {
            return null;
        }

        try{
            JsonNode node = mapper.readTree(doc);
            ObjectId id = mapper.treeToValue(node.get("_id"), ObjectId.class);
            ObservationDTO dto = new ObservationDTO();
            dto.setId(id);

            ArrayNode coordinatesNode = ((ArrayNode) node.get("location").get("coordinates"));
            GpsCoordinate coordinate = new GpsCoordinate(
                    coordinatesNode.get(0).asText(),
                    coordinatesNode.get(1).asText());

            dto.setLocation(coordinate);
            dto.setObservationNumber(node.get("observation_number").asText());
            dto.setObservationElevation(node.get("observation_elevation").asText());
            dto.setObservationDate(mapper.convertValue(node.get("observation_date").get("$date"), Date.class));

            dto.setDust(node.get("dust").asText());
            dto.setFog(node.get("fog").asText());
            dto.setHaze(node.get("haze").asText());
            dto.setSand(node.get("sand").asText());
            dto.setSmoke(node.get("smoke").asText());

            return dto;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
