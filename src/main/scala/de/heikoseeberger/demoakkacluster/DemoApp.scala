/*
 * Copyright 2015 Heiko Seeberger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.heikoseeberger.demoakkacluster

import akka.actor.{ Address, ActorRef, ActorSystem }
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives
import akka.pattern.ask
import akka.stream.ActorMaterializer
import akka.util.Timeout
import scala.concurrent.{ ExecutionContext, Await }
import scala.concurrent.duration.{ Duration, MILLISECONDS }
import scala.util.{ Failure, Success }

object DemoApp {

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("demo-system")
    import system.dispatcher
    implicit val mat = ActorMaterializer()

    val config = system.settings.config.getConfig("demo")
    val httpInterface = config.getString("http.interface")
    val httpPort = config.getInt("http.port")
    val clusterViewTimeout = Timeout(Duration(config.getDuration("cluster-view-timeout").toMillis, MILLISECONDS))

    val clusterView = system.actorOf(ClusterView.props, ClusterView.Name)
    Http()
      .bindAndHandle(route(clusterView, clusterViewTimeout), httpInterface, httpPort)
      .onComplete {
        case Success(_) => system.log.info(s"Listening on $httpInterface:$httpPort")
        case Failure(t) => system.log.error(t, s"Could not bind to $httpInterface:$httpPort")
      }

    Await.ready(system.whenTerminated, Duration.Inf)
  }

  def route(clusterView: ActorRef, clusterViewTimeout: Timeout)(implicit ec: ExecutionContext) = {
    import Directives._
    path("member-nodes") {
      get {
        implicit val timeout = clusterViewTimeout
        onSuccess((clusterView ? ClusterView.GetMemberNodes).mapTo[Set[Address]]) {
          case addresses => complete(addresses.mkString(" "))
        }
      }
    }
  }
}
