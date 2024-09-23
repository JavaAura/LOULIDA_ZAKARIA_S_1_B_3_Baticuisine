import main.com.baticuisine.controller.ClientController;
import main.com.baticuisine.controller.ComponentController;
import main.com.baticuisine.controller.ProjectController;
import main.com.baticuisine.model.Client;
import main.com.baticuisine.model.Project;

import java.util.Scanner;

public class BatiCuisineConsoleUI {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ClientController clientController = new ClientController();
    private static final ProjectController projectController = new ProjectController();
    private static final ComponentController componentController = new ComponentController();


    public static void main(String[] args) {
        runMainMenu();
    }

    private static void runMainMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("=== Bienvenue dans l'application Bati-Cuisine ===");
            System.out.println("1. Créer un nouveau projet");
            System.out.println("2. Afficher les projets existants");
            System.out.println("3. Calculer le coût d'un projet");
            System.out.println("4. Quitter");
            System.out.print("Choisissez une option : ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    createNewProject();
                    break;
                case 2:
                    displayExistingProjects();
                    break;
                case 3:
                    calculateProjectCost();
                    break;
                case 4:
                    exit = true;
                    System.out.println("Merci d'avoir utilisé Bati-Cuisine !");
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
            }
        }
    }

    private static void createNewProject() {
        System.out.println("--- Création d'un Nouveau Projet ---");

        // Search for an existing client or add a new one
        System.out.println("1. Chercher un client existant");
        System.out.println("2. Ajouter un nouveau client");
        System.out.print("Choisissez une option : ");
        int clientOption = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Client client;
        if (clientOption == 1) {
            System.out.print("Entrez le nom du client : ");
            String clientName = scanner.nextLine();
            client = clientController.findClientByName(clientName);
            if (client == null) {
                System.out.println("Client introuvable. Veuillez ajouter un nouveau client.");
                client = addNewClient();
            }
        } else {
            client = addNewClient();
        }

        // Create new project
        System.out.print("Entrez le nom du projet : ");
        String projectName = scanner.nextLine();
        System.out.print("Entrez la surface de la cuisine (en m²) : ");
        double surface = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        Project project = new Project(projectName, client, surface);
        projectController.createProject(project);

        // Add materials and labor
        addMaterialsToProject(project);
        addLaborToProject(project);

        System.out.println("Projet créé avec succès !");
    }

    private static Client addNewClient() {
        System.out.print("Entrez le nom du client : ");
        String clientName = scanner.nextLine();
        System.out.print("Entrez l'adresse du client : ");
        String address = scanner.nextLine();
        System.out.print("Entrez le numéro de téléphone du client : ");
        String phone = scanner.nextLine();
        System.out.print("Est-ce un professionnel ? (true/false) : ");
        boolean isProfessional = scanner.nextBoolean();
        scanner.nextLine(); // Consume newline

        Client newClient = new Client(clientName, address, phone, isProfessional);
        clientController.createClient(newClient);
        return newClient;
    }

    private static void addMaterialsToProject(Project project) {
        boolean addMoreMaterials = true;

        while (addMoreMaterials) {
            System.out.println("--- Ajout des matériaux ---");
            System.out.print("Entrez le nom du matériau : ");
            String materialName = scanner.nextLine();
            System.out.print("Entrez la quantité de ce matériau : ");
            double quantity = scanner.nextDouble();
            System.out.print("Entrez le coût unitaire du matériau : ");
            double unitCost = scanner.nextDouble();
            System.out.print("Entrez le coût de transport : ");
            double transportCost = scanner.nextDouble();
            System.out.print("Entrez le coefficient de qualité : ");
            double qualityCoefficient = scanner.nextDouble();
            scanner.nextLine(); // Consume newline

            Material material = new Material(materialName, quantity, unitCost, transportCost, qualityCoefficient);
            materialController.addMaterialToProject(project, material);

            System.out.print("Voulez-vous ajouter un autre matériau ? (y/n) : ");
            addMoreMaterials = scanner.nextLine().equalsIgnoreCase("y");
        }
    }

    private static void addLaborToProject(Project project) {
        boolean addMoreLabor = true;

        while (addMoreLabor) {
            System.out.println("--- Ajout de la main-d'œuvre ---");
            System.out.print("Entrez le type de main-d'œuvre : ");
            String laborType = scanner.nextLine();
            System.out.print("Entrez le taux horaire : ");
            double hourlyRate = scanner.nextDouble();
            System.out.print("Entrez le nombre d'heures travaillées : ");
            double hoursWorked = scanner.nextDouble();
            System.out.print("Entrez le facteur de productivité : ");
            double productivityFactor = scanner.nextDouble();
            scanner.nextLine(); // Consume newline

            Labor labor = new Labor(laborType, hourlyRate, hoursWorked, productivityFactor);
            laborController.addLaborToProject(project, labor);

            System.out.print("Voulez-vous ajouter un autre type de main-d'œuvre ? (y/n) : ");
            addMoreLabor = scanner.nextLine().equalsIgnoreCase("y");
        }
    }

    private static void displayExistingProjects() {
        System.out.println("--- Afficher les Projets Existants ---");
        projectController.getAllProjects().forEach(System.out::println);
    }

    private static void calculateProjectCost() {
        System.out.println("--- Calculer le Coût d'un Projet ---");
        System.out.print("Entrez le nom du projet à calculer : ");
        String projectName = scanner.nextLine();

        Project project = projectController.findProjectByName(projectName);
        if (project != null) {
            System.out.print("Souhaitez-vous appliquer une TVA au projet ? (y/n) : ");
            boolean applyVAT = scanner.nextLine().equalsIgnoreCase("y");
            double vatRate = 0;
            if (applyVAT) {
                System.out.print("Entrez le pourcentage de TVA (%) : ");
                vatRate = scanner.nextDouble();
                scanner.nextLine(); // Consume newline
            }

            System.out.print("Souhaitez-vous appliquer une marge bénéficiaire au projet ? (y/n) : ");
            boolean applyMargin = scanner.nextLine().equalsIgnoreCase("y");
            double marginRate = 0;
            if (applyMargin) {
                System.out.print("Entrez le pourcentage de marge bénéficiaire (%) : ");
                marginRate = scanner.nextDouble();
                scanner.nextLine(); // Consume newline
            }

            double totalCost = projectController.calculateTotalCost(project, vatRate, marginRate);
            System.out.println("--- Coût Total du Projet ---");
            System.out.println("Coût total estimé : " + totalCost + " €");
        } else {
            System.out.println("Projet introuvable.");
        }
    }
}
