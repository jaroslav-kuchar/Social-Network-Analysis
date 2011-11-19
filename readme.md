# Social Network Analysis
Author: Jaroslav Kuchar

<a href="https://gephi.org/plugins/social-network-analysis/">Social Network Analysis</a> plugin for <a href="http://www.gephi.org">Gephi</a>. 
This plugin allows compute metrics, which can be used in Social Network Analysis. 

This version contains:

  * Erdös number - idea of "The Small-World Phenomenon". Can be visualized using Layered Layout Plugin
  * Neighborhood Overlap and Embeddedness - identification of strong and weak ties, local bridges
  * Clustering Coefficient

# Tutorial
## Erdös number
This metric (<a href="http://en.wikipedia.org/wiki/Erd%C5%91s_number">Erdos number</a>) can be used to compute how close are other nodes to the specified node. It is based on the idea of "The Small-World Phenomenon".
How small is simple world. The average number is how many nodes (persons, friends, ...) in average are between selected node and other nodes. Simple example is visualized on the following figure. The metric was computed for node A and is visualized as color. 
The darker the color is the largest number was computed. The average for node A is 1.667.

![erdos](http://img803.imageshack.us/img803/7513/snmekobrazovky20111119v.png)

## Neighborhood Overlap and Embeddedness
These metric can be used to visualize, how strong are ties. The following example depicts how strong are edges. The lighter are bridges, local bridges etc. The dark red are strong edges. They are between nodes in strong connected group of nodes.

![overlap](http://img841.imageshack.us/img841/7513/snmekobrazovky20111119v.png)

## Clustering coefficient
The <a href="http://en.wikipedia.org/wiki/Clustering_coefficient">local clustering coefficient</a> of the light blue node is computed as the proportion of connections among its neighbors which are actually realized compared with the number of all possible connections.

![clustering](http://img825.imageshack.us/img825/7513/snmekobrazovky20111119v.png)

# License
The GPL version 3, http://www.gnu.org/licenses/gpl-3.0.txt