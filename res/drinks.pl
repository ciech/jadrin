%facts
ingredients([['Wsciekly'],['Pies']]	,[['wodka'],[['sok'], ['malinowy']],['tabasko']]).
ingredients([['Wolski'],['Kafar']]	,[['wodka'],['pieprz']]).
ingredients(['Mojito'],[['rum']		,[['sok'] ,['limonki']],['limonka'],['lod'],[['cukier'],['trzcinowy']]]).
ingredients([['White'],['Malibu']]	,[['malibu'],['mleko']]).
ingredients([['Black'],['Malibu']]	,[['malibu'],['olej'], ['samochodowy']]).
ingredients([['Lagodny'],['Pies']]	,[['wodka'],[['sok'], ['malinowy']],['tabasko'], ['mleko']]).


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
is_ingredient(I) :- ingredients(N, S), member_rec(I,S).
is_drink(I) :- ingredients(N,S), member_rec(I,N).

%===
%A. Pytanie jak robi się rum z colą?
%wystarczy ingredients([['Wsciekly'],['Pies']], X). + zwrócić przepis.

%===
%B. Mam składnik1 i składnik2, co mogę zrobić? 

%Zwraca tylko te drinki,ktore wykorzystuja wszystkie podane skladniki
what_can_i_do(I, D, L) :-  ((ingredients(D,L),member(I,L)) ; length(I,0)).
what_can_i_do([I|T],D,L) :-  (ingredients(D,L),member(I,L)) , what_can_i_do(T, D, L).

%Zwraca wszystkie drinki ktore wykorzystuja ktorykolwiek skladnik
what_can_i_do2([I|T],D,L) :-  (ingredients(D,L), (member(I,L)) ; what_can_i_do2(T, D, L)).

%===
%C. Mam cole i whisky czego mi brakuje do whisky z lodem? Zwaraca prawidlowy wynik lezeli mamy wiecej skladnikow niz jest w przepisie.

what_is_missing(I,D, R) :-  ingredients(D,L), deletelist(L,I, R).



