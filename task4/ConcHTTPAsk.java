import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;


public class ConcHTTPAsk {

	public static void main( String[] args) throws NumberFormatException, IOException {
	       
	    int Port;
	    if(args.length > 0){
	    	Port = Integer.parseInt(args[0]);
	  }
	    else { 
	    	Port = 8888;
	  }
		
	    try{
		    
	    	ServerSocket HTTP = new ServerSocket(Port);
	     
	        while(true){
	       
	           Socket clientSocket = HTTP.accept();	
	           (new Thread(new MyRunnable(clientSocket))).start();
	        }
	    } 
	    catch(IOException e){  
	    	System.err.println(e);
	    } 
   }
}
	   
    class MyRunnable implements Runnable {
	  
    	Socket clientSocket;

	    public MyRunnable(Socket clientSocket) {
	    	this.clientSocket = clientSocket;
	    }

    	 
	    public void run() {
	     
	        int BUFFERSIZE = 1024 * 6 ;
	    	
	    	String HTTP200 = "HTTP/1.1 200 OK\r\n\r\n";
	    	String HTTP404 = "HTTP/1.1 404 Not Found\r\n\r\n"; 
	    	String HTTP400 = "HTTP/1.1 400 Bad Request\r\n\r\n";
	        byte[] STATUS_OK =               HTTP200.getBytes();
		byte[] STATUS_BAD_REQUEST =      HTTP400.getBytes();
		byte[] STATUS_NOT_FOUND =        HTTP404.getBytes();
	 
	 	
	    	while(true) {
         
                byte[] fromClient = new byte[BUFFERSIZE];
               
                String input;
                String hea;
                String par;
                String protocol;
                String query; 
                
                StringBuilder ask = new StringBuilder();
        
	try {        
             do {
                 int fromClientLength = clientSocket.getInputStream().read(fromClient);   
                 
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
                
                  String Q = par.split("\\?", 2)[1];        	
            	  String host;
                  String port; 
                  String string;

            try {
                    host = Q.split("hostname=", 2)[1].split("&", 2)[0];
                    port = Q.split("port=", 2)[1].split("&", 2)[0];
                
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
	
	    	catch(IOException ioe) {
	            return;
           }
  
	}	
      }  		    	
}
    
    

    

