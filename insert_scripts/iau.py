from pymongo import MongoClient
import csv
from pprint import pprint

client = MongoClient('localhost:27017')

db=client.admin

csvfile = open('../data/iau/members_filtered.csv', 'r')

reader = csv.DictReader( csvfile )

for each in reader:
    row = {}
    row['location'] = {
        "type": "Point",
        "coordinates": [each['lat'], each['long']]
    }
    row['name'] = each['name']
    row['address'] = each['address']

    # pprint(each['long'])

    db.iau_members.insert(row)
