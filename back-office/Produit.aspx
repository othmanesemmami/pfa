<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Produit.aspx.cs" Inherits="pfa.Produit" %>

<!DOCTYPE html>
<html lang="fr">
<head runat="server">
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Gestion des Produits</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet" />
    <!-- Font Awesome pour les icônes -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet" />

    <style>
        #wrapper {
            display: flex;
            flex-direction: row;
        }

        #sidebar-wrapper {
            width: 250px;
            min-height: 100vh;
            background-color: #343a40;
        }

        #sidebar-wrapper .list-group-item {
            background-color: #343a40;
            color: white;
        }

        #sidebar-wrapper .list-group-item:hover {
            background-color: #495057;
        }

        #page-content-wrapper {
            flex-grow: 1;
        }

        #wrapper.toggled #sidebar-wrapper {
            margin-left: -250px;
        }

        @media (max-width: 768px) {
            #sidebar-wrapper {
                margin-left: -250px;
            }

            #wrapper.toggled #sidebar-wrapper {
                margin-left: 0;
            }
        }

        .form-container {
            background-color: #f8f9fa;
            border-radius: 10px;
            padding: 20px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
        }

        .card-header {
            background-color: #212529;
            color: white;
        }

        .table-container {
            overflow-x: auto;
        }
    </style>
</head>

<body>
    <form id="form1" runat="server">
        <div class="d-flex" id="wrapper">
            <!-- Sidebar -->
            <div class="bg-dark border-end" id="sidebar-wrapper">
                <div class="sidebar-heading text-white px-3 py-4 fw-bold">
                    <i class="fas fa-cubes me-2"></i>Gestion Produits
                </div>
                <div class="list-group list-group-flush">
                    <a href="Dashboard.aspx" class="list-group-item list-group-item-action">Dashboard</a>
                    <a href="Produit.aspx" class="list-group-item list-group-item-action active">Produits</a>
                    <a href="Livreur.aspx" class="list-group-item list-group-item-action">Livreurs</a>
                    <a href="Offre.aspx" class="list-group-item list-group-item-action">Offres</a>
                </div>
            </div>

            <!-- Page Content -->
            <div id="page-content-wrapper">
                <!-- Navigation avec bouton de déconnexion -->
                <nav class="navbar navbar-expand-lg navbar-light bg-light border-bottom px-4">
                    <button class="btn btn-primary" type="button" id="menu-toggle">
                        <i class="fas fa-bars"></i>
                    </button>
                    <span class="ms-3 fw-bold">Gestion des Produits</span>
                    <div class="ms-auto">
                        <asp:Button ID="BtnLogout" runat="server" CssClass="btn btn-danger" Text="Déconnecter" OnClick="BtnLogout_Click" />
                    </div>
                </nav>

                <div class="container-fluid py-4">
                    <div class="row mb-4">
                        <div class="col">
                            <h1 class="display-6 fw-bold text-primary">
                                <i class="fas fa-boxes me-2"></i>Liste des Produits
                            </h1>
                            <hr class="my-4" />
                        </div>
                    </div>

                    <!-- Tableau des produits -->
                    <div class="card mb-4">
                        <div class="card-header d-flex justify-content-between align-items-center">
                            <h5 class="mb-0"><i class="fas fa-table me-2"></i>Produits enregistrés</h5>
                        </div>
                        <div class="card-body table-container">
                            <asp:GridView ID="GvProduit" runat="server" AutoGenerateColumns="False"
                                OnSelectedIndexChanged="GvProduit_SelectedIndexChanged"
                                AutoGenerateSelectButton="True" DataKeyNames="IdProduit"
                                CssClass="table table-striped table-hover table-bordered">
                                <Columns>
                                    <asp:BoundField DataField="IdProduit" HeaderText="ID" />
                                    <asp:BoundField DataField="Categorie" HeaderText="Catégorie" />
                                    <asp:BoundField DataField="Quantite" HeaderText="Quantité" />
                                    <asp:BoundField DataField="Prix" HeaderText="Prix" />
                                    <asp:BoundField DataField="Description" HeaderText="Description" />
                                    <asp:TemplateField HeaderText="Image">
                                        <ItemTemplate>
                                            <img src='<%# Eval("Image") %>' style="max-width: 100px;" />
                                        </ItemTemplate>
                                    </asp:TemplateField>
                                </Columns>
                                <HeaderStyle CssClass="table-dark" />
                                <SelectedRowStyle CssClass="table-primary" />
                            </asp:GridView>
                        </div>
                    </div>

                    <!-- Formulaire de gestion -->
                    <div class="card form-container">
                        <div class="card-header">
                            <h5 class="mb-0"><i class="fas fa-edit me-2"></i>Détails du Produit</h5>
                        </div>
                        <div class="card-body">
                            <div class="row g-3">
                                <div class="col-md-6">
                                    <div class="form-floating mb-3">
                                        <asp:TextBox ID="TbIdProduit" runat="server" ReadOnly="true" CssClass="form-control" placeholder="ID" />
                                        <label for="TbIdProduit">ID</label>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-floating mb-3">
                                        <asp:DropDownList ID="DdCategorie" runat="server" CssClass="form-select">
                                            <asp:ListItem Text="Alimentation" Value="alimentation" />
                                            <asp:ListItem Text="Hygiène" Value="hygiene" />
                                            <asp:ListItem Text="Cadeau" Value="cadeau" />
                                            <asp:ListItem Text="Vêtement" Value="vetement" />
                                            <asp:ListItem Text="Électronique" Value="electronique" />
                                        </asp:DropDownList>
                                        <label for="DdCategorie">Catégorie</label>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-floating mb-3">
                                        <asp:TextBox ID="TbQuantite" runat="server" CssClass="form-control" placeholder="Quantité" TextMode="Number" />
                                        <label for="TbQuantite">Quantité</label>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-floating mb-3">
                                        <asp:TextBox ID="TbPrix" runat="server" CssClass="form-control" placeholder="Prix" TextMode="Number" />
                                        <label for="TbPrix">Prix</label>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <div class="form-floating mb-3">
                                        <asp:TextBox ID="TbDescription" runat="server" CssClass="form-control" placeholder="Description" TextMode="MultiLine" Rows="3" style="height: 100px" />
                                        <label for="TbDescription">Description</label>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <div class="form-floating mb-3">
                                        <asp:Image ID="ImgProduit" runat="server" Style="max-width: 200px;" /><br />
                                        <asp:Label Text="Nouvelle image (upload):" runat="server" CssClass="mt-2" />
                                        <asp:FileUpload ID="FileUploadImage" runat="server" CssClass="form-control" />
                                    </div>
                                </div>
                                <div class="col-12 text-center mt-4">
                                    <asp:Button ID="BtnAddProduit" Text="Ajouter" runat="server" OnClick="BtnAddProduit_Click" CssClass="btn btn-success btn-lg me-2" />
                                    <asp:Button ID="BtnUpdateProduit" Text="Modifier" runat="server" OnClick="BtnUpdateProduit_Click" CssClass="btn btn-primary btn-lg me-2" />
                                    <asp:Button ID="BtnDeleteProduit" Text="Supprimer" runat="server" OnClick="BtnDeleteProduit_Click" CssClass="btn btn-danger btn-lg"
                                        OnClientClick="return confirm('Êtes-vous sûr de vouloir supprimer ce produit?');" />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>

    <!-- Bootstrap Bundle avec Popper -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>

    <!-- Script Toggle Sidebar -->
    <script>
        document.getElementById("menu-toggle").addEventListener("click", function () {
            document.getElementById("wrapper").classList.toggle("toggled");
        });
    </script>
</body>
</html>
