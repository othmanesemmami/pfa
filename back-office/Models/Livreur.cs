using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace pfa.Models
{
    public class Livreur
    {
        [Key]
        public int IdLivreur { get; set; }

        [Required, MaxLength(50)]
        public string Nom { get; set; }

        [MaxLength(50)]
        public string Prenom { get; set; }

        [MaxLength(20)]
        public string CIN { get; set; }

        public string Adresse { get; set; }

        public int NbrCommande { get; set; }

        public virtual ICollection<Commande> Commandes { get; set; }
    }
}
