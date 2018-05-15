# FlexTracks

#### Date completed: May 2016

An automated fuel-optimal air traffic management system for airport ATC (Air Traffic Control) is implemented. The system is a novel method for routing and management of aircraft within the airspace managed by the ATC (Air Traffic Control) of an airport. The present system relies on a static methodology that serves flights on a first come first serve basis and determines runway combination solely based on wind conditions. The proposed system will allocate the most fuel efficient combination of runways at the airport to specifically cater to the flights currently being served by the airport. The system examines various parameters such as current wind conditions, air time, taxi time, type of aircraft, etc. to determine the most efficient combination of runways in terms of overall fuel consumption.

It uses a combination of greedy and dynamic programming methodologies for optimizing routes for take-off and landing sequences at major airports resulting in potentially massive savings in fuel burn and air time of flights. It will use a dynamic programming methodology to determine most efficient combination of runways to cater to the current flights under management by the Air Traffic Control. Also, it will automatically design a schedule that will maintain the necessary separation between two aircraft so as to avoid wake turbulence vortices. We conducted a comparative study of fuel-burn when current procedures are followed vis-a-vis a simulation of operations using our system. Our findings conclusively demonstrated, with experimental results, the magnitude of the economic and environmental impact of the system.

Technologies used: Java, JavaFx, MySQL database

The output of this project displays a schedule showing flight details along with the allotted runway. The schedule updates after every slot of 10 minutes. This dynamic allocation of flights leads to massive fuel savings and reduces the ATC workload.
