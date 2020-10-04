import json
import googlemaps
import os


gmaps = googlemaps.Client(key='')
curr_folder = os.getcwd()
out_folder = os.path.join(os.getcwd(), 'geocoded_data')
cache = {}


def recursive_geocode(sub):
    geocode_result = gmaps.geocode(sub["name"])
    if len(geocode_result) < 1:
        return
    lat = geocode_result[0]['geometry']['location']['lat']
    lng = geocode_result[0]['geometry']['location']['lng']
    sub["location"] = {
                "type": "Point",
                "coordinates": [row[6], row[7]]
                }
    if len(sub[sub]) == 0:
        return
    recursive_geocode(sub)

for filename in os.listdir(curr_folder):
    result = []
    with open(os.path.join(curr_folder, filename), encoding='utf-8') as file:
        if '.json' in filename:
             data = json.load(file)
             recursive_geocode(data[0])
             with open(os.path.join(out_folder, filename), 'w') as outfile:
                 json.dump(data, outfile)
