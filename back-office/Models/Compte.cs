using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace pfa.Models
{
    public class Compte
    {
        [Key]
        public int IdCompte { get; set; }

        [Required, MaxLength(100)]
        public string Login { get; set; }

        [Required]
        public string Password { get; set; }

        // Navigation properties
        public virtual ICollection<Commande> Commandes { get; set; }
        public virtual ICollection<Avis> Avises { get; set; }
    }
}
