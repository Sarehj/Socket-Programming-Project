# Task 4: Concurrent HTTPAsk Server

Overview

In this task, you will take the HTTPAsk server you did in Task 3, and turn it into a concurrent server. The server you did for Task 3 deals with one client at a time (most likely). A concurrent server can handle multiple clients in parallel.

# Task
The description here is almost the same as for Task 3: You should implement a class called ConcHTTPAsk. It's "main" method implements the server. It takes one argument: the port number. So if you want your server to run on port 8888, you would start it in the following way:

``` 
$ java ConcHTTPAsk 8888
``` 
The difference between ConcHTTPAsk and HTTPAsk from Task 3 is that as soon as a client contacts your ConcHTTPAsk server, the client will be served immediately. It does not have to wait for ConcHTTPAsk to finish serving the current client.

# Run
``` 
$ unzip task4.zip
$ cd task4
$ javac ConcHTTPAsk.java
$ java ConcHTTPAsk <port number>
  ``` 
