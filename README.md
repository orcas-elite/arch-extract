# arch-extract
Extraction of architectural model from Jaeger traces

## What does it do?
The program retrieves traces from jaeger and processes them to build an architecture model which can be used as input for the [Microservice Resilience Simulator](https://github.com/orcas-elite/resilience-simulator).

## How to use it
The program expects the jaeger-client to run on *localhost:16686* and retrieves the necessary data automatically from jaeger upon execution.
The created architecture model is saved as *architecture_model.json* in the project directory.

To save the data that is retrieved from jaeger add the program argument **-b**. The data is then stored in the *example* directory.
Careful, the content of this directory will get replaced every time that the program is executed with this argument.

It is also possible to build an architecture model from locally saved traces without running jaeger on your machine.
For this add the **-l** argument before execution. The program will read the files that are saved in the *example* directory.

# arch-to-obs

## What does it do?
The tool takes an architectural model as input and transforms it into the observations that can be used for computations on the [Orcas Decision Engine](https://github.com/orcas-elite/bayespy).
If a nextExperiment file from the decision engine is provided as input as well, the tool returns all operations of the system that meet the requirements of the next experiment.

## How to use it
To transform an architectural model, the path to the model file must be added to the program arguments.
to return the operations of the next experiment, the path to the respective architectural file of the system and to the nextExperiment file must be added to the program arguments.
