using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace pfa.Models
{
    public class Offre : Produit
    {
        [Key]
        public int IdOffre { get; set; }

        public int PourcentageReduction { get; set; }

        public DateTime Delai{ get; set; }
    }
}