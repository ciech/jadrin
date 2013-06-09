%rules
%not_empty(L) :- E=[], L\=E.

member_rec(M, [M|_]) :- true.
member_rec(M, [H|T]) :- ( member_rec(M,T) ; member_rec(M,H) ).


remove(X, [X|Ogon], Ogon).
remove(X, [Y|Ogon], [Y|Ogon1]) :- remove(X, Ogon, Ogon1). 


deletelist([], _, []).                  
deletelist([X|Xs], Y, Z) :- member(X, Y), deletelist(Xs, Y, Z), !.
deletelist([X|Xs], Y, [X|Zs]) :- deletelist(Xs, Y, Zs).
%============================================================================
%============================================================================
%Ptanie zero - co to jest za typ?
%is_ingredient(I) :- ingredients(N, S), member_rec(I,S), write(I), write(S).
%is_drink(I) :- ingredients(N,S), member_rec(I,N), write(I), write(N).



%is_ingredient(I) :- ingredients(N,S), I == S .

is_part_of(I) :- (is_part_of_ingredient(I)  ; is_part_of_drink(I)) .
is_part_of_ingredient(I) :- (ingredients(N, S), member_rec(I,S), \+ (member(I,S))).
is_part_of_drink(I) :-  ( ingredients(N,S), member_rec(I,N), I\==N ).

is_drink(I) :- ingredients(N,S), I == N.

is_ingredient(I) :- ingredients(N, S), member_rec(I,S) , \+ is_part_of_ingredient(I).

%===
%A. Pytanie jak robi sie rum z cola?
%wystarczy ingredients([['Wsciekly'],['Pies']], X). + zwrocic przepis.

%===
%B. Mam skladnik1 i skladnik2, co moge zrobic? 

%Zwraca tylko te drinki,ktore wykorzystuja wszystkie podane skladniki
what_can_i_do(Ingredient, Drink, List, Recipe) :-  ((ingredients(Drink,List),member(Ingredient,List)) ; length(Ingredient,0)), recipe(Drink,Recipe).
what_can_i_do([Ingredient|Tail],Drink,List, Recipe) :-  (ingredients(Drink,List), member(Ingredient,List)), what_can_i_do(Tail, Drink, List,Recipe ), recipe(Drink,Recipe).

%Zwraca wszystkie drinki ktore wykorzystuja ktorykolwiek skladnik
%what_can_i_do2([Ingredient|Tail],Drink,List) :-  (ingredients(Drink,List), (member(Ingredient,List)) ; what_can_i_do2(Tail, Drink, List)).

%===
%C. Mam cole i whisky czego mi brakuje do whisky z lodem? Zwaraca prawidlowy wynik lezeli mamy wiecej skladnikow niz jest w przepisie.

what_is_missing(I,D, Missing, Recipe) :-  ingredients(D,L), deletelist(L,I, Missing), recipe(D,Recipe).



