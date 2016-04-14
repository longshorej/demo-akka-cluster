# demo-akka-cluster #

Simple demo for Akka Cluster.

## Usage with Seed Nodes in standalone Mode ##

- Run the application:
  - `reStart --- -Dakka.remote.netty.tcp.hostname=127.0.0.1 -Dakka.remote.netty.tcp.port=2551 -Ddemo.http.port=8001 -Dlog-file=demo-akka-cluster-1.log -Dakka.cluster.seed-nodes.0=akka.tcp://demo-system@127.0.0.1:2551`
  - `reStart --- -Dakka.remote.netty.tcp.hostname=127.0.0.1 -Dakka.remote.netty.tcp.port=2552 -Ddemo.http.port=8002 -Dlog-file=demo-akka-cluster-2.log -Dakka.cluster.seed-nodes.0=akka.tcp://demo-system@127.0.0.1:2551`
- Get the member nodes: `curl 127.0.0.1:8001/member-nodes`

## Usage with Seed Nodes in Docker ##

On core-01/172.17.8.101:

```
docker run \
  --detach \
  --name demo \
  --publish 2552:2552 \
  --publish 8000:8000 \
  hseeberger/demo-akka-cluster:0.4.3 \
  -Dakka.remote.netty.tcp.hostname=172.17.8.101 \
  -Dakka.cluster.seed-nodes.0=akka.tcp://demo-system@172.17.8.101:2552
```

On core-02/172.17.8.102:

```
docker run \
  --detach \
  --name demo \
  --publish 2552:2552 \
  --publish 8000:8000 \
  hseeberger/demo-akka-cluster:0.4.3 \
  -Dakka.remote.netty.tcp.hostname=172.17.8.102 \
  -Dakka.cluster.seed-nodes.0=akka.tcp://demo-system@172.17.8.101:2552
```

## Usage with ConductR ##

- Start etcd: `docker run -d -p 2379:2379 --name etcd quay.io/coreos/etcd:v2.3.1 -advertise-client-urls http://192.168.99.100:2379 -listen-client-urls http://0.0.0.0:2379 -debug`
- Pass the JVM arguments for the etcd host and others to the application:
  - `reStart --- -Dakka.remote.netty.tcp.hostname=127.0.0.1 -Dakka.remote.netty.tcp.port=2551 -Ddemo.http.port=8001 -Dlog-file=demo-akka-cluster-1.log -Dconstructr.coordination.host=192.168.99.100`
  - `reStart --- -Dakka.remote.netty.tcp.hostname=127.0.0.1 -Dakka.remote.netty.tcp.port=2552 -Ddemo.http.port=8002 -Dlog-file=demo-akka-cluster-2.log -Dconstructr.akka.coordination.host=192.168.99.100`
- Get the member nodes: `curl 127.0.0.1:8001/member-nodes`
- Peek inside ConstructR: `curl 192.168.99.100:2379/v2/keys/constructr/akka/demo-system/nodes`

## Contribution policy ##

Contributions via GitHub pull requests are gladly accepted from their original author. Along with any pull requests, please state that the contribution is your original work and that you license the work to the project under the project's open source license. Whether or not you state this explicitly, by submitting any copyrighted material via pull request, email, or other means you agree to license the material under the project's open source license and warrant that you have the legal authority to do so.

## License ##

This code is open source software licensed under the [Apache 2.0 License](http://www.apache.org/licenses/LICENSE-2.0.html).
