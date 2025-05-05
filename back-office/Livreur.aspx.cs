
using System;
using System.Linq;
using pfa.Models;

namespace pfa
{
    public partial class Livreur : System.Web.UI.Page
    {
        livraisonContext db = new livraisonContext();

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                ChargerLivreurs();
            }
        }

        private void ChargerLivreurs()
        {
            GvLivreur.DataSource = db.Livreurs.ToList();
            GvLivreur.DataBind();
        }

        protected void GvLivreur_SelectedIndexChanged(object sender, EventArgs e)
        {
            int id = Convert.ToInt32(GvLivreur.SelectedValue);
            var liv = db.Livreurs.Find(id);
            if (liv != null)
            {
                TbIdLivreur.Text = liv.IdLivreur.ToString();
                TbNom.Text = liv.Nom;
                TbPrenom.Text = liv.Prenom;
                TbCIN.Text = liv.CIN;
                TbAdresse.Text = liv.Adresse;
                TbNbrCommande.Text = liv.NbrCommande.ToString();
            }
        }

        protected void BtnAdd_Click(object sender, EventArgs e)
        {
            var liv = new pfa.Models.Livreur
            {
                Nom = TbNom.Text,
                Prenom = TbPrenom.Text,
                CIN = TbCIN.Text,
                Adresse = TbAdresse.Text,
                NbrCommande = int.Parse(TbNbrCommande.Text),
            };
            db.Livreurs.Add(liv);
            db.SaveChanges();
            ChargerLivreurs();
        }

        protected void BtnUpdate_Click(object sender, EventArgs e)
        {
            int id = int.Parse(TbIdLivreur.Text);
            var liv = db.Livreurs.Find(id);
            if (liv != null)
            {
                liv.Nom = TbNom.Text;
                liv.Prenom = TbPrenom.Text;
                liv.CIN = TbCIN.Text;
                liv.Adresse = TbAdresse.Text;
                liv.NbrCommande = int.Parse(TbNbrCommande.Text);
                db.SaveChanges();
                ChargerLivreurs();
            }
        }

        protected void BtnDelete_Click(object sender, EventArgs e)
        {
            int id = int.Parse(TbIdLivreur.Text);
            var liv = db.Livreurs.Find(id);
            if (liv != null)
            {
                db.Livreurs.Remove(liv);
                db.SaveChanges();
                ChargerLivreurs();
            }
        }
    }
}
