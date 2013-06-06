

%facts
ingredients([['Wsciekly'],['Pies']]	,[['wodka'],[['sok'], ['malinowy']],['tabasko']]).
ingredients([['Wolski'],['Kafar']]	,[['wodka'],['pieprz']]).
ingredients(['Mojito'],[['rum']		,[['sok'] ,['limonki']],['limonka'],['lod'],[['cukier'],['trzcinowy']]]).
ingredients([['White'],['Malibu']]	,[['malibu'],['mleko']]).
ingredients([['Black'],['Malibu']]	,[['malibu'],['olej'], ['samochodowy']]).


%rules
%not_empty(L) :- E=[], L\=E.
%takie , gdzie R
%contains(I) :- findall(X,(ingredients(X,L),subset(I,L)),R), write(R), not_empty(R).

%subset([A|X], Y) :- member(A,Y), subset(X,Y).
%subset([], []).
%subset([E|Tail], [E|NTail]):- subset(Tail, NTail).
%subset([_|Tail],  NTail   ):- subset(Tail, NTail).
  
%zawiera_cos(I, X) :- ingredients(X,L),member(I,L).

%join([ ], L2, L2).
%join([X|L1], L2, [X|L3]):- join(L1, L2, L3). 
%sublist(S, L):-join(L1, L2, L), join(S, L3, L2). 

%zawiera_cos(I, X) :- ingredients(X,L),sublist(I,L).

%zawiera_cos(I, X) :- ingredients(X,L),member(I,L).

member_rec(M, [M|_]) :- write(M), write('  0: '), write(M), nl, true.
%member_rec(M, [[H]|_]) :- write(M), write('  1 HEAD:: '), write(H), nl, member_rec(M,H).

member_rec(M, [H|T]) :- write(M), write('  2 HEAD: '), write(H), write('  '), write(T), nl, ( member_rec(M,T) ; member_rec(M,H) ).
%member_rec(['sok'],[['wodka'],[['sok'], ['malinowy']],['tabasko']]).


%isIngredient(I) :- zawiera_cos(I, L).

%isDrink(I) :- ingredients(I,L).

%reguly potrzebne do rozwiazania:
%pytanie od kelnera czym jest "składnik":
%isIngredient :- 
%isDrink :- 

%Ptanie zero - co to jest
isIngredient(I) :- ingredients(N, S), member_rec(I,S).
isDrink(I) :- ingredients(N,S), member_rec(I,N).

%A. Pytanie jak robi się rum z colą?
%wystarczy ingredients

%B. Mam składnik1 i składnik2, co mogę zrobić?.


%C. Mam cole i whisky czego mi brakuje do whisky z lodem?

