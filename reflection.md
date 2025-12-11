# Final Project Reflection

Declare/discuss any aspects of your code that are not working. What are your intuitions about why they are not working? Acknowledge and discuss any parts of the program that appear to be inefficient.

What are some of the most important lessons you learned while working on this assignment? Why do you think so?

What was the most challenging aspect of this assignment? Why?

The most challenging aspect of this assignment was implementing dijkstra's algorithm, specifically the setup and initialization of all the things that you need to keep track of for the algorithm to work. once those were set up, I had a much easier time implementing the rest of it. I especially struggled trying to figure out how to keep track of the all of the distances associated with each node. I tried to do the way suggested to me, using a hashmap with the nodes as a keys and their distances as the values. However, I struggled with figuring out how to combine it with the priority queue that we were using for the rest of the algorithm because you would need a comparator which could have been Map.Entry.comparing by value, but then the priority queue would have to store the hashmap entries and not just the nodes, and this made adding nodes to the queue seem more difficult. I eventually switched to using a distance variable in the node class and making the node class comparable with this. I think however, that this might not be as efficient as using the hashmap to keep track of the distances.

Another slight difficulty I had at the beginning was understanding how all of the different parts of the program worked, what they did, how they interacted with each other, what I needed to do, and how I was supposed to do it. This was solved by about 2 hours of just reading and rereading everything in the files, as well as the project overview, and our class notes.

Through completing this assignment, I have gained a much better understanding of dijkstra's algorithm, how it works and what it does. I now also have a clearer understanding of breadth first search and depth first search, and how they function with code, not only in a theoretical sense. Another important thing I have learned from this assignment is how to better understand code that is written by others. This is important because it is necessary for working on projects with other people, especially in professional environments where many people might be working on one thing.

ineffiencies...
