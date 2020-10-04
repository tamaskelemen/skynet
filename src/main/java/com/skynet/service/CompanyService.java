package com.skynet.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.mongodb.client.MongoClient;
import com.skynet.dto.CompanyConnectionDto;
import com.skynet.dto.ContractDTO;
import com.skynet.dto.GpsCoordinate;
import com.skynet.dto.ScreenGpsCoordinates;
import com.skynet.repositories.CompanyRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyService {

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	MongoClient mongoClient;

	@Autowired
	ObjectMapper objectMapper;

	public CompanyConnectionDto getCompanyByProjectName(String projectName) {
		MongoOperations mongoOps = new MongoTemplate(mongoClient, "mongodb_data");
		String queryString = String.format("{project: \"%s\"}", projectName);
		BasicQuery basicQuery = new BasicQuery(queryString);
		String doc = mongoOps.findOne(basicQuery, String.class, "company");

		return parse(doc);
	}

	public List<String> getCompaniesInArea(ScreenGpsCoordinates screenGpsCoordinates) {
		String startingLatitude = screenGpsCoordinates.getStartPointPosition().getLatitude();
		String startingLongitude = screenGpsCoordinates.getStartPointPosition().getLongitude();
		String endingLatitude = screenGpsCoordinates.getEndPointPosition().getLatitude();
		String endingLongitude = screenGpsCoordinates.getEndPointPosition().getLongitude();

		return companyRepository.getCompaniesInArea(startingLatitude, startingLongitude, endingLatitude, endingLongitude);
	}

	private CompanyConnectionDto parse(String doc) {
		if (doc == null ) {
			return null;
		}

		try{
			JsonNode node = objectMapper.readTree(doc);
			ObjectId id = objectMapper.treeToValue(node.get("_id"), ObjectId.class);
			CompanyConnectionDto dto = new CompanyConnectionDto();
			dto.setId(id);

			ArrayNode coordinatesNode = ((ArrayNode) node.get("location").get("coordinates"));
			GpsCoordinate coordinate = new GpsCoordinate(
					coordinatesNode.get(0).asText(),
					coordinatesNode.get(1).asText());

			dto.setLocation(coordinate);
			dto.setName(node.get("name").asText());
			dto.setProject(objectMapper.treeToValue(node.get("project"), new ArrayList<>().getClass()));
			dto.setContract(objectMapper.treeToValue(node.get("contract"), ContractDTO.class));

			JsonNode subNode = node.get("sub");
			JsonNode arrayNode = objectMapper.readTree(subNode == null ? "" : subNode.asText());

			List<CompanyConnectionDto> subs = new ArrayList<>();

			if (arrayNode.isArray()) {
				for (JsonNode iterator: arrayNode) {
					subs.add(parse(iterator.asText()));
				}
			}

			dto.setSub(subs);

			return dto;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return null;
	}

	public List<String> getCompanies(){
		return companyRepository.getCompanies();
	}
}
