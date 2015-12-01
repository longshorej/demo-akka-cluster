# demo-akka-cluster #

Simple demo for Akka Cluster.

## Usage ##

- Start etcd, e.g. via `docker run -d -p 2379:2379 --name etcd quay.io/coreos/etcd:v2.2.1 -advertise-client-urls http://192.168.99.100:2379 -listen-client-urls http://0.0.0.0:2379`
- Pass the JVM argument for the etcd host to the application, e.g. `-Dconstructr.akka.coordination.host=192.168.99.100`

## Contribution policy ##

Contributions via GitHub pull requests are gladly accepted from their original author. Along with any pull requests, please state that the contribution is your original work and that you license the work to the project under the project's open source license. Whether or not you state this explicitly, by submitting any copyrighted material via pull request, email, or other means you agree to license the material under the project's open source license and warrant that you have the legal authority to do so.

## License ##

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
