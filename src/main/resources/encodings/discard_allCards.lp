mano(V, cuori) :- in_hand_HELPER(V, "hearts").
mano(V, fiori) :- in_hand_HELPER(V, "clubs").
mano(V, quadri) :- in_hand_HELPER(V, "diamonds").
mano(V, picche) :- in_hand_HELPER(V, "spades").

discard_HELPER(V, hearts) :- scarta(V, cuori).
discard_HELPER(V, clubs) :- scarta(V, fiori).
discard_HELPER(V, diamonds) :- scarta(V, quadri).
discard_HELPER(V, spades) :- scarta(V, picche).

seme(cuori). 
seme(quadri). 
seme(fiori). 
seme(picche).

scarta(V, S) :- mano(V, S).