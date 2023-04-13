Instructions for compiling
---------------------------

To compile, ensure you are in the appropriate directory.

Then, type javac Problem1.java
After hitting enter, type java Problem1

Follow the same instructions for problem 2, but replace "Problem1" with "Problem2".

My approach
-------------

PROBLEM 1


What I believe went wrong with the Minotaur's strategy was improper insertion and removal. I believe a servant could have been trying to insert a chain into the chain while another was removing a present that it was supposed to be linked to. As a result, there is now a gap in between presents and thus not each present recieved a thank you card when unchained. 

What I intended to do with my approach is as follows. I made my linked list based off of the LazyList implementation found in the textbook to represent the  chain of presents. I also made my Node class based off of the Node class from the textbook as well. 

For the presents, I made a list of integers, and made i range from 0 to 500000. I then looped through the list, and made each element's integer value match its index, for a total of 500000 unique numbers.

I then shuffled my list, and converted it to a synchronized list so that my threads could all properly access it.

For the servants, I created a class that extends the Thread class, and created a method that allowed them to remove a gift from the synchronized list so long as it was not empty. The run method for this class checks to see if the chain of presents AND the list of presents is not empty, and continously assigns 1 of the 3 possible actions at random until the prior condition becomes false. This intended to simulate that the servants could either add a present to the chain, remove one and write a thank-you card, or check if there is a present in the chain when the minotaur desires them too. 

The program then stops once both the chain of presents AND the list of presents is empty. This simulates that the servants grabbed every present, chained them, and eventually removed it and wrote a thank-you card.

PROBLEM 2

