# Task 2: HTTPEcho server

Overview

In this part, you will implement a web server. It is a web server that does not do very much, but you will probably find it useful for the rest of this assignment. The server accepts an incoming TCP connection, reads data from it until the client closes the connection, and returns ("echoes") an HTTP response back with the data in it. 

This may seem a bit odd. But the idea is that if you use your web browser to navigate to this server, what you will see in your browser window is the complete HTTP request that your browser sent.

So, let's say that you are running this server on port 8888 on your computer. In the navigation field of your browser, type in the URL "http://localhost:8888". What you then should see in your browser is something like this:
``` 
GET / HTTP/1.1
Host: localhost:8888
Upgrade-Insecure-Requests: 1
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_2) AppleWebKit/604.4.7 (KHTML, like Gecko) Version/11.0.2 Safari/604.4.7
Accept-Language: en-us
Accept-Encoding: gzip, deflate
Connection: keep-alive
```
So this is what the browser sent to the server.

# Task
Your job is to implement a class called HTTPEcho. It's "main" method implements the server. It takes one argument: the port number. So if you want your server to run on port 8888, you would start it in the following way:
```
$ java HTTPEcho 8888
```
You will be provided with a file with a template for the HTTPEcho class and your task is to fill it with Java code.

Since HTTPEcho is a TCP server, you should implement it in the same way as other servers: it should run in an infinite loop, and when one client has been served, the server should be prepared to take care of the next. Your server does not need to serve more than one client at a time, though. It does not need to be multithreaded, in other words (that's for later).   
