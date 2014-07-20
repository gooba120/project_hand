import java.net.Socket;
import java.util.Scanner;


public class HandMotion {
	
	private static final String QUERY1 = "Which finger would you like to move?\n\n"
			+ "1. Thumb [t]\n2. Pointer[p]\n3. Middle[m]\n4. Ring[r]\n5. Pinky[q]\n6. "
			+ "Exit[e]\n";
	private static final String QUERY2 = "\nWhat angle? (0~180): ";
	private static final String QUERY3 = "\nMake another commmand? [y/n?]: ";
	private static final String ERROR = "Sorry, I don't recognize this command, "
			+ "please try again.";
	private static final String ERROR2 = "Sorry, not a valid angle value! Please "
			+ "try again.";
	
	private static final char openCommands[] = {'A', 'B', 'C', 'D', 'E'};
	private static final char closeCommands[] = {'a', 'b', 'c', 'd', 'e'};
	private static final char stopCommands[] = {'1', '2', '3', '4', '5'};
	
	private static final char clientCommands[] = {'t', 'p', 'm', 'r', 'q', 'e'};
	
	public static void testArduinoCom() throws Exception {
        ArduinoUSBCom socket = new ArduinoUSBCom();
        Scanner input = new Scanner(System.in);
        char in1 = 0, in2 = 0, in3 = 0;
        
        if ( socket.initialize() ) {
        	do {
	        	System.out.println(QUERY1);
	        	System.out.print("Answer: ");
	        	in1 = input.next(".").charAt(0);
	        	
	        	if(in1 == 'e')
	        		break;
	        	else if(isValidCommand(in1) == false) {
	        		System.out.println(ERROR);
	        		break;
	        	}
	        	
	        	System.out.print(QUERY2);
	        	in2 = (char)input.nextInt();
	        	System.out.println();
	        	
	        	if(in2 < 0 && in2 > 180) {
	        		System.out.println(ERROR2);
	        		break;
	        	}
	        	
//	        	socket.sendData(processCommands(in1, in2));
	        	socket.sendData(String.valueOf(in1));
	        	socket.sendData(String.valueOf(in2));
	        	
	        	try { Thread.sleep(1500); } catch (InterruptedException ie) {}
	        	
	        	System.out.print(QUERY3);
	        	in3 = input.next(".").charAt(0);
	        	System.out.println();
        	}
        	while(in3 == 'y');
        	
        	System.out.println("Thank you!");
        	socket.close();
        }

        // Wait 5 seconds then shutdown
        try { Thread.sleep(1000); } catch (InterruptedException ie) {}
    }
	
	private static boolean isValidCommand(char command) {
		boolean isValid = false;
		
		for(char c : clientCommands) {
			if(command == c) {
				isValid = true;
				break;
			}
		}
		
		return isValid;
	}
	
	private static String processCommands(char finger, char openOrClose) {
		char command = 0;
		
		if(finger == clientCommands[0]) {
			if(openOrClose == 'o')
				command = openCommands[0];
			else
				command = closeCommands[0];
		}
		else if(finger == clientCommands[1]) {
			if(openOrClose == 'o')
				command = openCommands[1];
			else
				command = closeCommands[1];
		}
		else if(finger == clientCommands[2]) {
			if(openOrClose == 'o')
				command = openCommands[2];
			else
				command = closeCommands[2];
		}
		else if(finger == clientCommands[3]) {
			if(openOrClose == 'o')
				command = openCommands[3];
			else
				command = closeCommands[3];
		}
		else if(finger == clientCommands[4]) {
			if(openOrClose == 'o')
				command = openCommands[4];
			else
				command = closeCommands[4];
		}
		
		return String.valueOf(command);
	}
}