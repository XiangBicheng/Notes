# Chapter 4 Problems and answers 

1. We noted that network layer functionality can be broadly divided into data plane functionality
   and control plane functionality. What are the main functions of the data plane? Of the control
   plane?

   **answer: data plane的功能就是转发，就是指对于单个的路由器而言，如何把从某一端口进入的数据报，在通过查表之后，从指定端口发出去，完成"match-plus-action"这个流程。control plane的功能就是网络层面的路由，让包成功地从源主机发送到目的主机，细节上它依赖于一个个路由器层面的转发，而路由器的转发依赖于查表，control plane就是通过对路由器的每张表的配置，实现整体的路由的。**

2. We said that a network layer’s service model “defines the characteristics of end-to-end
   transport of packets between sending and receiving hosts.” What is the service model of the
   Internet’s network layer? What guarantees are made by the Internet’s service model regarding
   the host-to-host delivery of datagrams?

   **answer：网络层的服务模型，从单个包的角度而言，包括确保交付、确保在有限延迟内交付；从流的角度而言，包括按序包交付、最小带宽保证、包间最大抖动限制。Internet服务模型没有提供以上任意一种服务。**

3. Discuss why each input port in a high-speed router stores a shadow copy of the forwarding
   table.

   **answer：通过在每个输入端口保留一份转发表的浅拷贝，转发查找操作就能够在输入端口本地完成，而不需要调用路由器的中央处理器。这种分布式的方案，避免了单点造成的处理瓶颈。**

4. Three types of switching fabrics are discussed in Section 4.2 . List and briefly describe
   each type. Which, if any, can send multiple packets across the fabric in parallel?

   **answer：Switching via memory; switching via a bus; switching via an interconnection network. An interconnection network can forward packets in parallel as long as all the packets are being forwarded to different output ports.** 

5. Describe how packet loss can occur at input ports. Describe how packet loss at input ports
   can be eliminated (without using infinite buffers).

   **answer：当包到达的速率超过了switch fabric的传输速率时，来不及传输的包就会在输入端口那里排队，如果这种速率不匹配的情况持续下去，队列就会越来越长，超过缓冲区的容量，一些包就会被丢弃。要避免这种情况，需要switch fabric的传输速率大于等于所有输入端口链路速率之和。**

6. Describe how packet loss can occur at output ports. Can this loss be prevented by
   increasing the switch fabric speed?

   **answer：当switch fabric向某个输出端口交付包的速率大于该输出端口向外发送的速率时，来不及传输的包就会在输出端口处排队。如果这种速率不匹配的情况持续下去，队列就会越来越长，超过缓冲区的容量，一些包就会被丢弃。这种情况无法通过增加switch fabric的速率来避免。**

