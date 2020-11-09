# skynet #

https://2020.spaceappschallenge.org/challenges/connect/space-exploration-your-backyard/teams/jagermilkyway/project
# The Challange ## 
The work of space exploration (and the societal benefits it brings) permeates every location and facet of society. Your challenge is to create a visual representation of the breadth of the space sector’s influence, showing the network of organizations and locations associated with the work of space exploration.

# Summary #
**We took the challenge literally - we show where the work is done. And it is everywhere. Business, professional, or amateur - all communities can contribute to the space-sector, this way it affects a large part of humanity. Companies and industries, professional scientists or amateur observers, NGO or local unions - this exploration tool allows you to discover how big, how connected, how enormous the space industry is, how it has improved during the years, and how it is all around You.**

## How We Addressed This Challenge ## 
We developed an exploration tool. Discover all the companies, universities or institutes that helps NASA, ESA, JAXA or other space agencies know more about the space and about Earth. With our application, you can learn more about:

- What the contracts the NASA has with other companies are about?
- Who helped to built the most ambitious space projects the mankind has ever done?
- Where does the professional astronomers work? Are they really just around the space centers?
- Where are the amateur astronomers? Where are the observers?
- Where are the closest astronomical unions to you, and how can you contact them?
- How they are distributed all around the globe?

There is no way at this moment to visually see through, and by that realize how the work made on space exploration requires, affects more and more of us on the globe. **This application capable to show the network and work provided by companies, institutions and the all of the individuals at the same time, on a scalable way.**

As the principle says: keep it simple! By wandering around the globe, you can simply:
- learn about the NASA or ESA or other space agency partners, how they help each other
- learn who have helped to build the specific instruments on a spacecraft, or who have participated in projects and from where
- see where the professionals work at

The Space-net application does not only focuses on the scientists and businesses. Amateurs, enthusiastic contributors are everywhere. The reports, observations of those people really can help effectively scientist's work. Being a single report of a meteor during an annual shower, or reports of dusty air or a cloud what the individual saw, **the scientists value amateur data a lot.** These data can be useful as control-observations - was the satellite data correctly processed, did the meteor radar catch all the meteors that has fallen, or they can be significant in many more different way. By placing these kind of reports on a map, you can find other people who are interested in the nature just as you, or you can find a nice spot by checking where are the most observations to your next adventure.

Finding these kind of data was a big challenge. This brought the idea itself, our goal:

**why is there no platform to collect these data? Why there is no public platform to collect all of these data, all of the amateur astronomical, weather, or any?**

Probably because it is a complicated task technically. Or as we experienced, there is no organized collection of these data internationally, so presenting historical data will be a really challenging problem. We should provide a simple, public platform where those who wants to help, can help. Other thing is that this way, the data would be organized, and easier to access for publicity.

Creating a common a platform for everyone, making it easy to watch how the space industry grows. It would change the way how the publicity thinks about space industry, so everyone would be involved in exploring the space. Everyone should be involved in exploring the Earth, and the nature.

**This would "underscore our responsibility to deal more kindly with one another, and to preserve and cherish the pale blue dot, the only home we've ever known"**

## How We Developed This Project ##
The challenge was big: collect as many data as we can, build a system what can quickly show all of it, and make it ready to have more and more data, have big traffic yet work flawlessly. That's for us.

(And not to mention, we care about technology, love space exploration, and even some of us does amateur astronomy in their free time.)

1. To handle big amount of data, we knew we need a database which is designed to that: that's why we choose mongoDB as our database engine.
2. We knew we will have to aggregate and filter and do some work on the data we have, so that's we choose Google Cloud Platform to host our application when we saw your offer.
3. The server is written in Java, using the Spring Boot framework, because it is easy to config, easy to add libraries, and easy to develop bigger size projects.
4. For the map we used NASA's World Wind 3D globe, as it is free to use, and capable to do exactly what we need here.
5. For creating frontend panels, we used React.js framework, because it is extremely easy to quickly develop apps, and it handles pretty well any external libraries we may need.
6. Collecting, forming, sorting, organizing, scraping data we used python as it is the best for automatizing these tasks.

We have had our problem, we could not achieve what we imagined at the beginning. Collecting all of the data, all in different format, encoded in different ways, some in pdf files, some in csv, some in plain tables on html pages, and many more. We have found all of the data we wanted for the demo, it took way more time to sanitizing all. Though we succeeded.

Another big issue was how to configure the cloud solution we choose in a way to make it capable a large amount of load. We had to keep in mind the prizes of course, and the usability.

We believe have forsee what the difficulities will be, what datastructures we need (graphs, trees, recursive function to walk through it), and many more, because we could solve them - yet there are always the unknown. Finding some datas (weather reports, like clouds, weather reports from observer.globe.gov) took us really long time, since it was not listed on the resources panel. We think that is an important part in our demo application.

Overall, against all the difficullities, and all the missing features, bugs on the live demo site (what you will might notice) we are satisfied with the result. We know what this application is capable of, how big amount of data it could handle, and how spectacular those data presented would look like.

## How We Used Space Agency Data in This Project ##
The significant part of our application builds on data provided on the Resources tab.

We have collected the NASA contracts with small businesses, and other, international contracts. These have almost the biggest impact on our work. Many data, many knowledge to learn by studying those in our app. Due to short time, we could only collect the same list from ESA, but no other agencies.

Not presented because of a bug which occured like 1 hour before due is the Space Projects. Our idea is that there is a space project, like JUNO, PLATO (from ESA), the MESSENGER spacecraft, or many more. Each of these spacecraft has many instruments on board, which was designed and built by several other institutions or companies. They assemble it in a HQ, but the parts come from different places, and these networks work together on a standalone mission. Hovering on the connection, we would display what instrument the institution has provided. All of these data was collected from NASA sites.

The observation data from observe.globe.gov was a fantastic surprise when we found it. It came really useful at representing the amateur's contribution to science. The same goes for the data from IMO.net.

We have wrote a scraper to collect all of the individual astronomers from International Astronomers Union. Because it is publicly available information, we thought we could have a nice representation where the professional astronomers work. You can easily compare to amateurs, how their presence overlap on a map.

Also we have collected from a public site all of the amateur astronomy clubs in the USA. Because of the short time, we could not find all the clubs in each country all over the world. These data could provide a nice information source to others.

We have also found a dataset, where we could find all the budget for space missions at NASA. With more time, presenting it on the map, at the projects, it would show pretty nicely how much money goes to one single mission - and how it is so small cost compared to the scientific value it brings.

Project Demo  
Slides:
https://drive.google.com/file/d/1oTdj5mxr-PuydGYKX1vCbEl23oK9an4A/view?usp=sharing

Project Code
https://github.com/tamaskelemen/skynet

# Data & Resources #
**NASA Small Business Innovative Research (SBIR)/Small Business Technology Transfer (STTR) Database**
https://sbir.nasa.gov/advanced_search

**List of NASA's domestic and international contracts:**
https://www.nasa.gov/partnerships/about.html

**List of ESA's contracts:**
https://www.esa.int/Applications/Observing_the_Earth/Copernicus/Space_Component_tenders_and_contracts

**NASA Acquisition Internet Service:**
https://prod.nais.nasa.gov/cgibin/nais/welcome.cgi

**GLOBE Clouds observations:**
https://observer.globe.gov/get-data/clouds-data

**GLOBE Dust Data:**
https://observer.globe.gov/get-data/dust-data

**IAU members:**
https://www.iau.org/administration/membership/individual/

**Meteor observations data:**
https://www.imo.net/

**USA Astronomical clubs:**
https://www.go-astronomy.com/astro-club-search.htm

**JUNO Institutional Partners:**
https://web.archive.org/web/20091115141317/http://juno.wisc.edu/index_partner.html

**Planetary exploration budget:**
https://www.planetary.org/space-policy/planetary-exploration-budget-dataset
