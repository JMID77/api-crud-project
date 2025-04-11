# ****** RUN
# .\docker-orchestrator.ps1 dev
# .\docker-orchestrator.ps1 prod


# Vérifier si un argument a été passé
if ($args.Count -eq 0) {
    Write-Host "Erreur : Veuillez spécifier l'environnement (dev ou prod)."
    exit 1
}

# Récupérer l'argument passé (dev ou prod)
$ENV = $args[0].toLower()

# Vérifier si l'argument est valide
if ($ENV -ne "dev" -and $ENV -ne "prod") {
    Write-Host "Erreur : L'argument doit être 'dev' ou 'prod'."
    exit 1
}

# Si c'est PROD, construire avec Dockerfile
Write-Host "Environnement : $ENV"


# Vérifier si le conteneur est déjà en cours d'exécution
$listEnv = @("dev", "prod")
foreach ($subEnv in $listEnv) {
    $subContainerName = "spring_app_$subEnv"
    
    # Filtrer sur container en cours d'exécution (q) et arrêté (a).
    $runningContainer = docker ps -aq --filter "name=$subContainerName"
    if ($runningContainer) {
        Write-Host "Le conteneur est déjà en cours d'exécution. Arrêt et suppression du conteneur existant [$subContainerName]"
        docker stop $runningContainer
        docker rm $runningContainer
    } else {
        Write-Host "Aucun conteneur en cours d'exécution trouvé [$subContainerName]."
    }
}


if ($ENV -eq "dev") {
    Write-Host "Nettoyage et construction du projet avec Maven..."
    mvn clean package -DskipTests

    # Vérifier si le .jar a bien été généré
    $jarPath = "./target/apiCrudProject.1.02.jar"
    if (-Not (Test-Path $jarPath)) {
        Write-Host "Erreur : Le fichier .jar n'a pas été généré. Veuillez vérifier le processus de build Maven."
        exit 1
    } else {
        Write-Host "Le fichier .jar a été généré avec succès $jarPath."
    }
}


# Lancement via docker-compose
Write-Host "Lancement de l'environnement $ENV via docker-compose..."
$env:CONTAINER_NAME = "myapp_$ENV"
$env:APP_PORT = "8080"
#if ($ENV -eq "dev") { $env:APP_PORT = "8082" } else { $env:APP_PORT = "8080" }
docker-compose --profile $ENV up --build -d
if ($LASTEXITCODE -ne 0) {
    Write-Host "Erreur : Échec du lancement docker-compose."
    exit 1
}

# Afficher l'état du conteneur
$containerName = "spring_app_$ENV"
Start-Sleep -Seconds 3
$containerStatus = docker ps --filter "name=$containerName" --format "{{.Status}}"
if ($containerStatus) {
    Write-Host "État du conteneur : $containerStatus"
} else {
    Write-Host "Erreur : Le conteneur n'a pas été lancé correctement."
    exit 1
}


# Afficher l'URL d'accès à l'application
# Récupérer l'adresse IP locale
$ipAddress = (Test-Connection -ComputerName $env:COMPUTERNAME -Count 1).IPv4Address.IPAddressToString
Write-Host "L'application est accessible à l'adresse : http://$ipAddress`:8080"


# Afficher les logs du conteneur
Write-Host "Affichage des logs du conteneur..."
docker logs -f $containerName
# Fin du script