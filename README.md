## Planar Graph Project

<p><em>This is an extension of My-Project that displays the appropriate message depending on whether the graph is planar or not.</em></p>
<p>In graph theory, a planar graph is a graph that can be embedded in the plane, i.e., it can be drawn on the plane in such a way that its edges intersect only at their endpoints. In other words, it can be drawn in such a way that no edges cross each other. Such a drawing is called a plane graph or planar embedding of the graph. A plane graph can be defined as a planar graph with a mapping from every node to a point on a plane, and from every edge to a plane curve on that plane, such that the extreme points of each curve are the points mapped from its end nodes, and all curves are disjoint except on their extreme points.</p>
<p>To do so, this project implements a <i>heuristic method</i> called <strong>circle-chord method</strong>.</p>
<p>It consists of a step-by-step method of drawing the graph, edge-by-edge without crossing any edges.
</p>
<p><u>Step One:</u>  Find a circuit that contains all the vertices of the graph.	(Recall: a circuit is a path that ends where it starts)
</p>
<p><mark>Step Two:</mark>  Draw this circuit as a large circle.</p>
<p><mark>Step Three:</mark> Choose one chord, and decide to draw it either inside or outside the circle. If chosen correctly, it will force certain other chords to be drawn opposite to the circle. (Inside if the first chord was drawn outside, and vice versa.)
</p>
