<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Login.aspx.cs" Inherits="pfa.Login" %>

<!DOCTYPE html>
<html>
<head runat="server">
    <title>Authentification</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
</head>
<body>
    <form id="form1" runat="server">
        <div class="container mt-5">
            <div class="row justify-content-center">
                <div class="col-md-4">
                    <div class="card shadow">
                        <div class="card-header text-white bg-primary">
                            <h4 class="mb-0">Connexion</h4>
                        </div>
                        <div class="card-body">
                            <div class="mb-3">
                                <label for="TbUsername" class="form-label">Nom d'utilisateur</label>
                                <asp:TextBox ID="TbUsername" runat="server" CssClass="form-control" />
                            </div>
                            <div class="mb-3">
                                <label for="TbPassword" class="form-label">Mot de passe</label>
                                <asp:TextBox ID="TbPassword" runat="server" TextMode="Password" CssClass="form-control"  />
                            </div>
                            <asp:Button ID="BtnLogin" runat="server" CssClass="btn btn-primary w-100" Text="Se connecter" OnClick="BtnLogin_Click" />
                            <br /><br />
                            <asp:Label ID="LblMessage" runat="server" ForeColor="Red" />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</body>
</html>
