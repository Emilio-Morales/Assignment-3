import java.util.*;

public class Problem2 
{
	static List<Integer> tempReadings;
	static List<Integer> lowestReadings;
	static List<Integer> highestReadings;
	
	public static void main(String[] args) throws InterruptedException 
	{
		ArrayList<Integer> temps = new ArrayList<Integer>();
		ArrayList<Integer> low = new ArrayList<Integer>();
		ArrayList<Integer> high = new ArrayList<Integer>();
		Sensor[] sensors = new Sensor[4];
		
		for (int i = 0; i < 5; i++)
			low.add(Integer.MAX_VALUE);
		
		for (int i = 0; i < 5; i++)
			high.add(Integer.MIN_VALUE);
				
		// Allows all sensors to add their readings
		tempReadings = Collections.synchronizedList(temps);
		lowestReadings = Collections.synchronizedList(low);
		highestReadings = Collections.synchronizedList(high);
		
		for (int i = 0; i < 4; i++)
		{
			Sensor temp = new Sensor(i);
			temp.start();
			sensors[i] = temp;
		}
		
		for (Sensor sensor: sensors)
		{
			sensor.join();
		}
		
		System.out.println("All readings:");
		System.out.println("--------------");
		printReadings();
		System.out.println("-----------------------------------------------------------");
		
		System.out.print("Top 5 Lowest Readings: ");
		for (int i: lowestReadings)
			System.out.print(i + " ");
		System.out.println("");
		
		System.out.print("Top 5 Highest Readings: ");
		for (int i: highestReadings)
			System.out.print(i + " ");
		
	}// END OF Main
	
	static void printReadings()
	{
		for (int i = 0; i < tempReadings.size(); i++)
		{
			if (i >= 3 && i % 3 == 0)
				System.out.println("");
			
			System.out.print(tempReadings.get(i) + " ");
		}
		
		System.out.println("");
	}
	// Stores the top 5 max readings.
	static void checkHigh(int num)
	{
		for (int i = 0; i < 5; i++)
		{	
			if (i == 0)
			{
				if (num > highestReadings.get(i))
				{
					highestReadings.set(i, num);
					continue;
				}
				else
				{
					break;
				}
			}
			
			if (highestReadings.get(i - 1) > highestReadings.get(i))
			{
				int temp = highestReadings.get(i - 1);
				highestReadings.set(i - 1, highestReadings.get(i));
				highestReadings.set(i, temp);
			}
		}// END OF for
	}// END OF checkHigh
	
	static void checkLow(int num)
	{
		for (int i = 4; i >= 0; i--)
		{	
			if (i == 4)
			{
				if (num < lowestReadings.get(i))
				{
					lowestReadings.set(i, num);
					continue;
				}
				else
				{
					break;
				}
			}
			
			if (lowestReadings.get(i + 1) < lowestReadings.get(i))
			{
				int temp = lowestReadings.get(i + 1);
				lowestReadings.set(i + 1, lowestReadings.get(i));
				lowestReadings.set(i, temp);
			}
		}// END OF for
	}// END OF checkLow

}// END OF Problem2

class Sensor extends Thread
{	
	int sensorNum;
	Sensor(int num)
	{
		sensorNum = num;
	}
	
	@Override
	public void run()
	{
		for (int i = 0; i < 60; i++)
		{
			int recordedTemp = new Random().nextInt(70 + 100) - 100;
			Problem2.tempReadings.add(recordedTemp);
			Problem2.checkHigh(recordedTemp);
			Problem2.checkLow(recordedTemp);
		}
		
		//System.out.println("Done!");
	}
}// End of Sensor


