# Task 1: TCPAsk

Overview

The first programming assignment is to implement a TCP client, called TCPAsk. TCPAsk operates in a straight-forward manner:

Open a TCP connection to a server at a given host address and port number.
Take any data that the server sends, and and print the data.
TCPAsk takes an optional string as parameter. This string is sent as data to the server when the TCP connection is opened, followed by a newline character (linefeed '\n').
For example, assume we want to contact the "daytime" server at "time.nist.gov". The Daytime protocol is a standardised protocol for asking a Daytime server about the current date and time. Daytime runs at TCP port 13 by default. So we could use our program in the following way:
``` 
$ java TCPAsk time.nist.gov 13
time.nist.gov:13 says:

58128 18-01-10 23:18:34 00 0 0  40.2 UTC(NIST) * 
```
The following example uses another Internet protocol, the "whois" protocol, which is used to make queries about resources on the Internet. Here we use the third optional argument to TCPAsk to pass a string to the whois server to ask for information about the domain name "kth.se".
```
$ java TCPAsk whois.iis.se 43 kth.se
whois.iis.se:43 says:
...
```

(The output is quite lengthy, so try this for yourself and check the output!)

In the task, you will learn how to:

- create TCP sockets, and use them to send and receive data;
- design the client side of client-server communication; and
- deal with errors that can happen during the communication.

# Task
You will be provided with the source code for TCPAsk. But the program does not do much. It reads and parses its arguments, calls the method TCPClient.askServer, and prints out the result. The specification of TCPClient.askServer is as follows:
```
public static String askServer(String hostname, int port, String ToServer) throws  IOException
public static String askServer(String hostname, int port) throws  IOException
```
Your job is to implement the TCPClient class.

# Run
```
$ unzip task1.zip
$ cd task1
$ javac TCPAsk.java
$ java TCPAsk time.nist.gov 13
$ java TCPAsk whois.iis.se 43
$ java TCPAsk whois.internic.net
$ java TCPAsk java.lab.ssvl.kth.se 7
```
