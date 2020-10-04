package com.skynet.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.skynet.dto.CompanyConnectionDto;
import com.skynet.dto.ContractDTO;
import com.skynet.dto.GpsCoordinate;
import com.skynet.repositories.CompanyRepository;
import com.skynet.repositories.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ContractService {

	@Autowired
	ContractRepository contractRepository;

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	ObjectMapper objectMapper;

	public List<CompanyConnectionDto> getSimpleContracts() {
		List<CompanyConnectionDto> companyConnectionDtos = new ArrayList<>();
		List<String> simpleContracts = contractRepository.getSimpleContracts();
		List<String> companies = companyRepository.getCompanies();
		List<String> contracts = getContractsWithoutProject();

		List<ObjectNode> companiesNode = companies.stream().map(doc -> getObjectNodeFromString(doc)).collect(Collectors.toList());
		List<ObjectNode> contractsNode = contracts.stream().map(doc -> getObjectNodeFromString(doc)).collect(Collectors.toList());
		Map<String, ObjectNode> companiesMap = getIdMap(companiesNode);
		Map<String, ObjectNode> contractsMap = getIdMap(contractsNode);

		List<ObjectNode> simpleContractsNode = simpleContracts.stream().map(doc -> getObjectNodeFromString(doc)).collect(Collectors.toList());;

		for (ObjectNode currentContract : simpleContractsNode) {
			companyConnectionDtos.add(getComnpanyConnectionDTOWithFields(currentContract, companiesMap, contractsMap));
		}

		return companyConnectionDtos;
	}

	public CompanyConnectionDto getComnpanyConnectionDTOWithFields(ObjectNode node, Map<String, ObjectNode> companies, Map<String, ObjectNode> contracts) {
		CompanyConnectionDto companyConnectionDto = new CompanyConnectionDto();
		try {
			String companyId = node.get("companyId").get("$oid").asText();
			ObjectNode company = companies.get(companyId);
			if (company == null) {
				return companyConnectionDto;
			}
			JsonNode contractIdNode = node.get("contractId");
			if (contractIdNode != null) {
				SimpleDateFormat formatter = new SimpleDateFormat("M/d/yyyy");
				String contractId =  objectMapper.treeToValue(contractIdNode.get("$oid"), String.class);
				ObjectNode contract = contracts.get(contractId);
				ContractDTO contractDTO = new ContractDTO();
				contractDTO.setDescription(contract.get("description").asText());
				contractDTO.setStartDate(formatter.parse(contract.get("startDate").asText()));
				contractDTO.setEndDate(formatter.parse(contract.get("endDate").asText()));
				contractDTO.setPrice(contract.get("price").asText());
				companyConnectionDto.setContract(contractDTO);
			}

			ArrayNode coordinatesNode = ((ArrayNode) company.get("location").get("coordinates"));
			GpsCoordinate coordinate = new GpsCoordinate(
					coordinatesNode.get(0).asText(),
					coordinatesNode.get(1).asText());
			companyConnectionDto.setLocation(coordinate);
			companyConnectionDto.setName(company.get("name").asText());

			ArrayNode simpleContracts = (ArrayNode) node.get("simple_contracts");
			if (simpleContracts != null && !simpleContracts.isEmpty()) {
				List<CompanyConnectionDto> sub = new ArrayList<>();
				for (JsonNode simpleContract : simpleContracts) {
					sub.add(getComnpanyConnectionDTOWithFields((ObjectNode) simpleContract, companies, contracts));
				}
				companyConnectionDto.setSub(sub);
			}

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return companyConnectionDto;
	}

	public Map<String, ObjectNode> getIdMap(List<ObjectNode> array) {
		Map<String, ObjectNode> resultMap = new HashMap<>();
		for (ObjectNode node : array) {
			try {
				String id =  objectMapper.treeToValue(node.get("_id").get("$oid"), String.class);
				resultMap.put(id, node);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		return resultMap;
	}

	public List<String> getContractsWithoutProject() {
		return contractRepository.getContractsWithoutProjects();
	}

	public ObjectNode getObjectNodeFromString(String doc) {
		try{
			return (ObjectNode) objectMapper.readTree(doc);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
