#!/usr/bin/env python

#******************************************************************************
#
#  CS 6421 - Simple Conversation
#  Execution:    python convServer.py portnum
#  Team member:  Yuan Gao, Bei Xia
#  Conversion:   Pounds <---> Ounces
#  Default Port: 1111
#******************************************************************************

import socket
import sys

## Function to process requests
def process(conn):
    conn.send("**************************************************************************\n")
    conn.send("Welcome to the Pounds (lbs) <---> Ounces (oz) Python-based conversion server!\n")
    conn.send("Conversion: <input unit> <output unit> <input amount>(lbs oz XX)or(oz lbs XX)\n")
    conn.send("**************************************************************************\n")
    # read userInput from client
    userInput = conn.recv(BUFFER_SIZE)
    if not userInput:
        print "Error reading message"
        sys.exit(1)

    print "Received message: ", userInput
    # TODO: add convertion function here, reply = func(userInput)
    try:
	#split the string, input, output, amount
    	strinput = userInput.split(" ")
	#pounds converse to ounces
	if strinput[0]==("lbs") and strinput[1]==("oz"):
		conn.send(str(float(strinput[2])*16)+"\n")
	#ounces converse to pounds
	elif strinput[0]==("oz") and strinput[1]==("lbs"):
	        conn.send(str(float(strinput[2])/16)+"\n")
	else:
	        conn.send("**************************************************************************\n")
        	conn.send("Invalid Input\n")
	        conn.send("Conversion: <input unit> <output unit> <input amount>(lbs oz XX)or(oz lbs XX)\n")
        	conn.send("**************************************************************************\n")
    except:	           
  	conn.send("**************************************************************************\n")          
        conn.send("Invalid Input\n")                  
  	conn.send("Conversion: <input unit> <output unit> <input amount>(lbs oz XX)or(oz lbs XX)\n")
    	conn.send("**************************************************************************\n")
    conn.close()
### Main code run when program is started
BUFFER_SIZE = 1024
interface = ""

# if input arguments are wrong, print out usage
#if len(sys.argv) != 2:
#    print >> sys.stderr, "usage: python {0} portnum\n".format(sys.argv[0])
#    sys.exit(1)

#portnum = int(sys.argv[1])
portnum = 1111
# create socket
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind((interface, portnum))
s.listen(5)
print 'Started Server on port 1111'
# accept connection and print out info of client
while True:
	conn, addr = s.accept()
	print 'Accepted connection from client', addr
	process(conn)	
s.close()
