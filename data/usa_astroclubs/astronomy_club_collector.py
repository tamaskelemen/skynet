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





baseUrl = 'https://www.go-astronomy.com/astronomy-club.php?ID='

result = []

for x in range(1, 2000):
    if x%1000 == 0: time.sleep(60)

    fullUrl = baseUrl + str(x)
    
    print("Trying: " + str(fullUrl))

    webContent = parseUrlContent(fullUrl) 

    if not webContent:
        continue

    soup = BeautifulSoup(webContent, 'lxml')

    divTag = soup.find('div', class_='table-info')
    
    if not divTag:
        print(divTag)
        continue

    liContainer = divTag.find_all('li', class_='table-value')
    
    name = liContainer[0].text
    
    address = liContainer[1].text

    city = liContainer[2].text

    members = liContainer[-1].text

    result.append([name, city, address, members])

    print("next: " + str(x+1))
    print("");

with open('usa_astroclubs.csv', 'w', newline='') as file:
    writer = csv.writer(file)
    writer.writerows(result)

#print(result)
