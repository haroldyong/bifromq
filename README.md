# BifroMQ
test

[![GitHub Release](https://img.shields.io/github/release/bifromqio/bifromq?color=brightgreen&label=Release)](https://github.com/bifromqio/bifromq/releases)
[![Coverage Status](https://img.shields.io/coveralls/github/bifromqio/bifromq/main?label=Coverage)](https://coveralls.io/github/bifromqio/bifromq?branch=main)
[![Docker Pulls](https://img.shields.io/docker/pulls/bifromq/bifromq?label=Docker%20Pulls)](https://hub.docker.com/r/bifromq/bifromq)

English | [中文简体](./README.zh_Hans.md)

---

BifroMQ is a high-performance, distributed MQTT broker implementation that seamlessly integrates native multi-tenancy
support. It is designed to support building large-scale IoT device connections and messaging systems, Currently, it
serves as the foundational technology for Baidu [IoTCore](https://cloud.baidu.com/product/iot.html), a public serverless
cloud service.

## Features

* Full support for MQTT 3.1, 3.1.1 and 5.0 features over TCP, TLS, WS, WSS
* Native support for multi-tenancy resource sharing and workload isolation
* Built-in distributed storage engine optimized for MQTT workloads, with no third-party middleware dependencies.
* Extension mechanism for supporting:
  * Authentication/Authorization
  * Tenant-level Runtime Setting
  * Tenant-level Resource Throttling
  * Event
  * System/Tenant-level Monitoring

## Documentation

You can access the [documentation](https://bifromq.io/docs/get_started/intro/) on the
official [website](https://bifromq.io).
Additionally, contributions to the documentation are welcome in the
GitHub [repository](https://github.com/bifromqio/bifromq-docs).

## Getting Started

### Docker

```
docker run -d -m <MEM_LIMIT> -e MEM_LIMIT='<MEM_LIMIT_IN_BYTES>' --name bifromq -p 1883:1883 bifromq/bifromq:latest
```

Substitute `<MEM_LIMIT>` and `<MEM_LIMIT_IN_BYTES>` with the actual memory allocation for the Docker process, for
example, `2G` for `<MEM_LIMIT>` and `2147483648` for `<MEM_LIMIT_IN_BYTES>`. If not specified, BifroMQ defaults to using
the hosting server's physical memory for determining JVM parameters. This can result in the Docker process being
terminated by the host's Out-of-Memory (OOM) Killer. Refer to [here](https://bifromq.io/docs/installation/docker/)
for more information.

### Build from source

#### Prerequisites

* JDK 17+
* Maven 3.5.0+

#### Get source & Build

Clone the repository to your local workspace:

```
cd <YOUR_WORKSPACE>
git clone https://github.com/baidu/bifromq bifromq
```

Navigate to the project root folder and execute the following commands to build the entire project:

```
cd bifromq
mvn wrapper:wrapper
./mvnw -U clean package
```

The build output consists of several archive files located under `/build/build-bifromq-starters/target/`

* `bifromq-<VERSION>-windows-standalone.zip`
* `bifromq-<VERSION>-standalone.tar.gz`

#### Running the tests

Execute the following command in the project root folder to run all test cases, including unit tests and integration
tests.
Note: The tests may take some time to finish

```
mvn test
```

### Quick Start

To quickly set up a BifroMQ server, extract the `bifromq-xxx-standalone.tar.gz` file into a directory. You will see the
following directory structure:

```
|- bin
|- conf
|- lib
|- plugins
```

To start or stop the server, execute the respective command in the `bin` directory:

- **To start the server**, run:
  ```
  ./standalone.sh start // This starts the server process in the background.
  ```

- **To stop the server**, run:
  ```
  ./standalone.sh stop
  ```

The configuration file, `standalone.yml`, can be found in the `conf` directory. The settings within this file are named
in a self-explanatory manner. By default, the standalone server stores persistent data in the `data` directory.

### Cluster Deployment

BifroMQ has two cluster deployment modes: `Standard Cluster`, `Independent-Workload Cluster`

#### Standard Cluster

The standard cluster deployment mode is suitable for small to medium-sized production environments that require
reliability and scalability. It comprises several fully functional standalone nodes working together as a logical MQTT
broker
instance, ensuring high availability. You can also scale up the concurrent mqtt connection workload by adding more
nodes, while some types of messaging related workload are not horizontal scalable in this mode.

#### Independent Workload Cluster

The Independent Workload Cluster deployment mode is designed for building large-scale, multi-tenant serverless clusters.
In this mode, the cluster consists of several specialized sub-clusters, each focusing on a particular 'independent type'
of workload. These sub-clusters work together coherently to form a logical MQTT broker instance. This is the most
complex deployment mode and requires additional non-open-sourced building blocks. Feel free to contact us for commercial
support.

## Discussion

Join our Discord or WeChat group if you are interested in our work.

### Discord

<a href="https://discord.gg/Pfs3QRadRB"><img src="https://img.shields.io/discord/1115542029531885599?logo=discord&logoColor=white" alt="BifroMQ Discord server" /></a>

### WeChat group

[Email](mailto:hello@bifromq.io) us your WeChat ID, along with more information on why BifroMQ has caught your
attention (we'd love to hear about it), and we will invite you to join our group as soon as possible.
