# Modelling the system of an agricultural cooperative
## Miniproject Organisation
- all the code is in the app/src/main/java/miniproject directory
- the main is contained in app/src/main/java/miniproject/app/App.java
- The configuration parameters are in app/src/main/java/miniproject/config/Config.java
- A sample database for the farmers and the malls are in app/src/main/resources


## Miniproject Objective
L’objectif est de modéliser le système d’une coopérative agricole et d’en simuler le fonctionnement :
Dans une coopérative, plusieurs producteurs  livrent leur marchandise dans un entrepôt commun
L’entrepôt a forcément une taille limitée
Les agriculteurs livrent à une fréquence différente, entre 1 et 5 fois par semaine
Les livraisons peuvent être de tailles différentes
La coopérative dispose de plusieurs camions qui fonctionnent en 24/7 pour charger la marchandise depuis l’entrepôt et la livrer vers des destinations différentes en europe (hypermarchés)
Le temps de livraison du camion dépend de sa destination
 
L’objectif est de modéliser ce système et ensuite d’en simuler le fonctionnement sur une durée déterminée (plusieurs mois) avec différents paramètres :
Nombre d’agriculteurs
Taille de l’entrepôt
Capacité min et max des camions
Nombre de camions
On peut se contenter de faire un log à chaque livraison dans l’entrepôt ou vers un hypermarché
