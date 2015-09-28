1. Open DiscoveryServer(Default Port 1111)
  javac DiscoveryServer.java
  java DiscoveryServer

2. Open ProxyServer(Default Port 2222)
  javac ProxyServer.java
  java ProxyServer
  
3. Open ConvServer(Default Port 3333)
  javac ConvServer.java
  java ConvServer
  (When you open the ConvServer, the ConvServer will send "add" request to the DiscoveryServer.
  When you type "q" to quit the ConvServer, the ConvServer will send "remove" request to the DiscoveryServer.)
  
4. telnet 127.0.0.1 2222
  type "<input unit> <output unit>", ProxyServer will send "lookup" request to the DiscoveryServer,
  the DiscoveryServer will response "xxx,xxx,xxx,xxx yyyy" address to the ProxyServer.
