## Object Oriented Programming Lab

This repo contains task for Object Oriented Programming course laboratiores.

## Changelog:

 - [x] [`lab1`](https://github.com/xramzesx/oolab/releases/tag/lab1) - initial labs
 - [x] [`lab2`](https://github.com/xramzesx/oolab/releases/tag/lab2) - add enums and unit tests
 - [x] [`lab3`](https://github.com/xramzesx/oolab/releases/tag/lab3) - add `Animal` class and integration tests
	Task/Question 10 (without translation):
	```
	Odpowiedz na pytanie: jak zaimplementować mechanizm, który 
	wyklucza pojawienie się dwóch zwierząt w tym samym miejscu.
	```
	Answer:
	```
	W tym celu stworzyłbym 2-wymiarową, statyczną HashMapę wewnątrz
	klasy Animal, która dla każdego zajętego punktu (x,y) 
	przechowywałaby informację czy dane miejsce	jest aktualnie wolne
	(lub która przechowywałaby referencje do aktualnego obiektu `Animal`,
	który się tam znajduje)

	Dodatkowo, podczas tworzenia nowego zwierzęcia, potrzebaby było
	zaimplementować mechanizm wyszukujący najbliższego wolnego miejsca,
	np. wykorzystujący BFS.
	```