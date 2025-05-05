using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace pfa.Models
{
    public class Produit
    {
        [Key]
        public int IdProduit { get; set; }

        public int Quantite { get; set; }

        public string Categorie { get; set; }

        public decimal Prix { get; set; }

        public string Description { get; set; }

        public string Image { get; set; }

        public virtual ICollection<Commande> Commandes { get; set; }
    }
}
