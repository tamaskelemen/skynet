package com.skynet.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.skynet.dto.*;
import com.skynet.repositories.MeteorRepository;
import org.apache.tomcat.jni.Local;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MeteorService {

    @Autowired
    MeteorRepository repository;

    @Autowired
    ObjectMapper mapper;


    public List<MeteorDTO> findAll(Optional<String> shower, Optional<String> startDate, Optional<String> endDate) {
        var result = new ArrayList<MeteorDTO>();

        Date start = null;
        Date end = null;

        var startString = startDate.orElse(null);
        var endString = endDate.orElse(null);

        if ( startString != null) {
            start = Date.from(LocalDate.parse(startString).atStartOfDay().toInstant(ZoneOffset.UTC));
        }

        if (endString != null) {
            end = Date.from(LocalDate.parse(endString).atStartOfDay().toInstant(ZoneOffset.UTC));
        }

        List<String> magnitudes = repository.findAllMagnitude(shower, start, end);

        for (String magnitude: magnitudes) {
            try {
                JsonNode node = mapper.readTree(magnitude);
                MeteorDTO dto = new MeteorDTO();
                String sessionId =  node.get("obs_session_id").asText();
                String coordinates = repository.getSession(sessionId);

                JsonNode coordinateNode = mapper.readTree(coordinates);
                ArrayNode coordinatesNode = ((ArrayNode) coordinateNode.get("location").get("coordinates"));
                GpsCoordinate coordinate = new GpsCoordinate(
                        coordinatesNode.get(0).asText(),
                        coordinatesNode.get(1).asText());

                MagnitudeDTO magnitudeDTO = new MagnitudeDTO();
                magnitudeDTO.setMagN6(node.get("mag_n6").asInt());
                magnitudeDTO.setMagN5(node.get("mag_n5").asInt());
                magnitudeDTO.setMagN4(node.get("mag_n4").asInt());
                magnitudeDTO.setMagN3(node.get("mag_n3").asInt());
                magnitudeDTO.setMagN2(node.get("mag_n2").asInt());
                magnitudeDTO.setMagN1(node.get("mag_n1").asInt());
                magnitudeDTO.setMag0(node.get("mag_0").asInt());
                magnitudeDTO.setMag1(node.get("mag_1").asInt());
                magnitudeDTO.setMag2(node.get("mag_2").asInt());
                magnitudeDTO.setMag3(node.get("mag_3").asInt());
                magnitudeDTO.setMag4(node.get("mag_4").asInt());
                magnitudeDTO.setMag5(node.get("mag_5").asInt());
                magnitudeDTO.setMag6(node.get("mag_6").asInt());

                dto.setShower(node.get("shower").asText());

                dto.setLocation(coordinate);
                dto.setMagnitude(magnitudeDTO);
                result.add(dto);

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
