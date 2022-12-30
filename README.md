# Projet PC
## Introduction
Les objectifs de ce projets sont les suivants :
- poser des formes
- créer des groupes contenant des formes et/ou des groupes
- déplacer des formes et/ou groupes
- Exporter le dessin aux formats json et xml
- charger un dessin dans ces mêmes formats

Les buts principaux de ce projet sont de nous faire pratiquer la programmation orienté objet en java en utilisant les patrons de conception.

## Installation
### Prérequis
- Java 17
- Maven 3.8.1

## Utilisation
### Poser une forme
Selectionnez la forme que vous souhaitez déposer, puis cliquez sur la zone blanche à l'enroit où vous souhaitez
la déposer.

### Mode sélection
Pour passer en mode sélection, il suffit de cliquer sur le bouton "Sélection" en forme de souris en bas à gauche.

Ensuite, il suffit de cliquer sur la ou les formes que l'on souhaite sélectionner.

On peut alors déplacer le ou les formes en le(s) glissant(s) avec la souris.

Vous pouvez aussi modifier l'élevation de la forme en cliquant sur le menu "Edit" puis "Elevate" ou "Lower".

Vous pouvez supprimer la forme en cliquant sur le menu "Edit" puis "Delete".

Il est aussi possible de grouper les formes en cliquant sur le menu "Edit" puis "Group".

Lorsqu'un seul goupe est sélectionné, il est possible de le dégrouper en cliquant sur le menu "Edit" puis "Ungroup".

Pour désélectionner les formes, il suffit de cliquer à un endroit vide de la zone de dessin.

### Exporter le dessin
Pour exporter le dessin, il suffit de cliquer sur "XML" ou sur "JSON" selon le format d'export souhaité.

### Charger un dessin
Pour charger un dessin, il suffit de cliquer sur "Import" puis de selectionner le fichier à importer..

## Documentation de conception
- Visiteur (pour l'enregistrement du dessin en XML ou JSON)
- Composite (pour la gestion des groupes)
- Factory (pour la création des formes)

### Concepts généraux


### Patron visiteur et composite
![](./doc/svg/Model!composite_visiteur_shapes.svg)

### Patron Factory
Il est utilisé pour la création des formes.
Nous avons choisi de ne pas en faire un diagramme de classe car il est extrêmement simple.
En effet, il s'agit d'une classe : `ShapeFactory` qui possède une méthode `createShape` qui dispose de plusieurs méthodes statiques
qui permettent de créer les différentes formes.
Elle nous sert aussi pour la sérialisation des formes afin de convertir nos DTO en formes.

### Patron Command
![](./doc/svg/Model!patron_command_whiteboard.svg)



## Conclusion

Sur les attendus, il nous manque plusieurs choses : l'implémentation du CTRL + Z, l'ajout d'une nouvelle forme spécifique, 
Ce projet nous a permis d'expérimenter différents patrons de conception.
Il nous a aussi appris à faire du clean code et à utiliser les tests unitaires pour vérifier le bon fonctionnement de notre code.

