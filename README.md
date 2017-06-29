# HornetQ-Tests
### About
This repository contains a collection of scripts to test the performance and scalability of HornetQ. They are written entirely in Java.

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

### Setup
In one terminal window, download the HornetQ binaries and install packages by running:
```Bash
./setup.sh
```
### Execution
In one terminal window, start the HornetQ standalone server by running:
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
- Single producer/consumer on a single node with 1 million messages, 4kB each
