# HornetQ-Tests
### About
This repository contains a collection of scripts to test the performance and scalability of HornetQ. They are written entirely in Java.

### Execution
In a terminal window, start the HornetQ standalone server by running:
```Bash
cd bin
./run.sh
```
In another terminal window, start the consumer by running:
```Bash
mvn -Pconsumer package
```
In another terminal window, start the producer by running:
```Bash
mvn -Pproducer package
```

### To Dos
- Single client with 4kB message
