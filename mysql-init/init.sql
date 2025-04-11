-- Création de root@'%' pour permettre l'accès depuis les autres conteneurs
CREATE USER IF NOT EXISTS 'root'@'%' IDENTIFIED BY 'rootapi';
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' WITH GRANT OPTION;
FLUSH PRIVILEGES;