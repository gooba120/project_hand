import com.leapmotion.leap.Bone;
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.HandList;
import com.leapmotion.leap.Vector;


public class LeapDetector {
//	Vector hand_direction;
	
	Hand getRightHand(HandList hands){
		for(int i = 0; i < hands.count(); i++){
			if(hands.get(i).isRight())
				return hands.get(i);
		}
		return null;
	}
	Boolean is_thumb(Finger f){
		return (f.bone(Bone.Type.TYPE_METACARPAL)).length() == 0 ;
	}
	float finger_length(Finger f){
		if(!is_thumb(f)){
			return f.bone(Bone.Type.TYPE_PROXIMAL).length() + 
					f.bone(Bone.Type.TYPE_INTERMEDIATE).length() +
					f.bone(Bone.Type.TYPE_DISTAL).length();
		} else{
			return f.bone(Bone.Type.TYPE_INTERMEDIATE).length() + 
					f.bone(Bone.Type.TYPE_DISTAL).length();
		}
	}
	static final double RANGE = 70;
	float get_bent(Finger f){
		if(!is_thumb(f)){
			Vector ActualFinger = f.bone(Bone.Type.TYPE_PROXIMAL).prevJoint().minus(f.tipPosition()).normalized();
			return (int)((RANGE / 2.0) * Math.abs(1 + ActualFinger.dot(f.bone(Bone.Type.TYPE_METACARPAL).direction())));
		} else{
			Vector ActualFinger = f.bone(Bone.Type.TYPE_PROXIMAL).nextJoint().minus(f.tipPosition()).normalized();
			return (int)((RANGE / 2.0) * Math.abs(1 + ActualFinger.dot(f.bone(Bone.Type.TYPE_PROXIMAL).direction())));
		}
	}
	
	
	void Test(){
		Controller con = new Controller();
		System.out.println("Waiting for connection \n");
		while(!con.isConnected());
		System.out.println("Connected");
		
		ArduinoUSBCom socket = new ArduinoUSBCom();
		socket.initialize();
		
		Frame temp_frame;
		Hand right_hand;
		while(true){
			temp_frame = con.frame();
			if(temp_frame.hands().count() == 2){
				break;
			}
			right_hand = getRightHand(temp_frame.hands());
			if(right_hand != null){
				for(Finger finger: right_hand.fingers()){
					socket.sendData(String.valueOf((char)get_bent(finger)));
					//socket.sendData("");
					System.out.printf("%.2f ",get_bent(finger));
				}
				System.out.println();
			}
		}
		System.out.println("Dissconnecting");
		socket.close();
	}
	void TestNoUSB(){
		Controller con = new Controller();
		System.out.println("Waiting for connection \n");
		while(!con.isConnected());
		System.out.println("Connected");
		
		//ArduinoUSBCom socket = new ArduinoUSBCom();
		//socket.initialize();
		
		Frame temp_frame;
		Hand right_hand;
		while(true){
			temp_frame = con.frame();
			if(temp_frame.hands().count() == 2){
				break;
			}
			right_hand = getRightHand(temp_frame.hands());
			if(right_hand != null){
				for(Finger finger: right_hand.fingers()){
					//socket.sendData(String.valueOf((char)get_bent(finger)));
					//socket.sendData("");
					System.out.printf("%.2f ",get_bent(finger));
				}
				System.out.println();
			}
		}
		System.out.println("Dissconnecting");
		//socket.close();
	}

}
