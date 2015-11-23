# TP Synchro REST Festival

Les organisateurs d'un festival sont inquiets à propos de son organisation et
font appel à vous pour en réaliser une simulation. Votre simulation devra
s'appuyer sur un programme multi-threadé en java, et sur la librairie Restlet
pour le développement d'une API permettant son contrôle. Votre travail se
découpe en trois parties à faire séquentiellement.

## 1. Simulation

Votre simulation devra contenir les éléments suivants:

- une billetterie à laquelle on peut acheter des billets. Le nombre de billets est limité.

- un site de départ, sur lequel les festivaliers peuvent, une fois munis d'un billet, prendre un bus qui les mènera à l'aire de concert. Le site de départ est grand et peut accueillir simultanément plus de bus qu'il n'y en aura réellement en service au moment du festival.

- des festivaliers, potentiellement nombreux, qui ont le comportement suivant. D'abord, ils doivent acheter un billet à la billetterie. On ne peut acheter qu'un billet à la fois. S'il n'y a plus de billet, un festivalier ne peut pas prendre de bus (en gros il peut rentrer chez lui). Un festivalier muni d'un billet peut monter dans un bus non plein qui est sur le site de départ. Un festivalier cherche à monter dans un bus non plein dès que possible.  Une fois monté dans le bus, un festivalier peut se reposer. On lui demandera juste de descendre et d'entrer dans l'aire du
concert. Une fois un festivalier à l'aire de concert, votre travail de simulation le concernant s'arrête.

- des bus, avec un nombre de places limité, qui ont le comportement suivant: en boucle, ils rentrent sur le site de départ, puis attendent un
certain temps pour laisser les gens monter.  Qu'ils soient plein ou non, ils partent au bout de ce temps. Ils voyagent jusqu'à l'aire de concert, et font descendre les passagers. Ils retournent sur le site de départ pour recommencer ce processus (ou plutôt ce thread).

## 2. API REST de contrôle

Afin de faciliter le contrôle de la simulation, les organisateurs aimeraient avoir à leur disposition une API permettant les actions suivantes: 1) Rajouter plusieurs festivaliers à la fois, 2) Rajouter plusieurs bus à la fois, 3) Récupérer les informations courantes à propos des festivaliers, en particulier leur état. Un festivalier est à tout moment dans l'un des quatre états suiants:

- Etat 'A', le festivalier est créé
- Etat 'B', le festivalier est muni d'un billet
- Etat 'C', le festivalier est monté dans un bus
- Etat 'D', le festivalier est descendu du bus

On cherchera à garder les instants de passage à chacun de ses états. Ainsi, on
vous demande d'implémenter l'API suivante:

URI                       | commande  | description
--------------------------|-----------|------------------
/people                   | GET       | récupère la liste des festivaliers
/people                   | POST      | ajoute un certain nombre de festivaliers
/people/{people-id}       | GET       | récupère les infos d'un festivalier
/people/{people-id}/stats | GET       | récupère les quatre instants de changement d'état d'un festivalier
/buses                    | POST      | ajoute un certain nombre de bus

Les communications entre clients et serveurs se feront avec le format JSON. Voici quelques
exemples de format de réponses attendues:

### GET /people

    [
      {
        "id": 0,
        "url": "/people/0"
      },
      {
        "id": 1,
        "url": "/people/1"
      }
    ]

### GET /people/{people-id}

    {
      "id": 1,
      "state": "C",
      "url_stats": "/people/1/stats"
    }

### GET /people/{people-id}/stats

```
#!json


{   "A":1448195509051,
    "B":1448195509051,
    "C":1448195510011,
    "D":1448195514019
}
```

Remarque: Inspirez-vous du tp-restlet-twitter pour votre hiérarchie de répertoires. 

## 3 - Statistiques (bonus)

Enfin, si vous avez le temps, les organisateurs vous demande d'enrichir l'API
permettant de collecter les statistiques du nombre de festivaliers dans
chaque état, ainsi que le temps qu'il s'est écoulé en moyenne entre le moment
où un festivalier est créé et le moment où il arrive sur l'aire de concert:

### GET /stats

    {
      "states": [
        {
          "state": "A",
          "nb": 24
        },
        {
          "state": "B",
          "nb": 44
        },
        {
          "state": "C",
          "nb": 84
        },
        {
          "state": "D",
          "nb": 2
        }
      ],
      "temps": 385 # temps moyen d'exécution de tous les festivaliers ayant terminé
    }


## 4 - Livrable

Pour le 9 décembre 2015, vous devez envoyer à votre chargé de TP
(XOR[matthieu.simonin@inria.fr, david.guyon@inria.fr], dans un mail dont le sujet
sera "[TP-CSR] <nom1>-<nom2>", le code de votre TP richement commenté ainsi
qu'un rapport court contenant:

- la description des classes de votre simulateur
- la liste des problèmes de synchronisation rencontrés
- une trace commentée d'exécution
- toute information que vous jugerez utile à la prise en main de votre code
