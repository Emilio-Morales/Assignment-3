Instructions for compiling
---------------------------

To compile, ensure you are in the appropriate directory.

Then, type javac Problem1.java
After hitting enter, type java Problem1

Follow the same instructions for problem 2, but replace "Problem1" with "Problem2".

My approach
-------------

PROBLEM 1


What I believe went wrong with the Minotaur's strategy was improper insertion and removal. I believe a servant could have been trying to insert a present into the chain while another was removing a present that it was supposed to be linked to. As a result, there is now a gap in between presents and thus not each present recieved a thank you card when unchained. This could also have occurred in reverse. A present was being removed as one was being inserted in front of it. This leads to the newly inserted present being chained to the same present as the present before the one that was removed. 

What I intended to do with my approach is as follows. I made my linked list based off of the LazyList implementation found in the textbook to represent the  chain of presents. I also made my Node class based off of the Node class from the textbook as well. 

For the presents, I made a list of integers, and made i range from 0 to 500000. I then looped through the list, and made each element's integer value match its index, for a total of 500000 unique numbers.

I then shuffled my list, and converted it to a synchronized list so that my threads could all properly access it.

For the servants, I created a class that extends the Thread class, and created a method that allowed them to remove a gift from the synchronized list so long as it was not empty. The run method for this class checks to see if the chain of presents AND the list of presents is not empty, and continously assigns 1 of the 3 possible actions at random until the prior condition becomes false. This intended to simulate that the servants could either add a present to the chain, remove one and write a thank-you card, or check if there is a present in the chain when the minotaur desires them too. 

The program then stops once both the chain of presents AND the list of presents is empty. This simulates that the servants grabbed every present, chained them, and eventually removed it and wrote a thank-you card.

PROBLEM 2

For this problem, I created three Lists. One for storing all of the temperature readings, one for storing the 5 highest, and one for storing the 5 lowest. I then converted these lists to synchronized lists so that my threads to access and alter them properly. For the temperature sensors, I replicated them by creating a Sensor class that extends Thread. Inside of this class, I overrode the run method, and within it I would generate 60 values at random ranging from -100 to 70. I did this to simulate 60 temperature readings occuring within 1 hour since the problem states that the sensors take temperature readings every one minute. 

In addition, these readings are being added into the list that stores all the readings. Every time we have a new reading, it checks that value with the values stored in the highest value array, and lowest value array, and alters the readings stored there if necessary. 

With regards to the effiency of the program, it seems to be running smoothly. You can alter the number of values that the threads generate to simulate more hours. Even when generating 60000 values, simulating 1000 hours, it finishes quickly. The lowest and highest readings are also consistent with each simulation.

