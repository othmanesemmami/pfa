namespace pfa.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class RemoveAdminClient : DbMigration
    {
        public override void Up()
        {
            DropForeignKey("dbo.Avis", "IdAvis", "dbo.Clients");
            DropForeignKey("dbo.Commandes", "ClientId", "dbo.Clients");
            DropForeignKey("dbo.Livreurs", "Admin_IdAdmin", "dbo.Admins");
            DropForeignKey("dbo.Produits", "Admin_IdAdmin", "dbo.Admins");
            DropForeignKey("dbo.Clients", "Id", "dbo.Comptes");
            DropForeignKey("dbo.Admins", "IdAdmin", "dbo.Comptes");
            DropIndex("dbo.Admins", new[] { "IdAdmin" });
            DropIndex("dbo.Clients", new[] { "Id" });
            DropIndex("dbo.Avis", new[] { "IdAvis" });
            DropIndex("dbo.Commandes", new[] { "ClientId" });
            DropIndex("dbo.Livreurs", new[] { "Admin_IdAdmin" });
            DropIndex("dbo.Produits", new[] { "Admin_IdAdmin" });
            DropPrimaryKey("dbo.Avis");
            AddColumn("dbo.Avis", "CompteId", c => c.Int(nullable: false));
            AddColumn("dbo.Avis", "Compte_IdCompte", c => c.Int());
            AddColumn("dbo.Commandes", "CompteId", c => c.Int(nullable: false));
            AddColumn("dbo.Commandes", "Compte_IdCompte", c => c.Int());
            AddColumn("dbo.Produits", "Delai", c => c.DateTime());
            AlterColumn("dbo.Avis", "IdAvis", c => c.Int(nullable: false, identity: true));
            AddPrimaryKey("dbo.Avis", "IdAvis");
            CreateIndex("dbo.Avis", "Compte_IdCompte");
            CreateIndex("dbo.Commandes", "Compte_IdCompte");
            AddForeignKey("dbo.Avis", "Compte_IdCompte", "dbo.Comptes", "IdCompte");
            AddForeignKey("dbo.Commandes", "Compte_IdCompte", "dbo.Comptes", "IdCompte");
            DropColumn("dbo.Avis", "ClientId");
            DropColumn("dbo.Commandes", "ClientId");
            DropColumn("dbo.Livreurs", "AdminId");
            DropColumn("dbo.Livreurs", "Admin_IdAdmin");
            DropColumn("dbo.Produits", "AdminId");
            DropColumn("dbo.Produits", "DateDebut");
            DropColumn("dbo.Produits", "Admin_IdAdmin");
            DropTable("dbo.Admins");
            DropTable("dbo.Clients");
        }
        
        public override void Down()
        {
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
                .PrimaryKey(t => t.Id);
            
            CreateTable(
                "dbo.Admins",
                c => new
                    {
                        IdAdmin = c.Int(nullable: false),
                        Name = c.String(nullable: false, maxLength: 100),
                        Role = c.String(),
                        IdCompte = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.IdAdmin);
            
            AddColumn("dbo.Produits", "Admin_IdAdmin", c => c.Int());
            AddColumn("dbo.Produits", "DateDebut", c => c.DateTime());
            AddColumn("dbo.Produits", "AdminId", c => c.Int(nullable: false));
            AddColumn("dbo.Livreurs", "Admin_IdAdmin", c => c.Int());
            AddColumn("dbo.Livreurs", "AdminId", c => c.Int(nullable: false));
            AddColumn("dbo.Commandes", "ClientId", c => c.Int(nullable: false));
            AddColumn("dbo.Avis", "ClientId", c => c.Int(nullable: false));
            DropForeignKey("dbo.Commandes", "Compte_IdCompte", "dbo.Comptes");
            DropForeignKey("dbo.Avis", "Compte_IdCompte", "dbo.Comptes");
            DropIndex("dbo.Commandes", new[] { "Compte_IdCompte" });
            DropIndex("dbo.Avis", new[] { "Compte_IdCompte" });
            DropPrimaryKey("dbo.Avis");
            AlterColumn("dbo.Avis", "IdAvis", c => c.Int(nullable: false));
            DropColumn("dbo.Produits", "Delai");
            DropColumn("dbo.Commandes", "Compte_IdCompte");
            DropColumn("dbo.Commandes", "CompteId");
            DropColumn("dbo.Avis", "Compte_IdCompte");
            DropColumn("dbo.Avis", "CompteId");
            AddPrimaryKey("dbo.Avis", "IdAvis");
            CreateIndex("dbo.Produits", "Admin_IdAdmin");
            CreateIndex("dbo.Livreurs", "Admin_IdAdmin");
            CreateIndex("dbo.Commandes", "ClientId");
            CreateIndex("dbo.Avis", "IdAvis");
            CreateIndex("dbo.Clients", "Id");
            CreateIndex("dbo.Admins", "IdAdmin");
            AddForeignKey("dbo.Admins", "IdAdmin", "dbo.Comptes", "IdCompte");
            AddForeignKey("dbo.Clients", "Id", "dbo.Comptes", "IdCompte");
            AddForeignKey("dbo.Produits", "Admin_IdAdmin", "dbo.Admins", "IdAdmin");
            AddForeignKey("dbo.Livreurs", "Admin_IdAdmin", "dbo.Admins", "IdAdmin");
            AddForeignKey("dbo.Commandes", "ClientId", "dbo.Clients", "Id", cascadeDelete: true);
            AddForeignKey("dbo.Avis", "IdAvis", "dbo.Clients", "Id");
        }
    }
}
