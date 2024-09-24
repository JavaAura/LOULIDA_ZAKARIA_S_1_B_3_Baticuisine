import main.com.baticuisine.controller.ClientController;
import main.com.baticuisine.controller.ComponentController;
import main.com.baticuisine.controller.EstimateController;
import main.com.baticuisine.controller.ProjectController;
import main.com.baticuisine.model.*;
import main.com.baticuisine.utils.InputValidator;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;


public class BatiCuisineConsoleUI {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ClientController clientController = new ClientController();
    private static final ProjectController projectController = new ProjectController();
    private static final ComponentController componentController = new ComponentController();
    private static final EstimateController estimateController = new EstimateController();
private  static final InputValidator inputValidator =new InputValidator(scanner);
    public static void main(String[] args) {
        runMainMenu();
    }

    private static void runMainMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("=== Bienvenue dans l'application de gestion des projets de rénovation de cuisines ===");
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
                    Project project = searchProject();

                    if (project != null) {
                        displayproject(project);
                        checkIfAlreadyHaveAnEstimate(project);
                    } else {
                        System.out.println("Projet non trouvé.");
                    }
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

        Client client = searchOrCreateClient();
        Project project = new Project();

        String projectName = inputValidator.validateStringInput("Entrez le nom du projet : ");
        double surface = inputValidator.validateDoubleInput("Entrez la surface de la cuisine (en m²) : ");

        project.setProjectName(projectName);
        project.setSurface(surface);
        project.setClient(client);
        project.setStatus(ProjectStatus.IN_PROGRESS);

        List<Component> materials = addMaterialsToProject(project);
        List<Component> labors = addLaborToProject(project);

        List<Component> allComponents = new ArrayList<>();
        allComponents.addAll(labors);
        allComponents.addAll(materials);

        double vatRate = 0;
        System.out.print("Souhaitez-vous appliquer une TVA ? (y/n) : ");
        boolean applyVAT = scanner.nextLine().equalsIgnoreCase("y");
        if (applyVAT) {
            vatRate = inputValidator.validatePercentageInput("Entrez le pourcentage de TVA (%) : ");

            final double finalVatRate = vatRate;
            allComponents.forEach(component -> component.setVatRate(finalVatRate));

        }

        double marginRate = 0;
        System.out.print("Souhaitez-vous appliquer une marge bénéficiaire ? (y/n) : ");
        boolean applyMargin = scanner.nextLine().equalsIgnoreCase("y");
        if (applyMargin) {
            marginRate = inputValidator.validatePercentageInput("Entrez le pourcentage de marge bénéficiaire (%) : ");
            project.setProfitMargin(marginRate);
        }

        project.setComponents(allComponents);
        projectController.createProject(project);
        componentController.createComponents(allComponents);
        displayproject(project);

        madeEstimate(project);
    }

    private static void checkIfAlreadyHaveAnEstimate(Project project) {
        Estimate estimate = estimateController.getEstimateById(project.getId());

        if (estimate != null) {  // Ensure estimate exists before proceeding
            System.out.println("You already have an estimate: " + estimate.toString());
            boolean choice = inputValidator.validateYesNoInput("Do you want to delete this estimate and create a new one? (y/n):");

            if (choice) {
                estimateController.deleteEstimate(estimate.getId());
                madeEstimate(project);  // Changed to 'makeEstimate' to match correct English usage
            }
        }else{
            madeEstimate(project);
        }
    }

    private static void madeEstimate(Project project){

        LocalDate emissionDate = inputValidator.validateDateInput("Entrez la date d'emission du devis (AAAA-MM-JJ) :");

        LocalDate validityDate =inputValidator.validateDateInput("Entrez la date de validité du devis (AAAA-MM-JJ) : ");

        boolean saveestimate = inputValidator.validateYesNoInput(" Souhaitez-vous enregistrer le devis ? (y/n): ");

        if (saveestimate){
            Estimate estimate = new Estimate();
            estimate.setEstimatedAmount(project.calculateTotalCostWithVAT_WithProfitmargin());
            estimate.setIssueDate(emissionDate);
            estimate.setValidityDate(validityDate);
            estimate.setProject_id(project.getId());
            estimateController.createEstimate(estimate);

            System.out.println("Devis enregistré avec succès !");
        }else {
            System.out.println("Devis refusé  !");}
    }


    private static Client searchOrCreateClient() {
        System.out.println("--- Recherche de client ---");
        System.out.println("1. Chercher un client existant");
        System.out.println("2. Ajouter un nouveau client");
        System.out.print("Choisissez une option : ");
        int clientOption = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (clientOption == 1) {

            String clientName = inputValidator.validateStringInput("Enter client Name : ");
            Client client = clientController.getClientByName(clientName);
            if (client != null) {
                System.out.println("Client trouvé : " + client.getName());
               Boolean choice = inputValidator.validateYesNoInput("Souhaitez-vous continuer avec ce client ? (y/n) :");

                if (choice) {
                    return client;
                }
            } else {
                System.out.println("Client non trouvé.");
            }
        }
        return addNewClient();
    }

    private static Client addNewClient() {
        System.out.println("--- Ajout d'un nouveau client ---");
        String clientName = inputValidator.validateStringInput("Entrez le nom du client : ");
        String address = inputValidator.validateStringInput("Entrez l'adresse du client : ");
        String phone = inputValidator.validatePhoneNumberInput("Entrez le numéro de téléphone du client : ");
        boolean isProfessional = inputValidator.validateBooleanInput("Est-ce un professionnel ? (true/false) : ");

        Client newClient = new Client();
        newClient.setName(clientName);
        newClient.setPhone(phone);
        newClient.setAddress(address);
        newClient.setProfessional(isProfessional);

        clientController.createClient(newClient);
        return newClient;
    }

    private static List<Component> addMaterialsToProject(Project project) {
        boolean addMoreMaterials = true;
        List<Component> components = new ArrayList<>();

        while (addMoreMaterials) {
            System.out.println("--- Ajout des matériaux ---");
            String materialName = inputValidator.validateStringInput("Entrez le nom du matériau : ");
            double quantity = inputValidator.validateDoubleInput("Entrez la quantité de ce matériau : ");
            double unitCost = inputValidator.validateDoubleInput("Entrez le coût unitaire du matériau : ");
            double transportCost = inputValidator.validateDoubleInput("Entrez le coût de transport : ");
            double qualityCoefficient = inputValidator.validateDoubleInput("Entrez le coefficient de qualité : ");

            Material material = new Material();
            material.setQuantity(quantity);
            material.setProject_id(project.getId());
            material.setQualityCoefficient(qualityCoefficient);
            material.setUnitCost(unitCost);
            material.setName(materialName);
            material.setTransportCost(transportCost);
            material.setComponentType(componentType.Materialtype);

            components.add(material);


            addMoreMaterials = inputValidator.validateYesNoInput("Voulez-vous ajouter un autre matériau ? (y/n) :");
        }
        return components;
    }

    private static List<Component> addLaborToProject(Project project) {
        boolean addMoreLabor = true;
        List<Component> components = new ArrayList<>();

        while (addMoreLabor) {
            System.out.println("--- Ajout de la main-d'œuvre ---");
            String laborType = inputValidator.validateStringInput("Entrez le type de main-d'œuvre : ");
            double hourlyRate = inputValidator.validateDoubleInput("Entrez le taux horaire : ");
            double hoursWorked = inputValidator.validateDoubleInput("Entrez le nombre d'heures travaillées : ");
            double productivityFactor = inputValidator.validateDoubleInput("Entrez le facteur de productivité : ");

            Labor labor = new Labor();
            labor.setName(laborType);
            labor.setHoursWorked(hoursWorked);
            labor.setProductivityFactor(productivityFactor);
            labor.setComponentType(componentType.Labortype);
            labor.setProject_id(project.getId());
            labor.setHourlyRate(hourlyRate);

            components.add(labor);

            addMoreLabor = inputValidator.validateYesNoInput("Voulez-vous ajouter un autre matériau ? (y/n) :");
        }
        return components;
    }




    private static void displayExistingProjects() {
        System.out.println("--- Liste des Projets ---");
        List<Project> projects = projectController.getAllProjects();

        if (projects.isEmpty()) {
            System.out.println("Aucun projet trouvé.");
        } else {

            projects.forEach(project -> displayproject(project));
        }
    }


    private static void displayproject(Project project){

        System.out.println("---------------------Project details----------------------------");

        System.out.println("Nom du projet  : " +project.getProjectName());
        System.out.println("Client : " +project.getClient().getName());
        System.out.println("Phone : " +project.getClient().getPhone());
        System.out.println("Surface : " +project.getSurface());
        System.out.println("--- Détail des Coûts ---");

        System.out.println("\n--- Materials ---");
        project.getComponents().stream()
                .filter(component -> component instanceof Material)
                .forEach(material -> {
                    Material mat = (Material) material;
                    System.out.println("Nom : " + mat.getName());
                    System.out.println("Coût unitaire : " + mat.getUnitCost());
                    System.out.println("Quantité : " + mat.getQuantity());
                    System.out.println("Coût transport : " + mat.getTransportCost());
                    System.out.println("Coefficient qualité : " + mat.getQualityCoefficient());
                    System.out.println("Coût total des matériaux avant TVA : " + mat.calculateCostWithoutVAT());
                    System.out.println("Coût total des matériaux apres ("+ mat.getVatRate()+" %)TVA : " + mat.calculateCostWithVAT());
                    System.out.println("---");
                });


        System.out.println("\n--- Labor ---");
        project.getComponents().stream()
                .filter(component -> component instanceof Labor)
                .forEach(labor -> {
                    Labor lab = (Labor) labor;
                    System.out.println("Nom : " + lab.getName());
                    System.out.println("Taux horaire : " + lab.getHourlyRate());
                    System.out.println("Heures travaillées : " + lab.getHoursWorked());
                    System.out.println("Facteur de productivité : " + lab.getProductivityFactor());
                    System.out.println("Coût total de la main-d'oeuvre avant TVA : " + lab.calculateCostWithoutVAT());
                    System.out.println("Coût total de la main-d'oeuvre apres ("+ lab.getVatRate()+") TVA : " + lab.calculateCostWithVAT());

                    System.out.println("---");
                });





        System.out.println("\n========================\n");


        System.out.printf("Cost Before VAT : "+ project.calculateTotalCostWithoutVAT()+" €\n");
        System.out.println("Cost Before After VAT  est de " +project.calculateTotalCostWithVAT() +" €\n" );
        System.out.println("The total cost of the project with profit margin ("+ project.getProfitMargin()+" %) est de :"+ project.calculateTotalCostWithVAT_WithProfitmargin()+" €\n");
        if(project.getClient().isProfessional()) {
            System.out.println("As you are our Best client we made a discout for you   (5%) est de :" + project.calculateTotalCostWithVAT_WithProfitmarginDiscounted() + " €\n");
        }
    }

    private static Project searchProject() {

        String projectName = inputValidator.validateStringInput("Entrez le nom du projet à rechercher :");
        return projectController.getProjectByName(projectName);
    }
}



