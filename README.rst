Zaawansowane Techniki Programowania

==========================
Praca domowa 3: (Knapsack)
==========================

Problem
=======

Dana jest płyta *n x n*. *int n* podane jest jako parametr programu 
`<square_size>`. Plik wejściowy, wskazany przez parametr wejściowy 
`<input_file>` składa się z par *(int w, int h)*, opisujących rozmiary 
prostokątnych elementów do wycięcia z płyty.

Klasa `Knapsack` powinna przetrzymywać strukturę problemu. Jej metoda 
`int pack()` powinna rozwiązywać go w sposób pozostawiający jak najmniejszą 
ilość odpadu. Zwracać powinna obszar odpadu.

Uruchamianie
============

Kompilacja: ::

	javac –Xlint Knapsack.java Main.java

Uruchamianie: ::

	java Main <input_file> <square_size>

Wyjście: ::

	Waste area: <waste_area>

Algorytm
========

Algorytm zaimplementowany w programie dzieli obszar rekursywnie na mniejsze 
puste i wypełnione obszary. Pierwszy element umieszczamy w lewym górnym rogu. 
Dzielimy obszar na ten poniżej dolnej granicy elementu i z górnej części 
wydzielamy obszar na prawo od elementu. Jeśli chcemy dodać kolejny element, 
sprawdzamy czy zmieści się powyżej poziomej granicy. Jeśli nie, to sprawdzamy na 
lewo od pionowej granicy. W którymś z tych obszarów się zmieści, dzielimy go tak 
samo, jak dzieliliśmy obszar oryginalny. W innym wypadku umieszczamy poniżej 
poziomej granicy i dzielimy tak ten obszar.



