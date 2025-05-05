<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Livreur.aspx.cs" Inherits="pfa.Livreur" %>

<!DOCTYPE html>
<html lang="fr">
<head runat="server">
    <meta charset="utf-8" />
    <title>Gestion des Livreurs</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />

    <!-- Bootstrap CSS -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet" />
    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet" />

    <style>
        #wrapper {
            display: flex;
        }

        #sidebar-wrapper {
            width: 250px;
            background-color: #343a40;
            min-height: 100vh;
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

        .form-container {
            background-color: #f8f9fa;
            border-radius: 10px;
            padding: 20px;
            box-shadow: 0 0 15px rgba(0,0,0,0.1);
        }

        .card-header {
            background-color: #212529;
            color: white;
        }

        .table-container {
            overflow-x: auto;
        }

        @media (max-width: 768px) {
            #sidebar-wrapper {
                margin-left: -250px;
            }

            #wrapper.toggled #sidebar-wrapper {
                margin-left: 0;
            }
        }
    </style>
</head>
<body>
    <form id="form1" runat="server">
        <div id="wrapper" class="d-flex">
            <!-- Sidebar -->
            <div id="sidebar-wrapper">
                <div class="sidebar-heading text-white px-3 py-4 fw-bold">
                    <i class="fas fa-truck me-2"></i>Gestion Livreurs
                </div>
                <div class="list-group list-group-flush">
    <a href="Dashboard.aspx" class="list-group-item list-group-item-action">Dashboard</a>
    <a href="Produit.aspx" class="list-group-item list-group-item-action active">Produits</a>
    <a href="Livreur.aspx" class="list-group-item list-group-item-action">Livreurs</a>
    <a href="Offre.aspx" class="list-group-item list-group-item-action">Offres</a>
</div>
            </div>

            <!-- Page content -->
            <div id="page-content-wrapper">
                <nav class="navbar navbar-expand-lg navbar-light bg-light border-bottom px-4">
                    <button class="btn btn-primary" type="button" id="menu-toggle">
                        <i class="fas fa-bars"></i>
                    </button>
                    <span class="ms-3 fw-bold">Gestion des Livreurs</span>
                </nav>

                <div class="container-fluid py-4">
                    <div class="row mb-4">
                        <div class="col">
                            <h1 class="display-6 fw-bold text-primary">
                                <i class="fas fa-truck me-2"></i>Liste des Livreurs
                            </h1>
                            <hr />
                        </div>
                    </div>

                    <!-- Tableau des livreurs -->
                    <div class="card mb-4">
                        <div class="card-header">
                            <i class="fas fa-table me-2"></i>Livreurs enregistrés
                        </div>
                        <div class="card-body table-container">
                            <asp:GridView ID="GvLivreur" runat="server" AutoGenerateColumns="False"
                                OnSelectedIndexChanged="GvLivreur_SelectedIndexChanged"
                                AutoGenerateSelectButton="True" DataKeyNames="IdLivreur"
                                CssClass="table table-bordered table-hover">
                                <Columns>
                                    <asp:BoundField DataField="IdLivreur" HeaderText="ID" />
                                    <asp:BoundField DataField="Nom" HeaderText="Nom" />
                                    <asp:BoundField DataField="Prenom" HeaderText="Prénom" />
                                    <asp:BoundField DataField="CIN" HeaderText="CIN" />
                                    <asp:BoundField DataField="Adresse" HeaderText="Adresse" />
                                    <asp:BoundField DataField="NbrCommande" HeaderText="Commandes" />
                                </Columns>
                                <HeaderStyle CssClass="table-dark" />
                                <SelectedRowStyle CssClass="table-primary" />
                            </asp:GridView>
                        </div>
                    </div>

                    <!-- Formulaire de gestion -->
                    <div class="card form-container">
                        <div class="card-header">
                            <h5><i class="fas fa-edit me-2"></i>Détails du Livreur</h5>
                        </div>
                        <div class="card-body">
                            <div class="row g-3">
                                <div class="col-md-6">
                                    <div class="form-floating mb-3">
                                        <asp:TextBox ID="TbIdLivreur" runat="server" ReadOnly="true" CssClass="form-control" placeholder="ID" />
                                        <label for="TbIdLivreur">ID</label>
                                    </div>
                                </div>

                                <div class="col-md-6">
                                    <div class="form-floating mb-3">
                                        <asp:TextBox ID="TbNom" runat="server" CssClass="form-control" placeholder="Nom" />
                                        <label for="TbNom">Nom</label>
                                    </div>
                                </div>

                                <div class="col-md-6">
                                    <div class="form-floating mb-3">
                                        <asp:TextBox ID="TbPrenom" runat="server" CssClass="form-control" placeholder="Prénom" />
                                        <label for="TbPrenom">Prénom</label>
                                    </div>
                                </div>

                                <div class="col-md-6">
                                    <div class="form-floating mb-3">
                                        <asp:TextBox ID="TbCIN" runat="server" CssClass="form-control" placeholder="CIN" />
                                        <label for="TbCIN">CIN</label>
                                    </div>
                                </div>

                                <div class="col-12">
                                    <div class="form-floating mb-3">
                                        <asp:TextBox ID="TbAdresse" runat="server" CssClass="form-control" placeholder="Adresse" />
                                        <label for="TbAdresse">Adresse</label>
                                    </div>
                                </div>

                                <div class="col-md-6">
                                    <div class="form-floating mb-3">
                                        <asp:TextBox ID="TbNbrCommande" runat="server" CssClass="form-control" TextMode="Number" placeholder="Commandes" />
                                        <label for="TbNbrCommande">Nombre de Commandes</label>
                                    </div>
                                </div>

                                <div class="col-12 text-center mt-4">
                                    <asp:Button ID="BtnAdd" Text="Ajouter" runat="server" CssClass="btn btn-success btn-lg me-2" OnClick="BtnAdd_Click" />
                                    <asp:Button ID="BtnUpdate" Text="Modifier" runat="server" CssClass="btn btn-primary btn-lg me-2" OnClick="BtnUpdate_Click" />
                                    <asp:Button ID="BtnDelete" Text="Supprimer" runat="server" CssClass="btn btn-danger btn-lg"
                                        OnClientClick="return confirm('Confirmez-vous la suppression du livreur ?');" OnClick="BtnDelete_Click" />
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </form>

    <!-- Bootstrap Bundle JS -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
    <script>
        document.getElementById("menu-toggle").addEventListener("click", function () {
            document.getElementById("wrapper").classList.toggle("toggled");
        });
    </script>
</body>
</html>
