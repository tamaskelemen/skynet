import pymongo
import csv
import json
import os
import random


data_csv = os.path.join(os.getcwd(), 'processed_data_1.csv')


myclient = pymongo.MongoClient("mongodb://localhost:27017/")
mydb = myclient["skynet_mongodb_1"]
company_col = mydb["company"]
contract_col = mydb["contract"]
simple_contracts = mydb["simple_contracts"]

company_id_map = {}

contracts = []

def filter_contracts(contract, id):
    return contract["companyId"] == id

with open(data_csv) as csvfile:
    readCSV = csv.reader(csvfile)
    for row in readCSV:
        if row[0] not in company_id_map.keys():
            company = {
            "name": row[0],
            "location":{
                "type": "Point",
                "coordinates": [row[6], row[7]]
                }
            }
            new_id = company_col.insert_one(company).inserted_id
            company_id_map[row[0]] = new_id
            
        if row[1] not in company_id_map.keys():
            company = {
            "name": row[1],
            "location":{
                "type": "Point",
                "coordinates": [row[8], row[9]]
                }
            }
            new_id = company_col.insert_one(company).inserted_id
            company_id_map[row[0]] = new_id

        if len(row) == 11:
                type = row[10]
        else:
                type = ""
            
        this_contract = {
            "description": row[2],
            "type": type,
            "startDate": row[3],
            "endDate": row[4],
            "price": row[5]
            }

        contract_id = contract_col.insert_one(this_contract).inserted_id
        matching_company = list(filter(lambda x: x["companyId"] == company_id_map[row[0]], contracts))
        if len(matching_company) == 0:
            contracts.append({
                "companyId": company_id_map[row[0]],
                "simple_contracts": [{
                    "companyId": row[1],
                    "contractId": contract_id
                    }
                    ]
                })
        else:
           contracts[matching_company[0]["simple_contracts"]].append({"contractId": contract_id, "companyId":row[1]})

print(contracts)
simple_contracts.insert_many(contracts)

