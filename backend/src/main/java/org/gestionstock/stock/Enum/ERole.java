package org.gestionstock.stock.Enum;

public enum ERole {

    //Gestion des stocks
    GESTIONNAIRE_STOCK,
    PREPARATEUR_COMMANDE,

    // Achats et approvisionnements
    RESPONSABLE_ACHATS,
    FOURNISSEUR,

    // Ventes et distribution
    COMMERCIAL,
    CONTROLEUR,

    // Support technique
    TECHNICIEN_SUPPORT,

    // Analayse et reporting,
    ANALYSE_METIER,

    // Clients (optional)
    CLIENT_FINAL,

    // Roles transverses
    SUPER_ADMINISTRATEUR,
    INTEGRATEUR_TECHNIQUE,
    RESPONSABLE_QUALITE,

    // Autres
    GESTIONNAIRE_LOTS,
    AGENT_INVENTAIRE
}
