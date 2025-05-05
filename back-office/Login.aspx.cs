using System;
using System.Web;

namespace pfa
{
    public partial class Login : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            // Pour déconnecter si déjà loggé
            Session.Clear();
        }

        protected void BtnLogin_Click(object sender, EventArgs e)
        {
            string username = TbUsername.Text.Trim();
            string password = TbPassword.Text.Trim();

            if (username == "admin" && password == "admin")
            {
                // Crée une session
                Session["User"] = "admin";

                // Redirige vers le menu principal
                Response.Redirect("Produit.aspx");
            }
            else
            {
                LblMessage.Text = "Identifiants incorrects.";
            }
        }
    }
}
