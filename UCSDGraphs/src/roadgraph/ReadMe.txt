Class: MapGraph
Modifications made to MapGraph (what and why): All modifications can be divided into two categories, refactoring and class design. 

Refactoring is simple, I added private helper methods to improve the readability and understandability of the code, like a method to verify the validity of all input parameters, or a method to perform BFS and generate the parent map like in the example proposed by Christine. 

As for class design, 4 instance variables are added. The use of numVertices and numEdges are clear as the name shows. mapGraph is a HashMap mapping GeographicPoint objects to MapNode objects, where the latter is a vertex in the graph representing the structure of a road map. edges is a nested HashMap structure. The key of this map is MapNode, representing the start vertex from a directed edge. The value of this key is another HashMap, whose key is another MapNode, representing the end vertex from a directed edge. The value of this second key is an MapEdge class object, storing information like street name and distance of the edge. I choose this nested HashMap structure because it's easy to implement and because HashMap is fast in insertion and searching.

Class name: MapNode

Purpose and description of class: Since we have to abstract the real-world road map into a mathematical graph, the purpose of this class is to represent a node in that graph. This class has only two private instance variables but they are enough to provide the information we need. The first variable is location of type GeographicPoint, storing the location, and the second variable is neighbors a list of adjacent  MapNodes.

Class name: MapEdge

Purpose and description of class: As the name shows, this class represents an edge in the graph. It stores information of two end vertices, road name, road type and distance.

Overall Design Justification (4-6 sentences):
If you watched the support video, you may find my class design very similar to what Leo has proposed. That is reasonable because that is the most intuitive design, a simple and clear way to contain all necessary information. 

I think the part that needs more explanation is why I employed nested HashMap, one in the other, to get the edge information. I think that's the simplest way to search for an edge. Since we need two nodes to identify an edge and the node is no longer a simple data type like integer, we will need a two layer structure anyway to find the edge. Among all possible structure, MapNode is the one most efficient in insertion and searching.

Apart from this, I used another HashMap associating GeographicPoint to MapNode. This implementation is for efficient search. Once we enter a GeographicPoint, we can get the corresponding MapNode within O(1) time.