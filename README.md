# arch-extract
Extraction of architectural model from Jaeger traces

## What does it do?
The program retrieves traces from jaeger and processes them to build an architecture model which can be used as input for the [Microservice Resilience Simulator](https://github.com/orcas-elite/resilience-simulator).

## How to use it?
The program expects the jaeger-client to run on *localhost:16686* and retrieves the necessary data automatically from jaeger upon execution.
The created architecutre model is saved as *architecture_model.json* in the project directory.

To save the data that is retrieved from jaeger add the program argument **-b**. The data is then stored in the *example* directory.
Careful, the content of this directory will get replaced every time that the program is executed with this argument.

It is also possible to build an architecture model from locally saved traces without running jaeger on your machine.
For this add the **-l** argument before execution. The program will read the files that are saved in the *example* directory.
