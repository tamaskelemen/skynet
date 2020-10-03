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

mydoc = company_col.find({})

for x in mydoc:
  print(x) 
