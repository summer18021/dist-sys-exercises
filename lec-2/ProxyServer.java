/******************************************************************************
 *
 *  CS 6421 - Simple Conversation
 *  Compilation:  javac ConvServer.java
 *  Execution:    java ConvServer port
 *  Team Member:  Yuan Gao, Bei Xia
 *  Conversion:   pounds(lbs) <-> ounces of bananas(oz) <-> dollars($) <-> Japanese Yen(y)
 *  Default Port: 5555
 *  % java ConvServer portnum
 ******************************************************************************/

import java.net.Socket;
import java.net.ServerSocket;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ProxyServer {

	public static void process(Socket clientSocket) throws IOException {
		// open up IO streams
		BufferedReader in = new BufferedReader(new InputStreamReader(
				clientSocket.getInputStream()));
		PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

		/* Write a welcome message to the client */
		out.println("**************************************************************************");
		out.println("Welcome to the pounds(lbs) <---> Japanese Yen(y) Java-based conversion server!");
		out.println("Conversion: <input unit> <output unit> <input amount>(lbs y XX)or(y lbs XX)");
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
		// --TODO: add your converting functions here, msg = func(userInput);
		try {
			// split the string, input, output, amount
			String str[] = userInput.split(" ");
			// Pounds converse to Japanese yen
			if (str[0].equals("lbs") && str[1].equals("y")) {
				// Conversion: pounds(lbs) <-> ounces of bananas(oz) <->
				// dollars($) <-> Japanese Yen(y)
				// lbs oz XX
				out.println();
				out.println("lbs oz " + str[2]);
				float result1 = PoundsToOunces(Float.parseFloat(str[2]),
						"lbsTooz");
				// result1
				out.println(result1);
				out.println();
				// oz $ result1
				out.println("oz $ " + result1);
				float result2 = OuncesToDollars(result1, "ozTo$");
				// result2
				out.println(result2);
				out.println();
				// $ y result2
				out.println("$ y " + result2);
				float result3 = DollarsToYen(result2, "$Toy");
				// result3
				out.println(result3);
				out.println();
				// Final result
				out.println("lbs " + str[2] + " = " + result3 + " y ");
			}
			// Japanese yen converse to pounds
			else if (str[0].equals("y") && str[1].equals("lbs")) {
				// Conversion: pounds(lbs) <-> ounces of bananas(oz) <->
				// dollars($) <-> Japanese Yen(y)
				// lbs oz XX
				out.println();
				out.println("y $ " + str[2]);
				float result1 = DollarsToYen(Float.parseFloat(str[2]), "yTo$");
				// result1
				out.println(result1);
				out.println();
				// oz $ result1
				out.println("$ oz " + result1);
				float result2 = OuncesToDollars(result1, "$Tooz");
				// result2
				out.println(result2);
				out.println();
				// $ y result2
				out.println("oz lbs" + result2);
				float result3 = PoundsToOunces(result2, "ozTolbs");
				// result3
				out.println(result3);
				out.println();
				// Final result
				out.println("y " + str[2] + " = " + result3 + " lbs ");
			} else {
				out.println("**************************************************************************");
				out.println("Invalid Input");
				out.println("Conversion: <input unit> <output unit> <input amount>(lbs y XX)or(y lbs XX)");
				out.println("**************************************************************************");
			}
		} catch (Exception e) {
			out.println("**************************************************************************");
			out.println("Invalid Input");
			out.println("Conversion: <input unit> <output unit> <input amount>(lbs y XX)or(y lbs XX)");
			out.println("**************************************************************************");
		}
		// close IO streams, then socket
		out.close();
		in.close();
		clientSocket.close();
	}

	private static float PoundsToOunces(float f, String string)
			throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
		Socket socket = new Socket("127.0.0.1", 1111);
		InputStreamReader streamReader = new InputStreamReader(
				socket.getInputStream());
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader reader = new BufferedReader(streamReader);
		switch (string) {
		case "lbsTooz":
			out.println("lbs oz " + f);
			for (int i = 0; i <= 3; i++) {
				System.out.println((reader.readLine()));
			}
			return Float.parseFloat(reader.readLine());
		case "ozTolbs":
			out.println("oz lbs " + f);
			for (int i = 0; i <= 3; i++) {
				System.out.println((reader.readLine()));
			}
			return Float.parseFloat(reader.readLine());
		}
		out.println();
		socket.close();
		return 0;
	}

	private static float OuncesToDollars(float result1, String string)
			throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
		Socket socket = new Socket("127.0.0.1", 2222);
		InputStreamReader streamReader = new InputStreamReader(
				socket.getInputStream());
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader reader = new BufferedReader(streamReader);
		switch (string) {
		case "ozTo$":
			out.println("oz $ " + result1);
			for (int i = 0; i <= 3; i++) {
				System.out.println((reader.readLine()));
			}
			return Float.parseFloat(reader.readLine());
		case "$Tooz":
			out.println("$ oz " + result1);
			for (int i = 0; i <= 3; i++) {
				System.out.println((reader.readLine()));
			}
			return Float.parseFloat(reader.readLine());
		}
		out.println();
		socket.close();
		return 0;
	}

	private static float DollarsToYen(float result2, String string)
			throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
		Socket socket = new Socket("127.0.0.1", 3333);
		InputStreamReader streamReader = new InputStreamReader(
				socket.getInputStream());
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader reader = new BufferedReader(streamReader);
		switch (string) {
		case "$Toy":
			out.println("$ y " + result2);
			for (int i = 0; i <= 3; i++) {
				System.out.println((reader.readLine()));
			}
			return Float.parseFloat(reader.readLine());
		case "yTo$":
			out.println("y $ " + result2);
			for (int i = 0; i <= 3; i++) {
				System.out.println((reader.readLine()));
			}
			return Float.parseFloat(reader.readLine());
		}
		out.println();
		socket.close();
		return 0;
	}

	public static void main(String[] args) throws Exception {

		// check if argument length is invalid
		// if(args.length != 1) {
		// System.err.println("Usage: java ConvServer port");
		// }
		// create socket
		// int port = Integer.parseInt(args[0]);
		int port = 5555;
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
}

