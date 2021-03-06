/******************************************************************************
 *
 *  CS 6421 - Simple Conversation
 *  Compilation:  javac ConvServer.java
 *  Execution:    java ConvServer port
 *  Team Member:  Yuan Gao, Bei Xia
 *  Conversion:   Dollars <---> Ounces of bananas
 *  Default Port: 2222
 *  % java ConvServer portnum
 ******************************************************************************/

import java.net.Socket;
import java.net.ServerSocket;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ConvServer {

    public static void process (Socket clientSocket) throws IOException {
        // open up IO streams
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        /* Write a welcome message to the client */
	out.println("**************************************************************************");
	out.println("Welcome to the Dollars ($) <---> Ounces (oz) Java-based conversion server!");
	out.println("Conversion: <input unit> <output unit> <input amount>($ oz XX)or(oz $ XX)");
	out.println("**************************************************************************");
        /* read and print the client's request */
        // readLine() blocks until the server receives a new line from client
        String userInput;
        if ((userInput = in.readLine()) == null) {
            System.out.println("Error reading message");
            out.close();
            in.close();
            clientSocket.close();
        }

        System.out.println("Received message: " + userInput);
        //--TODO: add your converting functions here, msg = func(userInput);
	try{
	// split the string, input, output, amount
	String str[] = userInput.split(" ");
	//dollars converse to ounces
	if(str[0].equals("$")&&str[1].equals("oz"))
		out.println(Float.parseFloat(str[2])*20);
	//ounces converse to dollars
	else if(str[0].equals("oz")&&str[1].equals("$"))
		out.println(Float.parseFloat(str[2])/20);
        else{
		out.println("**************************************************************************");
		out.println("Invalid Input");
		out.println("Conversion: <input unit> <output unit> <input amount>($ oz XX)or(oz $ XX)");
		out.println("**************************************************************************");
	}}
	catch(Exception e){
		out.println("**************************************************************************");
		out.println("Invalid Input");
		out.println("Conversion: <input unit> <output unit> <input amount>($ oz XX)or(oz $ XX)");
		out.println("**************************************************************************");
	}
	// close IO streams, then socket
        out.close();
        in.close();
        clientSocket.close();
    }

    public static void main(String[] args) throws Exception {

        //check if argument length is invalid
        //if(args.length != 1) {
        //    System.err.println("Usage: java ConvServer port");
        //}
        // create socket
        //int port = Integer.parseInt(args[0]);
        int port = 2222;
	ServerSocket serverSocket = new ServerSocket(port);
        System.err.println("Started server on port " + port);

        // wait for connections, and process
        try {
            while(true) {
                // a "blocking" call which waits until a connection is requested
                Socket clientSocket = serverSocket.accept();
                System.err.println("\nAccepted connection from client");
                process(clientSocket);
            }

        }catch (IOException e) {
            System.err.println("Connection Error");
        }
        System.exit(0);
    }
}
