using System;
using System.Linq;
using pfa.Models;

namespace pfa
{
    public partial class Offre : System.Web.UI.Page
    {
        livraisonContext db = new livraisonContext();

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                ChargerOffres();
                ClearForm();
            }
        }

        private void ChargerOffres()
        {
            GvOffre.DataSource = db.Offres.ToList();
            GvOffre.DataBind();
        }

        private void ClearForm()
        {
            TbIdOffre.Text = string.Empty;
            TbPourcentageReduction.Text = string.Empty;
            TbDateDebut.Text = DateTime.Now.ToString("yyyy-MM-dd");
        }

        protected void GvOffre_SelectedIndexChanged(object sender, EventArgs e)
        {
            int id = Convert.ToInt32(GvOffre.SelectedValue);
            var offre = db.Offres.Find(id);

            if (offre != null)
            {
                TbIdOffre.Text = offre.IdOffre.ToString();

                TbPourcentageReduction.Text = offre.PourcentageReduction.ToString();
                TbDateDebut.Text = offre.Delai.ToString("yyyy-MM-dd");

                // Gérer d'autres champs de Produit si nécessaire
            }
        }

        protected void BtnAddOffre_Click(object sender, EventArgs e)
        {
            try
            {
                var offre = new pfa.Models.Offre
                {
                    PourcentageReduction = int.Parse(TbPourcentageReduction.Text),
                    Delai = DateTime.Parse(TbDateDebut.Text)

                    // Gérer d'autres champs de Produit si nécessaire
                };

                db.Offres.Add(offre);
                db.SaveChanges();
                ChargerOffres();
                ClearForm();
            }
            catch (Exception ex)
            {
                // Gérer les erreurs (afficher un message, etc.)
                Response.Write($"<script>alert('Erreur: {ex.Message}')</script>");
            }
        }

        protected void BtnUpdateOffre_Click(object sender, EventArgs e)
        {
            try
            {
                if (string.IsNullOrEmpty(TbIdOffre.Text))
                {
                    Response.Write("<script>alert('Veuillez sélectionner une offre à modifier')</script>");
                    return;
                }

                int id = int.Parse(TbIdOffre.Text);
                var offre = db.Offres.Find(id);

                if (offre != null)
                {

                    offre.PourcentageReduction = int.Parse(TbPourcentageReduction.Text);
                    offre.Delai = DateTime.Parse(TbDateDebut.Text);

                    // Gérer d'autres champs de Produit si nécessaire

                    db.SaveChanges();
                    ChargerOffres();
                    ClearForm();
                }
            }
            catch (Exception ex)
            {
                Response.Write($"<script>alert('Erreur: {ex.Message}')</script>");
            }
        }

        protected void BtnDeleteOffre_Click(object sender, EventArgs e)
        {
            try
            {
                if (string.IsNullOrEmpty(TbIdOffre.Text))
                {
                    Response.Write("<script>alert('Veuillez sélectionner une offre à supprimer')</script>");
                    return;
                }

                int id = int.Parse(TbIdOffre.Text);
                var offre = db.Offres.Find(id);

                if (offre != null)
                {
                    db.Offres.Remove(offre);
                    db.SaveChanges();
                    ChargerOffres();
                    ClearForm();
                }
            }
            catch (Exception ex)
            {
                Response.Write($"<script>alert('Erreur: {ex.Message}')</script>");
            }
        }

        protected void BtnClear_Click(object sender, EventArgs e)
        {
            ClearForm();
        }
    }
}