using System.ComponentModel.DataAnnotations;

namespace pfa.Models
{
    public class Avis
    {
        [Key]
        public int IdAvis { get; set; }

        public int NbrEtoile { get; set; }

        public string Commentaire { get; set; }

        public int CompteId { get; set; }
        public virtual Compte Compte { get; set; }
    }
}
