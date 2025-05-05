namespace pfa.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class InitialCreate : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.Admins",
                c => new
                    {
                        IdAdmin = c.Int(nullable: false),
                        Name = c.String(nullable: false, maxLength: 100),
                        Role = c.String(),
                        IdCompte = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.IdAdmin)
                .ForeignKey("dbo.Comptes", t => t.IdAdmin)
                .Index(t => t.IdAdmin);
            
            CreateTable(
                "dbo.Comptes",
                c => new
                    {
                        IdCompte = c.Int(nullable: false, identity: true),
                        Login = c.String(nullable: false, maxLength: 100),
                        Password = c.String(nullable: false),
                    })
                .PrimaryKey(t => t.IdCompte);
            
            CreateTable(
                "dbo.Clients",
                c => new
                    {
                        Id = c.Int(nullable: false),
                        Name = c.String(nullable: false, maxLength: 100),
                        Adresse = c.String(),
                        NTel = c.String(maxLength: 20),
                        Email = c.String(),
                        IdCompte = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Comptes", t => t.Id)
                .Index(t => t.Id);
            
            CreateTable(
                "dbo.Avis",
                c => new
                    {
                        IdAvis = c.Int(nullable: false),
                        NbrEtoile = c.Int(nullable: false),
                        Commentaire = c.String(),
                        ClientId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.IdAvis)
                .ForeignKey("dbo.Clients", t => t.IdAvis)
                .Index(t => t.IdAvis);
            
            CreateTable(
                "dbo.Commandes",
                c => new
                    {
                        IdCom = c.Int(nullable: false, identity: true),
                        PrixTotale = c.Decimal(nullable: false, precision: 18, scale: 2),
                        DateCommande = c.DateTime(nullable: false),
                        Status = c.String(),
                        DateEstime = c.DateTime(nullable: false),
                        Adresse = c.String(),
                        ClientId = c.Int(nullable: false),
                        LivreurId = c.Int(nullable: false),
                        Livreur_IdLivreur = c.Int(),
                    })
                .PrimaryKey(t => t.IdCom)
                .ForeignKey("dbo.Clients", t => t.ClientId, cascadeDelete: true)
                .ForeignKey("dbo.Livreurs", t => t.Livreur_IdLivreur)
                .Index(t => t.ClientId)
                .Index(t => t.Livreur_IdLivreur);
            
            CreateTable(
                "dbo.Livreurs",
                c => new
                    {
                        IdLivreur = c.Int(nullable: false, identity: true),
                        Nom = c.String(nullable: false, maxLength: 50),
                        Prenom = c.String(maxLength: 50),
                        CIN = c.String(maxLength: 20),
                        Adresse = c.String(),
                        NbrCommande = c.Int(nullable: false),
                        AdminId = c.Int(nullable: false),
                        Admin_IdAdmin = c.Int(),
                    })
                .PrimaryKey(t => t.IdLivreur)
                .ForeignKey("dbo.Admins", t => t.Admin_IdAdmin)
                .Index(t => t.Admin_IdAdmin);
            
            CreateTable(
                "dbo.Produits",
                c => new
                    {
                        IdProduit = c.Int(nullable: false, identity: true),
                        Quantite = c.Int(nullable: false),
                        Categorie = c.String(),
                        Prix = c.Decimal(nullable: false, precision: 18, scale: 2),
                        Description = c.String(),
                        Image = c.String(),
                        AdminId = c.Int(nullable: false),
                        IdOffre = c.Int(),
                        PourcentageReduction = c.Int(),
                        DateDebut = c.DateTime(),
                        Discriminator = c.String(nullable: false, maxLength: 128),
                        Admin_IdAdmin = c.Int(),
                    })
                .PrimaryKey(t => t.IdProduit)
                .ForeignKey("dbo.Admins", t => t.Admin_IdAdmin)
                .Index(t => t.Admin_IdAdmin);
            
            CreateTable(
                "dbo.CommandeProduits",
                c => new
                    {
                        Commande_IdCom = c.Int(nullable: false),
                        Produit_IdProduit = c.Int(nullable: false),
                    })
                .PrimaryKey(t => new { t.Commande_IdCom, t.Produit_IdProduit })
                .ForeignKey("dbo.Commandes", t => t.Commande_IdCom, cascadeDelete: true)
                .ForeignKey("dbo.Produits", t => t.Produit_IdProduit, cascadeDelete: true)
                .Index(t => t.Commande_IdCom)
                .Index(t => t.Produit_IdProduit);
            
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.Admins", "IdAdmin", "dbo.Comptes");
            DropForeignKey("dbo.Clients", "Id", "dbo.Comptes");
            DropForeignKey("dbo.CommandeProduits", "Produit_IdProduit", "dbo.Produits");
            DropForeignKey("dbo.CommandeProduits", "Commande_IdCom", "dbo.Commandes");
            DropForeignKey("dbo.Produits", "Admin_IdAdmin", "dbo.Admins");
            DropForeignKey("dbo.Commandes", "Livreur_IdLivreur", "dbo.Livreurs");
            DropForeignKey("dbo.Livreurs", "Admin_IdAdmin", "dbo.Admins");
            DropForeignKey("dbo.Commandes", "ClientId", "dbo.Clients");
            DropForeignKey("dbo.Avis", "IdAvis", "dbo.Clients");
            DropIndex("dbo.CommandeProduits", new[] { "Produit_IdProduit" });
            DropIndex("dbo.CommandeProduits", new[] { "Commande_IdCom" });
            DropIndex("dbo.Produits", new[] { "Admin_IdAdmin" });
            DropIndex("dbo.Livreurs", new[] { "Admin_IdAdmin" });
            DropIndex("dbo.Commandes", new[] { "Livreur_IdLivreur" });
            DropIndex("dbo.Commandes", new[] { "ClientId" });
            DropIndex("dbo.Avis", new[] { "IdAvis" });
            DropIndex("dbo.Clients", new[] { "Id" });
            DropIndex("dbo.Admins", new[] { "IdAdmin" });
            DropTable("dbo.CommandeProduits");
            DropTable("dbo.Produits");
            DropTable("dbo.Livreurs");
            DropTable("dbo.Commandes");
            DropTable("dbo.Avis");
            DropTable("dbo.Clients");
            DropTable("dbo.Comptes");
            DropTable("dbo.Admins");
        }
    }
}
