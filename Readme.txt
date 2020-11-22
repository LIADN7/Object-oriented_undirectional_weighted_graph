Readme:
The classes presents undirectional weighted graph, 
which allows you to:
add vertices  [O(1)],
remove node [O(N+V);|N|=vertices in the graph, |V|= edges of ]
add and delete connections between vertices  [O(1)], 
make a deep copy of the graph  [O(N);|N|=all the edges should be removed], 
check the shortest distance between two vertices  [O(N+V);|N|=vertices in the graph, |V|= edges], 
give the shortest path between two vertices  [O(N+V);|N|=vertices in the graph, |V|= edges],
save and load by Gson elements,
you can compare between graphs.

Builds a graph with a million vertices in less than 10 seconds

There are testers checking:
if save and load works, and check if they equals,
Check the shortest Path and the distance,
and check if copy of the graph work,
all the simple update.