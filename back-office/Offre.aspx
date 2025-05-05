<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Offre.aspx.cs" Inherits="pfa.Offre" %>

<!DOCTYPE html>
<html lang="fr">
<head runat="server">
    <meta charset="utf-8" />
    <title>Gestion des Offres</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />

    <!-- Bootstrap 5 -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet" />
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
                    <i class="fas fa-percent me-2"></i>Gestion Offres
                </div>
                <div class="list-group list-group-flush">
                    <a href="Dashboard.aspx" class="list-group-item list-group-item-action">Dashboard</a>
                    <a href="Produit.aspx" class="list-group-item list-group-item-action">Produits</a>
                    <a href="Livreur.aspx" class="list-group-item list-group-item-action">Livreurs</a>
                    <a href="Offre.aspx" class="list-group-item list-group-item-action active">Offres</a>
                </div>
            </div>

            <!-- Page Content -->
            <div id="page-content-wrapper">
                <nav class="navbar navbar-expand-lg navbar-light bg-light border-bottom px-4">
                    <button class="btn btn-primary" type="button" id="menu-toggle">
                        <i class="fas fa-bars"></i>
                    </button>
                    <span class="ms-3 fw-bold">Gestion des Offres</span>
                </nav>

                <div class="container-fluid py-4">
                    <h1 class="display-6 text-primary"><i class="fas fa-tags me-2"></i>Liste des Offres</h1>
                    <hr />

                    <!-- Table des Offres -->
                    <div class="card mb-4">
                        <div class="card-header">
                            <i class="fas fa-table me-2"></i>Offres existantes
                        </div>
                        <div class="card-body table-container">
                            <asp:GridView ID="GvOffre" runat="server" AutoGenerateColumns="False"
                                OnSelectedIndexChanged="GvOffre_SelectedIndexChanged"
                                AutoGenerateSelectButton="True" DataKeyNames="IdOffre"
                                CssClass="table table-bordered table-hover">
                                <Columns>
                                    <asp:BoundField DataField="IdOffre" HeaderText="ID" />
                                    <asp:BoundField DataField="PourcentageReduction" HeaderText="Réduction (%)" />
                                    <asp:BoundField DataField="DateDebut" HeaderText="Date Début" DataFormatString="{0:dd/MM/yyyy}" />
                                    <asp:BoundField DataField="DateFin" HeaderText="Date Fin" DataFormatString="{0:dd/MM/yyyy}" />
                                </Columns>
                                <HeaderStyle CssClass="table-dark" />
                                <SelectedRowStyle CssClass="table-primary" />
                            </asp:GridView>
                        </div>
                    </div>

                    <!-- Formulaire Offre -->
                    <div class="card form-container">
                        <div class="card-header">
                            <h5><i class="fas fa-edit me-2"></i>Détails de l'offre</h5>
                        </div>
                        <div class="card-body">
                            <div class="row g-3">
                                <div class="col-md-4">
                                    <div class="form-floating">
                                        <asp:TextBox ID="TbIdOffre" runat="server" CssClass="form-control" ReadOnly="true" placeholder="ID" />
                                        <label for="TbIdOffre">ID</label>
                                    </div>
                                </div>

                                <div class="col-md-4">
                                    <div class="form-floating">
                                        <asp:TextBox ID="TbPourcentageReduction" runat="server" CssClass="form-control" placeholder="Réduction" />
                                        <label for="TbPourcentageReduction">Réduction (%)</label>
                                    </div>
                                </div>

                                <div class="col-md-4">
                                    <div class="form-floating">
                                        <asp:TextBox ID="TbDateDebut" runat="server" CssClass="form-control" TextMode="Date" />
                                        <label for="TbDateDebut">Date Début</label>
                                    </div>
                                </div>

                                <div class="col-md-4">
                                    <div class="form-floating">
                                        <asp:TextBox ID="TbDateFin" runat="server" CssClass="form-control" TextMode="Date" />
                                        <label for="TbDateFin">Date Fin</label>
                                    </div>
                                </div>

                                <div class="col-12 text-center mt-4">
                                    <asp:Button ID="BtnAddOffre" Text="Ajouter" runat="server" CssClass="btn btn-success btn-lg me-2" OnClick="BtnAddOffre_Click" />
                                    <asp:Button ID="BtnUpdateOffre" Text="Modifier" runat="server" CssClass="btn btn-primary btn-lg me-2" OnClick="BtnUpdateOffre_Click" />
                                    <asp:Button ID="BtnDeleteOffre" Text="Supprimer" runat="server" CssClass="btn btn-danger btn-lg me-2" OnClick="BtnDeleteOffre_Click" />
                                    <asp:Button ID="BtnClear" Text="Effacer" runat="server" CssClass="btn btn-secondary btn-lg" OnClick="BtnClear_Click" />
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </form>

    <!-- JS Bootstrap -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
    <script>
        document.getElementById("menu-toggle").addEventListener("click", function () {
            document.getElementById("wrapper").classList.toggle("toggled");
        });
    </script>
</body>
</html>
