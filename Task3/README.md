# Task 2: HTTPAsk Server

Overview
In this part, you will implement another web server – HTTPAsk. This is a web server that uses the client you did in Task 1. When you send an HTTP request to HTTPAsk, you provide a hostname, a port number, and optionally a string as parameters for the request.

When HTTPAsk receives the HTTP request, it will call the method TCPClient.askServer, (which you wrote for Task 1, remember?), and return the output as an HTTP response. This may seem confusing, but it is really very simple: instead of running TCPAsk from the command line, you will build a web server that runs TCPAsk for you, and presents the result as a web page (an HTTP response).

What you will learn here is to:

- Read and analyse an HTTP GET request, and extract a query string from it. 
- Launch an application from your server, where the application provides the data that the server returns in response to the HTTP get. 

# Task
Your job is to implement a class called HTTPAsk. It's "main" method implements the server. It takes one argument: the port number. So if you want your server to run on port 8888, you would start it in the following way:
```
$ java HTTPAsk 8888
```
The requirements for HTTPAsk are the same as for HTTPEcho in Task 2: it should run in an infinite loop, but does not need to be multithreaded.     
