using System.Data.Entity;

namespace pfa.Models
{
    public class livraisonContext : DbContext
    {
        public livraisonContext() : base("name=livraisonApp") { }

        public DbSet<Compte> Comptes { get; set; }
        public DbSet<Livreur> Livreurs { get; set; }
        public DbSet<Commande> Commandes { get; set; }
        public DbSet<Produit> Produits { get; set; }
        public DbSet<Offre> Offres { get; set; }
        public DbSet<Avis> Avises { get; set; }

        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);

            modelBuilder.Entity<Commande>()
                .HasMany(c => c.Produits)
                .WithMany(p => p.Commandes);
        }
    }
}
