using System;
using System.IO;
using System.Linq;
using pfa.Models;

namespace pfa
{
    public partial class Produit : System.Web.UI.Page
    {
        livraisonContext db = new livraisonContext();

        protected void Page_Load(object sender, EventArgs e)
        {
            // Protection : redirige vers Login si non authentifié
            if (Session["User"] == null)
                Response.Redirect("Login.aspx");

            if (!IsPostBack)
                ChargerProduits();
        }

        private void ChargerProduits()
        {
            GvProduit.DataSource = db.Produits.ToList();
            GvProduit.DataBind();
        }

        protected void GvProduit_SelectedIndexChanged(object sender, EventArgs e)
        {
            int id = Convert.ToInt32(GvProduit.SelectedValue);
            var produit = db.Produits.Find(id);
            if (produit != null)
            {
                TbIdProduit.Text = produit.IdProduit.ToString();
                DdCategorie.SelectedValue = produit.Categorie;
                TbQuantite.Text = produit.Quantite.ToString();
                TbPrix.Text = produit.Prix.ToString("0.00");
                TbDescription.Text = produit.Description;
                ImgProduit.ImageUrl = produit.Image;  // affiche l'image
            }
        }

        protected void BtnAddProduit_Click(object sender, EventArgs e)
        {
            string imagePath = "";

            if (FileUploadImage.HasFile)
            {
                string ext = Path.GetExtension(FileUploadImage.FileName).ToLower();
                if (ext == ".jpg" || ext == ".jpeg" || ext == ".png")
                {
                    string nomFichier = Guid.NewGuid().ToString() + ext;
                    string cheminRelatif = "Images/" + nomFichier;
                    string cheminPhysique = Server.MapPath("~/" + cheminRelatif);

                    // S'assurer que le dossier existe
                    if (!Directory.Exists(Path.GetDirectoryName(cheminPhysique)))
                    {
                        Directory.CreateDirectory(Path.GetDirectoryName(cheminPhysique));
                    }

                    FileUploadImage.SaveAs(cheminPhysique);
                    imagePath = Request.Url.GetLeftPart(UriPartial.Authority) + "/" + cheminRelatif;
                }
            }

            var produit = new pfa.Models.Produit
            {
                Categorie = DdCategorie.SelectedValue,
                Quantite = int.Parse(TbQuantite.Text),
                Prix = decimal.TryParse(TbPrix.Text, out decimal prix) ? prix : 0,
                Description = TbDescription.Text,
                Image = imagePath
            };

            db.Produits.Add(produit);
            db.SaveChanges();
            ChargerProduits();
        }

        protected void BtnUpdateProduit_Click(object sender, EventArgs e)
        {
            int id = int.Parse(TbIdProduit.Text);
            var produit = db.Produits.Find(id);
            if (produit != null)
            {
                produit.Categorie = DdCategorie.SelectedValue;
                produit.Quantite = int.Parse(TbQuantite.Text);
                produit.Prix = decimal.TryParse(TbPrix.Text, out decimal prix) ? prix : 0;
                produit.Description = TbDescription.Text;

                // S’il y a une nouvelle image à uploader
                if (FileUploadImage.HasFile)
                {
                    string ext = Path.GetExtension(FileUploadImage.FileName).ToLower();
                    if (ext == ".jpg" || ext == ".jpeg" || ext == ".png")
                    {
                        string nomFichier = Guid.NewGuid().ToString() + ext;
                        string cheminRelatif = "Images/" + nomFichier;
                        string cheminPhysique = Server.MapPath("~/" + cheminRelatif);

                        FileUploadImage.SaveAs(cheminPhysique);
                        produit.Image = Request.Url.GetLeftPart(UriPartial.Authority) + "/" + cheminRelatif;
                    }
                }

                db.SaveChanges();
                ChargerProduits();
            }
        }

        protected void BtnDeleteProduit_Click(object sender, EventArgs e)
        {
            int id = int.Parse(TbIdProduit.Text);
            var produit = db.Produits.Find(id);
            if (produit != null)
            {
                // Supprimer aussi le fichier image du serveur si besoin
                if (!string.IsNullOrEmpty(produit.Image))
                {
                    try
                    {
                        Uri uri = new Uri(produit.Image);
                        string cheminImage = Server.MapPath("~" + uri.AbsolutePath.Replace(Request.Url.GetLeftPart(UriPartial.Authority), ""));
                        if (File.Exists(cheminImage))
                            File.Delete(cheminImage);
                    }
                    catch (Exception ex)
                    {
                        // Gérer ou logger l’erreur de suppression
                    }
                }

                db.Produits.Remove(produit);
                db.SaveChanges();
                ChargerProduits();
            }
        }

        protected void BtnLogout_Click(object sender, EventArgs e)
        {
            // Vider la session et rediriger vers la page Login
            Session.Clear();
            Session.Abandon();
            Response.Redirect("Login.aspx");
        }

        protected void TbQuantite_TextChanged(object sender, EventArgs e)
        {
            // Éventuel traitement sur changement de quantité
        }
    }
}
