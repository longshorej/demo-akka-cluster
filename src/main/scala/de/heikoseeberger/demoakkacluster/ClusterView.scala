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

import akka.actor.{ Props, Address, Actor }
import akka.cluster.{ Cluster, ClusterEvent }

object ClusterView {

  case object GetMemberNodes

  final val Name = "cluster-view"

  def props: Props = Props(new ClusterView)
}

class ClusterView extends Actor {
  import ClusterView._

  private var members = Set.empty[Address]

  Cluster(context.system).subscribe(self, ClusterEvent.InitialStateAsEvents, classOf[ClusterEvent.MemberEvent])

  override def receive = {
    case GetMemberNodes                        => sender() ! members
    case ClusterEvent.MemberUp(member)         => members += member.address
    case ClusterEvent.MemberRemoved(member, _) => members -= member.address
  }
}
