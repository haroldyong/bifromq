/*
 * Copyright (c) 2023. The BifroMQ Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *    http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package com.baidu.bifromq.basecluster;

import com.baidu.bifromq.basecluster.memberlist.HostAddressResolver;
import com.baidu.bifromq.basecluster.memberlist.IHostAddressResolver;
import com.baidu.bifromq.basecluster.memberlist.agent.IAgent;
import com.baidu.bifromq.basecluster.membership.proto.HostEndpoint;
import com.baidu.bifromq.basecluster.transport.ITransport;
import com.baidu.bifromq.basecluster.transport.TCPTransport;
import com.baidu.bifromq.basecluster.transport.Transport;
import io.reactivex.rxjava3.core.Observable;
import java.net.InetSocketAddress;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface IAgentHost {
    static IAgentHost newInstance(AgentHostOptions options) {
        ITransport transport = Transport.builder()
            .env(options.env())
            .bindAddr(new InetSocketAddress(options.addr(), options.port()))
            .serverSslContext(options.serverSslContext())
            .clientSslContext(options.clientSslContext())
            .options(new Transport.TransportOptions()
                .mtu(options.udpPacketLimit())
                .tcpTransportOptions(new TCPTransport.TCPTransportOptions()
                    .maxChannelsPerHost(options.maxChannelsPerHost())
                    .idleTimeoutInSec(options.idleTimeoutInSec())
                    .connTimeoutInMS(options.connTimeoutInMS())))
            .build();
        IHostAddressResolver resolver =
            new HostAddressResolver(options.addressExpiryInterval(), options.addressRefreshInterval());
        return new AgentHost(transport, resolver, options);
    }

    HostEndpoint local();

    /**
     * Join the cluster as a running node by communicating with some existing running node of the cluster as the seeds
     *
     * @param seeds
     * @return
     */
    CompletableFuture<Void> join(Set<InetSocketAddress> seeds);

    /**
     * An observable of the live membership of host cluster
     *
     * @return
     */
    Observable<Set<HostEndpoint>> cluster();

    /**
     * Host an agent locally
     *
     * @param agentId
     * @return
     */
    IAgent host(String agentId);

    /**
     * unhost the agent
     *
     * @param agentId
     */
    CompletableFuture<Void> stopHosting(String agentId);

    /**
     * An observable of agent host membership
     *
     * @return
     */
    Observable<Set<HostEndpoint>> membership();

    /**
     * Start the host
     */
    void start();

    /**
     * Shutdown the host
     */
    void shutdown();
}
