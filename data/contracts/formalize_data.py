import pandas
import csv
import os
import googlemaps

gmaps = googlemaps.Client(key='')
csv_genfolder = os.path.join(os.getcwd(), 'csv_gen')
outfolder = os.path.join(os.getcwd(), 'geocoded_data')
cache = {}
for filename in os.listdir(csv_genfolder):
    result = []
    if 'small_business' in filename:
        with open(os.path.join(csv_genfolder, filename)) as csvfile:
            readCSV = csv.reader(csvfile)
            for row in readCSV:
                try:
                    if row[0] not in cache.keys():
                        geocode_result = gmaps.geocode(row[0])
                        if len(geocode_result) < 1:
                            continue
                        lat = geocode_result[0]['geometry']['location']['lat']
                        lng = geocode_result[0]['geometry']['location']['lng']
                        cache[row[0]] = [lat, lng]
                    else:
                        lat = cache[row[0]][0]
                        lng = cache[row[0]][1]
                    if row[2] not in cache.keys():
                        geocode_result2 = gmaps.geocode(row[1])
                        if len(geocode_result2) < 1:
                            continue
                        lat2 = geocode_result2[0]['geometry']['location']['lat']
                        lng2 = geocode_result2[0]['geometry']['location']['lng']
                        cache[row[1]] = [lat2, lng2]
                    else:
                        lat2 = cache[row[1]][0]
                        lng2 = cache[row[1]][1]
                    result.append([row[0], row[1], row[2], row[3], row[4], row[5], row[6], lat, lng, lat2, lng2])
                except:
                    print('WTF')
    with open(os.path.join(outfolder, filename), 'w+', newline='') as csvfile:
        for res in result:
            csvfile.write(str(res[0]) + ',' + str(res[1]) + ',' + str(res[2]) + ',' + str(res[3])+ ',' + str(res[4])+ ',' + str(res[5])+ ',' + str(res[6])+ ',' + str(res[7])+ ',' + str(res[8])+ ',' + str(res[9]) + ',' + str(res[10]))
            csvfile.write('\n')
    csvfile.close()


        
