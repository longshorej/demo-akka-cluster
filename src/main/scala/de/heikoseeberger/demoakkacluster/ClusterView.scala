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

import akka.actor.{ ActorLogging, Props, Address, Actor }
import akka.cluster.{ Cluster, ClusterEvent }

object ClusterView {

  case object GetMemberNodes

  final val Name = "cluster-view"

  def props: Props = Props(new ClusterView)
}

class ClusterView extends Actor with ActorLogging {
  import ClusterEvent._
  import ClusterView._

  private var members = Set.empty[Address]

  Cluster(context.system).subscribe(self, InitialStateAsEvents, classOf[MemberEvent])

  override def receive = {
    case GetMemberNodes =>
      sender() ! members

    case MemberJoined(member) =>
      log.info("Member joined: {}", member.address)
      members += member.address

    case MemberUp(member) =>
      log.info("Member up: {}", member.address)
      members += member.address

    case MemberRemoved(member, _) =>
      log.info("Member removed: {}", member.address)
      members -= member.address
  }
}
