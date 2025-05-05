using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace pfa.Models
{
    public class Commande
    {
        [Key]
        public int IdCom { get; set; }

        public decimal PrixTotale { get; set; }

        public DateTime DateCommande { get; set; }

        public string Status { get; set; }

        public DateTime DateEstime { get; set; }

        public string Adresse { get; set; }

        // Lié à un compte
        public int CompteId { get; set; }
        public virtual Compte Compte { get; set; }

        // FK vers Livreur
        public int LivreurId { get; set; }
        public virtual Livreur Livreur { get; set; }

        // Relation *-* avec Produit
        public virtual ICollection<Produit> Produits { get; set; }
    }
}
