package tcpclient;
import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class TCPClient {

    private static int BUFFERSIZE = 1024*5 ;
    private static int Timeout = 5000;

    public static String askServer(String hostname, int port, String ToServer) throws  IOException {

      if(ToServer == null){
       
    	  return askServer(hostname , port);
      }
      
     
      byte [] fromUserBuff = new byte[BUFFERSIZE];
      byte [] fromServerBuff = new byte[BUFFERSIZE];
      
      fromUserBuff = ToServer.getBytes(StandardCharsets.UTF_8);
     
     //Calls socket()and then connect() system calls to open
     //connection to server hostname and port number
      Socket clientSocket = new Socket(hostname,port);
     
     //if no response in 5 seconds, Timeout
      clientSocket.setSoTimeout(Timeout);
      
     //Send bytes on socket
      clientSocket.getOutputStream().write(fromUserBuff);
 

     //Receive bytes on socket
     //int fromServerLength = clientSocket.getInputStream().read(fromServerBuff);
      
     StringBuilder response = new StringBuilder ("");
     
    try {
    	int fromServerLength;
    	 while((fromServerLength = clientSocket.getInputStream().read(fromServerBuff)) != -1
    	            && response.length() < BUFFERSIZE* 200) {
    	 
    	 response.append( new String(fromServerBuff, 0,fromServerLength));
       }
     }
        catch (SocketTimeoutException e) {}    
      
     // if(fromServerLength > 0) {
     // String response = new String(fromServerBuff, 0,fromServerLength);

       clientSocket.close();
       return response.toString();

//    else {   
//    	  clientSocket.close();
//			throw new ConnectException("There was no response");
//} 	
    }

     public static String askServer(String hostname, int port) throws  IOException {

      byte [] fromServerBuff = new byte[BUFFERSIZE];
      Socket clientSocket = new Socket(hostname,port);
      
      clientSocket.setSoTimeout(Timeout);
      
     // int fromServerLength = clientSocket.getInputStream().read(fromServerBuff);
 
      StringBuilder response = new StringBuilder ("");
      try {
      	int fromServerLength;
      	 while((fromServerLength = clientSocket.getInputStream().read(fromServerBuff)) != -1
      	            && response.length() < BUFFERSIZE*200) {
      	 
      	 response.append( new String(fromServerBuff, 0,fromServerLength));
         }
       }
          catch (SocketTimeoutException e) {}  
      
//      if(fromServerLength > 0) {
//     String response = new String(fromServerBuff, 0,fromServerLength);

      clientSocket.close();
      return response.toString();

//    else {  
//    	clientSocket.close();
//	throw new ConnectException("There was no response");
//} 	
    }
  }

