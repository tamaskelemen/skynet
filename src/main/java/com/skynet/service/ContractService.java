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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

	public List<CompanyConnectionDto> getSimpleContracts(String startDate, String endDate) {
		List<CompanyConnectionDto> companyConnectionDtos = new ArrayList<>();
		List<String> simpleContracts = contractRepository.getSimpleContracts();
		List<String> companies = companyRepository.getCompanies();
		List<String> contracts = getContractsWithoutProject(startDate, endDate);

		List<ObjectNode> companiesNode = companies.stream().map(doc -> getObjectNodeFromString(doc)).collect(Collectors.toList());
		List<ObjectNode> contractsNode = contracts.stream().map(doc -> getObjectNodeFromString(doc)).collect(Collectors.toList());
		Map<String, ObjectNode> companiesMap = getIdMap(companiesNode);
		Map<String, ObjectNode> contractsMap = getIdMap(contractsNode);

		List<ObjectNode> simpleContractsNode = simpleContracts.stream().map(doc -> getObjectNodeFromString(doc)).collect(Collectors.toList());;

		for (ObjectNode currentContract : simpleContractsNode) {
			fillFields(currentContract, companiesMap, contractsMap);
			mergeContracts(currentContract);
			companyConnectionDtos.add(getComnpanyConnectionDTOWithFields(currentContract, companiesMap, contractsMap));
		}

		return companyConnectionDtos;
	}

	public void fillFields(ObjectNode node, Map<String, ObjectNode> companies, Map<String, ObjectNode> contracts) {
		String companyId = node.get("companyId").get("$oid").asText();
		ObjectNode company = companies.get(companyId);
		node.set("location", company.get("location"));
		node.set("name", company.get("name"));
		JsonNode contractIdNode = node.get("contractId");
		if (contractIdNode != null) {
			String contractId =  contractIdNode.get("$oid").asText();
			ObjectNode contract = contracts.get(contractId);
			if(contract != null) {
				node.set("description", contract.get("description"));
				node.set("price", contract.get("price"));
			} else {
				node.remove("contractId");
			}
		}
		ArrayNode subs = (ArrayNode) node.get("simple_contracts");
		if(subs != null) {
			for (JsonNode childNode : subs) {
				fillFields((ObjectNode) childNode, companies, contracts);
			}
		}
	}

	public void mergeContracts(ObjectNode simpleContractsNode) {
		Map<String, ObjectNode> newSubs = new HashMap<>();
		ArrayNode subs = (ArrayNode) simpleContractsNode.get("simple_contracts");

		if(subs != null) {
			for (JsonNode node : subs) {
				ObjectNode child = (ObjectNode) node;
				String companyId = child.get("companyId").get("$oid").asText();
				if (newSubs.get(companyId) != null) {
					ObjectNode newChild = newSubs.get(companyId);
					Double price = child.get("price") != null ? child.get("price").asDouble() : 0.0;
					newChild.put("priceSum", newChild.get("priceSum").asDouble() + price);
					ArrayNode descriptionArray = (ArrayNode) newChild.get("descriptionArray");
					if (child.get("description") != null) {
						descriptionArray.add(child.get("description"));
					}
					newChild.set("descriptionArray", descriptionArray);
					newSubs.put(companyId, newChild);
				} else {
					Double price = child.get("price") != null ? child.get("price").asDouble() : 0.0;
					child.put("priceSum", price);
					ArrayNode descriptionArray = objectMapper.createArrayNode();
					if (child.get("description") != null) {
						descriptionArray.add(child.get("description"));
					}
					child.set("descriptionArray", descriptionArray);
					newSubs.put(companyId, child);
				}

				child.remove("contractId");
				child.remove("description");
				child.remove("price");
				mergeContracts(child);
			}
		}
		if (simpleContractsNode.get("descriptionArray") == null) {
			simpleContractsNode.set("descriptionArray", objectMapper.createArrayNode());
		}
		if (simpleContractsNode.get("priceSum") == null) {
			simpleContractsNode.put("priceSum", 0.0);
		}

		ArrayNode newSubsArray = objectMapper.createArrayNode();
		newSubsArray.addAll(newSubs.values());

		simpleContractsNode.remove("companyId");
		simpleContractsNode.set("simple_contracts", newSubsArray);
	}

	public CompanyConnectionDto getComnpanyConnectionDTOWithFields(ObjectNode node, Map<String, ObjectNode> companies, Map<String, ObjectNode> contracts) {
		CompanyConnectionDto companyConnectionDto = new CompanyConnectionDto();
		try {
			ArrayNode coordinatesNode = ((ArrayNode) node.get("location").get("coordinates"));
			GpsCoordinate coordinate = new GpsCoordinate(
					coordinatesNode.get(0).asText(),
					coordinatesNode.get(1).asText());
			companyConnectionDto.setLocation(coordinate);
			companyConnectionDto.setName(node.get("name").asText());

			ContractDTO contractDTO = new ContractDTO();
			ArrayNode descriptionsNode = (ArrayNode) node.get("descriptionArray");
			List<String> descriptions = new ArrayList<>();
			for (JsonNode description : descriptionsNode) {
				descriptions.add((description.asText()));
			}
			contractDTO.setDescription(descriptions.toArray(new String[0]));
			contractDTO.setPrice(node.get("priceSum").asDouble());
			companyConnectionDto.setContract(contractDTO);

			ArrayNode simpleContracts = (ArrayNode) node.get("simple_contracts");
			if (simpleContracts != null && !simpleContracts.isEmpty()) {
				List<CompanyConnectionDto> sub = new ArrayList<>();
				for (JsonNode simpleContract : simpleContracts) {
					sub.add(getComnpanyConnectionDTOWithFields((ObjectNode) simpleContract, companies, contracts));
				}
				companyConnectionDto.setSub(sub);
			}
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

	public List<String> getContractsWithoutProject(String startDate, String endDate) {
		return contractRepository.getContractsWithoutProjects(startDate, endDate);
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
