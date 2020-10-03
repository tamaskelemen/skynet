import csv
import json
from pymongo import MongoClient
from pprint import pprint
import datetime
client = MongoClient('localhost:27017')

db=client.admin

serverStatusResult=db.command("serverStatus")

csvfile = open('../data/2012-2019_GLOBE_dust_data_v1-0.csv', 'r')

reader = csv.DictReader( csvfile )

header = {"observation_number": "Observation Number", 'observation_elevation': 'Observation Elevation', 'fog': 'Fog', 'smoke': 'Smoke', 'haze': 'Haze', 'dust': 'Dust', 'sand': 'Sand'}

for each in reader:
    row={}
    row['location'] = {
        "type": "Point",
        "coordinates": [ each['Observation Latitude'], each['Observation Longitude' ] ]
    }
    date = each['Measurement Date (UTC)']
    time = each['Measurement Time (UTC)']

    date = date.split('/')
    time = time.split(':')

    row['observation_date'] = datetime.datetime(int("20" + date[2]), int(date[0]), int(date[1]), int(time[0]), int(time[1]), int(time[2]))

    for key, value in header.items():
        row[key] = each[value]
    # pprint(row)
    db.globe_dust.insert(row)

