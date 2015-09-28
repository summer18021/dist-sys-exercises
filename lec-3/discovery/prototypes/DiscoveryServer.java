package discoveryServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.invoke.MethodHandles.Lookup;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;

import javax.swing.LookAndFeel;

public class DiscoveryServer {
	public static Hashtable<String, String> ConvAndIp = new Hashtable<String, String>();

	public static void process(Socket clientSocket) throws IOException {
		// open up IO streams
		BufferedReader in = new BufferedReader(new InputStreamReader(
				clientSocket.getInputStream()));
		PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

		/* Write a welcome message to the client */
		out.println("**************************************************************************");
		out.println("Welcome to the Discovery server!");
		out.println("Lookup Format:(lookup unit1 unit2)");
		out.println("Add Format:(add unit1 unit2 xxx.xxx.xxx.xxx yyyy)");
		out.println("Remove Format:(remove unit1 unit2 xxx.xxx.xxx.xxx yyyy)");
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
		// --TODO:
		String choice[] = userInput.split(" ");
		try {
			switch (choice[0]) {
			case "lookup":
				out.println(lookup(choice[1] + " " + choice[2]));
				break;
			case "add":
				out.println(add(choice[1] + " " + choice[2], choice[3] + " "
						+ choice[4]));
				break;
			case "remove":
				out.println(remove(choice[1] + " " + choice[2], choice[3] + " "
						+ choice[4]));
				break;
			default:
				out.println("**************************************************************************");
				out.println("Invalid Input");
				out.println("**************************************************************************");
				out.println("Lookup Format:(lookup unit1 unit2");
				out.println("Add Format:(add unit1 unit2 xxx.xxx.xxx.xxx yyyy");
				out.println("Remove Format:(remove unit1 unit2 xxx.xxx.xxx.xxx yyyy");
				out.println("**************************************************************************");
				break;
			}
		} catch (Exception e) {
			out.println("**************************************************************************");
			out.println("Invalid Input");
			out.println("**************************************************************************");
			out.println("Lookup Format:(lookup unit1 unit2");
			out.println("Add Format:(add unit1 unit2 xxx.xxx.xxx.xxx yyyy");
			out.println("Remove Format:(remove unit1 unit2 xxx.xxx.xxx.xxx yyyy");
			out.println("**************************************************************************");
		}

		// close IO streams, then socket
		out.close();
		in.close();
		clientSocket.close();
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		// check if argument length is invalid
		// if(args.length != 1) {
		// System.err.println("Usage: java ConvServer port");
		// }
		// create socket
		// int port = Integer.parseInt(args[0]);
		int port = 1111;
		ServerSocket serverSocket = new ServerSocket(port);
		System.err.println("Started server on port " + port);

		// wait for connections, and process
		try {
			while (true) {
				// a "blocking" call which waits until a connection is requested
				Socket clientSocket = serverSocket.accept();
				System.err.println("\nAccepted connection from client");
				process(clientSocket);
			}

		} catch (IOException e) {
			System.err.println("Connection Error");
		}
		System.exit(0);
	}

	private static String lookup(String conv) {
		// TODO Auto-generated method stub
		String conversion[] = conv.split(" ");
		String conv1 = conversion[1] + " " + conversion[0];
		//converse "m cm" == "cm m"
		if (ConvAndIp.get(conv) != null)
			return ConvAndIp.get(conv);
		else if (ConvAndIp.get(conv1) != null)
			return ConvAndIp.get(conv1);
		else
			return null;
	}

	public static String add(String conversion, String address) {
		if (lookup(conversion) != null) {
			String addr = lookup(conversion) + address+",";
			ConvAndIp.remove(conversion);
			//converse "m cm" == "cm m"
			String conv[] = conversion.split(" ");
			String conv1 = conv[1] + " " + conv[0];
			ConvAndIp.remove(conv1);
			ConvAndIp.put(conversion, addr);
		} else
			ConvAndIp.put(conversion, address+",");

		return "Servers Updated!";
	}

	public static String remove(String str1,String str2) {
		String addr[] = lookup(str1).split(str2+",");
		
		ConvAndIp.remove(str1);
		//converse "m cm" == "cm m"
		String conv[] = str1.split(" ");
		String conv1 = conv[1] + " " + conv[0];
		ConvAndIp.remove(conv1);
		
		ConvAndIp.put(str1, addr[0]+addr[1]);
		return "Servers Updated!";
	}

}
