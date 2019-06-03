## Mecaworks :

Ce projet est une application web qui a pour but la gestion de Gasoil des entreprises BTP.

> **This project is no longer maintained**

---

Here is the list of supported services and features in v0.1.0 :

## Services :

### **Admin**

CRUD operations for these entities :

- Chantier
- Engin
- Employe
- Fournisseur
- SousFamille
- Famille
- Groupe
- Marque
- Classe

### **User/Inspection**

- Gazoil dashboard (onglet 1 & 2) & chantier dashboard (onglet 1, 2 & 3)
- Bons filters (engin - fournisseur - livraison)
- BonEngin filter (grouped by engin)
- Alerts filter

### **Saisi**

CRUD operations for :

- BonEngin
- BonLivraison
- BonFournisseur

**Formation**

Provide instructional videos about the business model, the application and how to work with it.

## Features :

**Security**

- Login/logout
- Users management (admin section)
- Admin confirmation code to proceed adding BonEngin in `saisi` section

**i18n**

- auto mode depending on `accept-language` http header.
- only french is supported in this version
