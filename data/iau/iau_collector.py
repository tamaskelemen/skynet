#This script intends to collect all of the active individual members from International Astronomical Union (iau.org)
import urllib.request, urllib.error, urllib.parse
from bs4 import BeautifulSoup
import csv
import time

def parseUrlContent(url):
    try:
        response = urllib.request.urlopen(url)
        webContent = response.read()
        return webContent
    except urllib.error.HTTPError as e: 
        print("HTTPError:" + format(e.code))

        return False





baseUrl = 'https://www.iau.org/administration/membership/individual/'

result = []

for x in range(100, 18000):
    if x%1000 == 0: #THIS IS IMPORTANT after 1000 request, need to sleep to not time out. 
        print("sleeping for 60 seconds.......")
        time.sleep(60)

    fullUrl = baseUrl + str(x) + "/"
    
    print("Trying: " + str(fullUrl))

    webContent = parseUrlContent(fullUrl) 

    if not webContent:
        continue

    soup = BeautifulSoup(webContent, 'lxml')

    divTag = soup.find('div', class_='profile_box')
    
    if not divTag:
        print(divTag)
        continue

    name = divTag.find('h3').text

    name = name.strip()
    print(name)

    collection = []

    firstPtag = divTag.find('p');


    splitted = firstPtag.get_text(separator='<br/>')

    splitted = splitted.split('<br/>')



    result.append([name, splitted])
    
    print("next: " + str(x+1))
    print("");


with open('iau_members.csv', 'w', newline='') as file:
    writer = csv.writer(file)
    writer.writerows(result)

#print(result)
