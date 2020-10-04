import csv
import json
from pymongo import MongoClient
from pprint import pprint
import datetime
client = MongoClient('localhost:27017')

db=client.admin


def formatDate(date):
    parts = date.split(' ')
    firstPart = parts[0].split('-')
    secondPart = parts[1].split(':')

    result = datetime.datetime(int(firstPart[0]), int(firstPart[1]), int(firstPart[2]), int(secondPart[0]), int(secondPart[1]), int(secondPart[2]))
    return result


for year in range(1990, 2021):
    #MAGNITUDE FIRST
    print("opening " + str(year) + " magnitude...")
    csvfile = open('../data/meteor_obs/Magnitude-IMO-VMDB-Year-' + str(year) + '.csv', 'r', encoding='utf-8-sig')
    reader = csv.DictReader(csvfile)

    header = {
        'magnitude_id': 'Magnitude ID',
        'user_id': 'User ID',
        'obs_session_id': 'Obs Session ID',
        'shower': 'Shower',
        'mag_n6': 'Mag N6',
        'mag_n5': 'Mag N5',
        'mag_n4': 'Mag N4',
        'mag_n3': 'Mag N3',
        'mag_n2': 'Mag N2',
        'mag_n1': 'Mag N1',
        'mag_0': 'Mag 0',
        'mag_1': 'Mag 1',
        'mag_2': 'Mag 2',
        'mag_3': 'Mag 3',
        'mag_4': 'Mag 4',
        'mag_5': 'Mag 5',
        'mag_6': 'Mag 6',
        'mag_7': 'Mag 7',
          }

    for each in reader:

        row = {}

        row['start_date'] = formatDate(each['Start Date'])
        row['end_date'] = formatDate(each['End Date'])
        for key, value in header.items():
            row[key] = each[value]

    db.meteor_magnitude.insert(row)

    print("done")

    #RATE data
    print("opening " + str(year) + " rate...")
    csvfile = open('../data/meteor_obs/Rate-IMO-VMDB-Year-' + str(year) + '.csv', 'r', encoding='utf-8-sig')
    reader = csv.DictReader(csvfile)

    header = {
        'rate_id': 'Rate ID',
        'user_id': 'User ID',
        'obs_session_id': 'Obs Session ID',
        'ra': 'Ra',
        'decl': 'Decl',
        'teff': 'Teff',
        'f': 'F',
        'lm': 'Lm',
        'shower': 'Shower',
        'method': 'Method',
        'number': 'Number'
    }

    for each in reader:
        row= {}

        row['start_date'] = formatDate(each['Start Date'])
        row['end_date'] = formatDate(each['End Date'])

        for key, value in header.items():
            row[key] = each[value]

    db.meteor_rate.insert(row)


    #SESSION
    print("opening " + str(year) + " session...")
    csvfile = open('../data/meteor_obs/Session-IMO-VMDB-Year-' + str(year) + '.csv', 'r', encoding='utf-8-sig')
    reader = csv.DictReader(csvfile)

    header = {
        'session_id': 'Session ID',
        'observer_id': 'Observer ID',
        'submitter_id': 'Submitter ID',
        'actual_observer_name': 'Actual Observer Name',
        'submitted_by': 'Submitted by',
        'city': 'City',
        'country': 'Country',
        'elevation': 'Elevation'
    }

    for each in reader:
        row = {}
        row['location'] = {
            "type": "Point",
            "coordinates": [each['Latitude'], each['Longitude']]
        }
        row['start_date'] = formatDate(each['Start Date'])

        for key, value in header.items():
            row[key] = each[value]

    db.meteor_session.insert(row)

    print("done")


