from pymongo import MongoClient
import csv
from pprint import pprint

client = MongoClient('localhost:27017')

db=client.admin

csvfile = open('../data/usa_astroclubs/usa_astroclubs.csv', 'r', encoding='utf_8')

reader = csv.DictReader( csvfile )

for each in reader:
    row = {}
    row['location'] = {
        "type": "Point",
        "coordinates": [each['lat'], each['long']]
    }
    row['name'] = each['name']
    row['address'] = each['address']
    row['note'] = each['note']

    # pprint(each['long'])

    db.usa_astroclubs.insert(row)
