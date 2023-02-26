import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class HTTPAsk {
   
	public static void main( String[] args) throws NumberFormatException, IOException {
       
    	int BUFFERSIZE = 1024 * 6 ;
    	
    	String HTTP200 = "HTTP/1.1 200 OK\r\n\r\n";
    	String HTTP404 = "HTTP/1.1 404 Not Found\r\n\r\n"; 
    	String HTTP400 = "HTTP/1.1 400 Bad Request\r\n\r\n";
        byte[] STATUS_OK =               HTTP200.getBytes();
	    byte[] STATUS_BAD_REQUEST =      HTTP400.getBytes();
	    byte[] STATUS_NOT_FOUND =        HTTP404.getBytes();

        
        ServerSocket HTTP = new ServerSocket(Integer.parseInt(args[0]));
      

    	   while(true) {
         

                byte[] fromClient = new byte[BUFFERSIZE];
               
                String input;
                String hea;
                String par;
                String protocol;
                String query; 
                
                Socket clientSocket = HTTP.accept();
                StringBuilder ask = new StringBuilder();
                
             do {
                 int   fromClientLength = clientSocket.getInputStream().read(fromClient);   
                 
                   if (fromClientLength != -1) {
                	 
                	   ask.append(new String(fromClient, 0, fromClientLength));  
                 }  
                   else {
                    	   clientSocket.close();
                 }
              }
                
                while(!(ask.substring(ask.length() - 2).equals("\n\n") ||
                		ask.substring(ask.length() - 4).equals("\r\n\r\n")));

                  input = ask.toString(); 
 
                  OutputStream output = clientSocket.getOutputStream();
                         
          try {
                 
        	      hea = input.split(" ", 2)[0];
                  protocol = input.split(" HTTP/", 2)[1].split("\r\n", 2)[0];
                  par = input.split(" ", 3)[1];
        	      query = par.split("\\?", 2)[0]; 
              }
         
          catch(ArrayIndexOutOfBoundsException ex) {
                 
        	  output.write(STATUS_BAD_REQUEST);
              clientSocket.close();
                   continue;
              }       
   

        if(protocol.equals("1.1") && hea.equals("GET") && query.equals("/ask")) {
                
            	  String host;
                  String port; 
                  String string;

            try {
                    host = par.split("hostname=", 2)[1].split("&", 2)[0];
                    port = par.split("port=", 2)[1].split("&", 2)[0];
                
                  try {
                          string = par.split("string=", 2)[1] + "\n";
                   }
           
                  catch(ArrayIndexOutOfBoundsException ex) {
                       
                	      string = null;
                  }    
              }
               
            catch(ArrayIndexOutOfBoundsException ex) {
                	 
            	output.write(STATUS_NOT_FOUND);
                clientSocket.close();
                      continue;
                  }
           
            try {
                    String response;
                    if (string == null) {
                    	
                    	  response = TCPClient.askServer(host, Integer.parseInt(port));
                  }
                    else {
                    	 
                    	  response = TCPClient.askServer(host, Integer.parseInt(port), string);
                  }
                     
                      output.write(STATUS_OK);
                      output.write(response.getBytes());
                  }
                 
             catch(IOException ex) {}
     }
              
      else {
             output.write(STATUS_NOT_FOUND);
            }
            
             clientSocket.close();
           }  
    	}
  }


 class TCPClient {

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
      
//      if(fromServerLength > 0) {
//      String response = new String(fromServerBuff, 0,fromServerLength);

 
      clientSocket.close();
      return response.toString();

  
//    else {
//        
//    	  clientSocket.close();
//			throw new ConnectException("There was no response");
//} 	

    }

   
    public static String askServer(String hostname, int port) throws  IOException {

      byte [] fromServerBuff = new byte[BUFFERSIZE];
      Socket clientSocket = new Socket(hostname,port);
      
      clientSocket.setSoTimeout(Timeout);
      
 //     int fromServerLength = clientSocket.getInputStream().read(fromServerBuff);
 
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
//     
//     String response = new String(fromServerBuff, 0,fromServerLength);

      clientSocket.close();

      return response.toString();
  

//    else {
//       
//    	clientSocket.close();
//			throw new ConnectException("There was no response");
//} 	
    }

  }




    	
    	
