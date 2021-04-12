# Construction Site Simulator

This application is a comprehensive simulation for bulldozer drives.
It allows bulldozer operators to send commands to operate
a bulldozer, and simulate clearing of particular area.

## How to Build

The project is configured via Maven. To build it from the project
checkout directory, type:

> mvn clean install

## How to Run
To run the simulator, one input argument must be provided. 
This argument is a file-system path to a file with the map of 
an area to run the simulation on:

> java -jar construction-site-simulator-[version]-spring-boot.jar [file path]

An example map is provided with the build of this program. 
The run the recent build with an example map, 
one may run within the checkout directory of the project:

> java -jar target/construction-site-simulation-1.0-SNAPSHOT-spring-boot.jar src/test/resources/map.txt

When the program starts, the map is printed, and the user is asked
to input commands to operate the bulldozer. At the end of the simulation
the bill is printed. 

# Program Design

The program combines stateless services, stateful domain objects,
and functional programming style actions.

Services are stored in the sub-package ```services``` and 
they trigger actions from the sub-package ```actions```.
The result of execution is modification of stateful objects 
from the sub-package ```domain```.

The program runs in a loop until it is executed. This loop
is executed from the main class ```App``` in the root package.

All the functionality is tested via `JUnit`.  

## Discussion
The project does not use any IoC framework such as Spring, 
but it is designed to use it easily - i.e. the services
are designed with the dependency injection principle. 

The project shows possible
combination of stateless services and functional programming in 
Java. It has some limitations caused by not sufficient 
dynamicity of Java lambdas. For instance, the number of lambda arguments 
is not flexible, which means that some actions from 
```ActionsFactory``` must define the input argument, 
even if it is not used (see, the ```x``` argument, which is ignored),
because other actions do have an input argument. 

The enum ```CommandType``` breaks the inversion-of-control
principle as it directly injects dependencies from ```ActionsFactory```.
It is because enums are "static classes", and while they can be 
modified at run-time, there is no elegant way to inject their
dependencies as part of their creation. For this reason ```ActionsFactory``` contains 
static methods, which are directly called from the enum creation. 
An alternative could be a standard class 
instantiated in more instances in replacement of enum items. 