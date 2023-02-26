import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class HTTPEcho {
    
	
	public static void main(String[] args) throws IOException {
       
		int TIMEOUT = 2000;
    	int BUFFERSIZE = 1024 * 6 ;
        String Httpheader = "HTTP/1.1 200 OK\r\n\r\n";
        
        
        ServerSocket HTTP = new ServerSocket(Integer.parseInt(args[0]));
      
     try
         {
           while(true)
         {

                byte[] fromClient = new byte[BUFFERSIZE];
               
                Socket clientSocket = HTTP.accept();
                clientSocket.setSoTimeout(TIMEOUT);
                
                InputStream input = clientSocket.getInputStream();
                OutputStream output = clientSocket.getOutputStream();

                try {

                    int c = 0;
                    int i= 0;
                   
                    while (true)
                    {

                   int   fS = input.read();
                       
                        fromClient[c] = (byte) fS;
                        c++;

                        if( fS == -1) {
                         
                            break;
                    }     
                        
                        if(fS == 10)
                        {
                        	
                            i++;
                        }
                        else  if( fS == 13) {i++;}
                      
                        else
                            {
                                i = 0;
                            }
                    }
                }
                
                catch (SocketTimeoutException e){
                   
                }

               
                StringBuilder result = new StringBuilder();
                
                String input1 = new String(fromClient, StandardCharsets.UTF_8).trim();
 
                result.append(Httpheader);
                result.append(input1);
                String Out = result.toString();
                
			    byte[] toClient = Out.getBytes(StandardCharsets.UTF_8);

                output.write(toClient);
                input.close();
                output.close();
                clientSocket.close();

            }
        }

        catch(UnknownHostException e)
        {
           
        }
    }
}