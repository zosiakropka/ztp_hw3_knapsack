Advanced Programming Techniques

======================
Homework 3: (Knapsack)
======================

Problem
=======

There is *n x n* square slab given. *int n* is given as program input parameter 
`<square size>`. Input file (pointed by program input parameter `<input_file>`) 
consists of numbers pairs *(int w, int h)*, describing sizes of rectangle 
elements to be cut off from the slab.

`Knapsack` class should keep problem's structure and its method `int pack()` is 
expected to solve it any in way that ensures the least amount of waste left. It 
should return waste's area. 

Technical requirements
======================

Compilation: ::

	javac –Xlint Knapsack.java Main.java

Usage: ::

	java Main <input_file> <square_size>

Output: ::

	Waste area: <waste_area>

