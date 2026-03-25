---
geometry:
    - a4paper
    - margin=2.5cm
    - textwidth=16cm
---

# FAMP - Appello 2

## Parte di analisi

### Problema 1
Si consideri la funzione $\mathbb{R}^3 \rightarrow{} \mathbb{R}$ definita da
$$ f(x,y,z) = 5e^{-6z^2}\cosh{3x+2y}-5\cos{y}+3 $$

 (a) Scrivere la matrice Hessiana di $f$ nell'origine $(0,0,0)$ e inserire il valore del suo determinante

 (b) Se la funzione assume massimo ristretta al piano $\{x=0\}$, inserire il valore di tale massimo. Se ciò non e' possibile, inserire il valore -111111

 (c) Si restringa la funzione al piano $\{x = 2\pi\}$. Se tale restrizione ammette minimo, inserire il valore di tale minimo. Se ciò non e' possibile inserire il valore -111111

### Problema 2
Si consideri il campo vettoriale $\bold{F}:\mathbb{R}^2\rightarrow{}\mathbb{R}^2$ definito da
$$\bold{F}(x,y) = (9x^2-3y+3x\sin(x^2+y^2), 3x+3y\sin(x^2+y^2))$$
Si consideri il semidisco superiore
$$ D = \{(x,y):x^2+y^2 \le 25, y \ge 0 \} $$
Il bordo è orientato positivamente ed è formato da un segmento orrizontale descritto dalla curva parametrica $\gamma_1$ e da un
semicerchio descritto dalla curva parametrica $\gamma_2$.

 (a) Calcolare il lavoro di $\bold{F}$ lungo $\gamma_1$

 (b) Calcolare il lavoro di $\int_{\partial^+D}{\bold{F} \cdot{} dr}$

 (c) Dedurre dai punti precendenti il lavoro di $\bold{F}$ lungo $\gamma_2$

### Problema 3

Si consideri il dominio piano
$$ D = \{ (x,y): 0 \le x \le 3, 0 \le y \le 5x \} $$
Si consideri inoltre la funzione
$$ f(x,y) = 4x^2+5y $$
Si consideri anche il campo vettoriale $\bold{V}:\mathbb{R}^2\rightarrow{}\mathbb{R}^2$ definito da
$$ \bold{V}(x,y) = (-2.5x^2, \frac{4}{3}y^3) $$

 (a) Calcolare il valore dell'integrale doppio di $f$ in $D$

 (b) Calcolare il lavoro di $V$ lungo il segmento orientato orrizontale dall'origine a $(3,0)$

 (c) Calcolare il lavoro di $V$ lungo il segmento verticale da $(3,0)$ a $(3,15)$

 (d) Dedurre dai precendenti punti il lavoro di $V$ lungo il segmento dall'origine a $(3,15)$

### Problema 4
Si sta eseguendo il rendering di una scena contenente un'immagine circolare. Il costo computazionale per elaborare ogni pixel $(x,y)$ dell'immagine è modellato dalla funzione
$$ z = f(x,y) = 5(x^2+y^2) $$
I pixel più lontani dal centro richiedono più rimbalzi  causa della complessità geometrica della scena in quelle regioni. L'immagine da renderizzare è definita sul dominio, espresso in pixel,
$$ D = \{ (x,y,z) \in \mathbb{R}^3 : (x,y) \in D, z=5(x^2+y^2) \} $$
dove la terza dimensione $z$ rappresenta il costo computazionale nello "spazio del costo". Determinare l'area della superficie $S$ e selezionare la risposta corretta, indicando sul foglio che si consegna gli elementi essenziali della scelta.

- [ ] a. mi avvalgo della facoltà di non rispondere
- [ ] b. -1550.3856
- [ ] c. 42.2370
- [ ] d. 1033.5904
- [ ] e. 13.4444