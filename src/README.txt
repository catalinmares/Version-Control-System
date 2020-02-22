\\------------------------ Mares Catalin-Constantin -------------------------// 

\\\------------------------------ Grupa 322CD ------------------------------///

\\\\------------------ PROGRAMARE ORIENTATATA PE OBIECTE ------------------////

\\\\\----------------------------- Tema nr. 2 ----------------------------/////

\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\////////////////////////////////////////



PREZENTAREA CLASELOR SI A METODELOR
Proiectul pe care l-am realizat, este compus, pe langa clasele din schelet, din
inca 9 clase pe care mi le-am definit in vederea sistemului de versionare.
Aceste clase sunt:

1. Commit - clasa specifica commit-urilor, retinand ID-ul commitului, starea
sistemului de fisiere la momentul crearii commitului si mesajul cu care a fost
creat commit-ul. Aceasta clasa are 3 metode de get folosite pentru operatiile
de checkout (getCommitId si getActiveSnapshot) si log (getCommitId si getMessage).

2. Branch - clasa specifica branch-urilor, retinand numele branch-ului, daca acesta
este head (este activ la un moment dat), o lista de commituri si o copie a
sistemului de fisiere care reprezinta starea sistemului de fisiere la momentul
crearii branch-ului. Pe langa metode de get si set, aceasta clasa implementeaza
urmatoarele metode:
a) resetHead - reseteaza head-ul (devine false, adica branch-ul nu va mai fi
	       vazut ca fiind branch-ul activ).

b) setHead - seteaza head-ul (opus fata de resetHead).

c) addCommit - adauga un nou commit in lista de commit-uri.

d) checkCommitId - verifica daca exista in lista de commit-uri un commit cu
		   ID-ul primit ca parametru.

e) moveTo - parcurge lista de commituri si sterge commiturile cu ID-ul mai mare decat
            ID-ul primit ca parametru.

f) getCommit - returneaza commit-ul cu ID-ul dat ca parametru din lista de commituri.

3. Clasele pentru operatii:
	- BranchOperation;
	- CheckoutOperation;
	- CommitOperation;
	- InvalidVcsOperation;
	- LogOperation;
	- RollbackOperation;
	- StatusOperation.

Fiecare dintre aceste clase extind clasa abstracta VcsOperation si implementeaza
metoda abstracta execute astfel:

a) BranchOperation - retine numele branch-ului de creat;
		   - apeleaza metoda din Vcs checkBranchName care verifica daca
		     exista deja un branch cu acelasi nume, caz in care se
                     returneaza eroare;
		   - in caz contrar, se apeleaza metoda din Vcs createBranch
		     care va crea noul branch.

b) CheckoutOperation - verifica daca sunt operatii in staging;
		     - daca sunt se returneaza eroare (nu se poate da checkout);
		     - daca nu sunt, se verifica daca se doreste mutarea pe un
		       commit anterior sau pe un alt branch;
		     - daca se doreste mutarea pe un commit anterior, se verifica
		       daca commitul cu ID-ul respectiv exista (metoda
		       checkCommitId din Branch);
		     - daca commitul nu exista se returneaza eroare, alfel ne
		       mutam pe acel commit, stergandu-le pe cele create dupa el
		       (metoda moveTo din Branch) si se seteaza sistemul de
		       fisiere la starea in care era la momentul crearii commitului
		       pe care ne-am mutat;
		     - daca se doreste mutarea pe un alt branch, verificam daca
		       exista un branch cu numele primit ca argument (metoda 
		       checkBranchName din Vcs);
		     - daca nu exista, se returneaza eroare; altfel ne mutam pe
		       branch-ul respectiv (metoda moveTo din Vcs).

c) CommitOperation - daca nu sunt operatii in staging returneaza eroare;
                   - salveaza mesajul commitului;
		   - se goleste staging-ul;
		   - se adauga noul commit in branch-ul curent (metoda
		     addCommit din Branch) clonand sistemul de fisiere.

d) InvalidVcsOperation - se returneaza eroare (operatia de vcs data este invalida).

e) LogOperation - se parcurge lista de commituri a branch-ului curent si se
		  scrie in fisier ID-ul si mesajul fiecarui commit.

f) RollbackOperation - se goleste staging-ul;
	    	     - se seteaza sistemul de fisiere la starea pe care o avea la 
                       crearea ultimului commit din branch-ul curent.

g) StatusOperation - se scrie in fisier numele branch-ului curent;
		   - se parcurge lista operatiilor din staging si se scrie in
		     fisier toate modificarile asupra sistemului de fisiere
		     de la ultimul commit.


De asemenea, in clasa Vcs, am implementat urmatoarele metode ajutatoare:
a) trackOperation - in functie de operatie, adauga in staging modificarea pe
		    care operatia respectiva a adus-o sistemului de fisiere.

b) getActiveBranch - returneaza branch-ul curent.

c) checkBranchName - verifica daca numele unui nou branch primit ca argument
                     pentru operatia branch este valid (nu exista deja un branch
                     cu acel nume);
		   - metoda folosita si la checkout asemanator.

d) createBranch - daca branch-ul curent are commituri, noul branch va avea
		  o copie a ultimei versiuni a sistemului de fisiere (a sistemului
		  de fisiere specific ultimului commit), altfel va avea o copie a
		  sistemului de fisiere al branch-ului curent;
		- am procedat astfel pentru cazul in care suntem pe un branch fara
                  commit-uri si vrem sa cream un alt branch.

e) moveTo - reseteaza head-ul branch-ului curent (nu va mai fi activ);
	  - cauta in lista de branch-uri branch-ul cu numele primit ca parametru si
	    ii seteaza head-ul si seteaza sistemul de fisiere la starea din noul
	    branch.