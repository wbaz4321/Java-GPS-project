package gps;
import java.util.Scanner;

public class GPS {

	public static String dealTime(int s) 
	{
		String result = "";
		if(s < 10) 
		{
			result = "0"+s;
		}
		else 
		{
			result = Integer.toString(s);
		}
		return result;
	}
	
	public static void main(String[] args) {		
		Scanner in = new Scanner(System.in);
		// initializing
		String str_id = "";  // store id of a gps statement
		boolean isGRMC = false;
		boolean isLocated = false; 
		String verify = ""; // store the mains statement of one gps
		int key = ' ';
		int value = 0; // store the validation value as the baseline for comparison
		
		String str_time = ""; // store the time of a gps statement
		int hour = 0;
		int mintue = 0;
		int second = 0;
		String str_hour = "";
		String str_mintue = "";
		String str_second = "";
		String str_final = "";
			
		MAIN_LOOP:
		while(true) 
		{
			String gps = in.nextLine();
			if(gps.equals("END"))
				break MAIN_LOOP;
			if(gps.equals("")) 
				continue MAIN_LOOP;
			// get gps ID
			str_id = gps.split(",")[0];						
			isGRMC = str_id.equals("$GPRMC");
			
			// get gps status
			isLocated = gps.split(",")[2].equals("A");

			if(isGRMC && isLocated) 
			{
				// get validation value
				value = Integer.parseInt(gps.substring(gps.indexOf("*")+1, gps.length()), 16);
				// get GPRMC main statement
				verify = gps.substring(gps.indexOf("$")+1, gps.indexOf("*"));	
				key = verify.charAt(0);
				for(int i=1; i<verify.length(); i++) 
				{
					key = key ^ verify.charAt(i);
				};
				key = key % 65536;
								
				// verify this gps, if true, this gps is valid
				if(key == value) 
				{
					str_time = gps.substring(gps.indexOf(",")+1, gps.indexOf("."));
					hour = Integer.parseInt(str_time.substring(0, 2));
					mintue = Integer.parseInt(str_time.substring(2, 4));
					second = Integer.parseInt(str_time.substring(4, 6));
					if(hour >= 16) 
						hour = (hour + 8) - 24;
					else 
						hour = hour + 8;
					str_hour = GPS.dealTime(hour);
					str_mintue = dealTime(mintue);
					str_second = dealTime(second);
					str_final = str_hour+":"+str_mintue+":"+str_second;
				}
				else 
				{
					continue MAIN_LOOP;
				}
			}
			else 
			{
				continue MAIN_LOOP;			
			}
		}		
		System.out.println(str_final);	
		in.close();
	}
}
