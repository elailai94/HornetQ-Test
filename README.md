# HornetQ Test
### About
This repository contains a program to measure the performance and scalability of [HornetQ](http://hornetq.jboss.org/). The program is written entirely in Java. HornetQ is an open-source project to build a multi-protocol, embeddable, very high performance, clustered, asynchronous messaging system. 

### Setup
In a new terminal window, download the HornetQ binaries and install the packages by running:
```Bash
./setup.sh
```

### Execution
In the same terminal window, start the HornetQ standalone server by running:
```Bash
cd bin
./run.sh
```
In a new terminal window, start the consumer by running:
```Bash
mvn -DskipTests -Pconsumer package
```
In a new terminal window, start the producer by running:
```Bash
mvn -DskipTests -Pproducer package
```

### Configuration
The server address, server port, number of messages and message size can be configured by modifying lines **52-59** and **94-99** in the **pom.xml** file respectively.

### Results Plotting
A utility script 

### Testbed
Each compute cluster is an identical configuration of the following components:

- 1x Supermicro SSG-6047R-E1R36L Large compute node consisting of:
  - 2x Intel E5-2630v2 CPU
  - 256GB RAM
  - 14x 2TB 7200RPM SAS2 hard drives (LSI HBA-connected)
  - 1x Intel S3700 400GB SATA3 SSD
  - 1x Intel P3700 400GB PCIe NVMe solid-state storage device
  - 4x Intel i350 gigabit Ethernet ports
  - 1x Mellanox 40GbE QSFP port
- 15x Supermicro SYS-6017R-TDF compute nodes. Each consisting of:
  - 2x Intel E5-2620v2 CPU
  - 64 GB RAM
  - 3x 1TB SATA3 hard drives
  - 1x Intel S3700 200GB SATA3 SSD
  - 2x Intel i350 gigabit Ethernet ports
  - 1x Mellanox 10GbE SFP port
- 1x Mellanox SX1012 10/40 GbE 12-port cluster switch

### To Dos
- Single producer/consumer on a single node with 1 million messages, 4kB each

### License
* This repository is licensed under the [MIT License](https://github.com/elailai94/hornetq-test/blob/master/LICENSE.md).
