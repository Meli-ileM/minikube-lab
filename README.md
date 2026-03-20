# TP : Techniques de conteneurisation et micro-services

## Objectifs
- Comprendre l’architecture Cloud Native
- Déployer un cluster Kubernetes local avec Minikube dans Docker
- Utiliser kubectl pour administrer le cluster
- Déployer une application microservices
- Exposer les services dans Kubernetes
- Créer un ingress pour le routage
- Superviser le cluster via Kubernetes Dashboard

---

## 1. Création de l’image Docker Minikube

Créer le dossier `minikube-lab` et le fichier `Dockerfile` :

```dockerfile
FROM ubuntu:22.04
RUN apt-get update && apt-get install -y \
curl \
conntrack \
docker.io \
sudo
# installation kubectl
RUN curl -LO https://dl.k8s.io/release/v1.29.0/bin/linux/amd64/kubectl \
&& install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl
# installation minikube
RUN curl -LO https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64 \
&& install minikube-linux-amd64 /usr/local/bin/minikube
CMD ["/bin/bash"]
```

Construire l’image :
```
docker build -t minikube-lab ./minikube-lab
```

---

## 2. Lancement du container Minikube

```
docker run -it --privileged minikube-lab
```

---

## 3. Création du cluster Kubernetes

Dans le container :
```
minikube start --driver=docker
kubectl get nodes
kubectl get pods -A
```

---

## 4. Installation du Kubernetes Dashboard

```
minikube addons enable dashboard
minikube dashboard
```

---

## 5. Déploiement des microservices

Les fichiers YAML sont dans le dossier `k8s/`.
Pour chaque microservice :
```
kubectl apply -f <microservice>-deployment.yaml
kubectl apply -f <microservice>-service.yaml
```

Ordre recommandé :
- config
- discovery
- catalogue
- commande
- paiement
- gateway

---

## 6. Ajout de PostgreSQL et JPA

Dans `pom.xml` de `catalogue-service` et `paiement-service` :
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-ui</artifactId>
</dependency>
```

---

## 7. Configuration base de données

Dans `application.properties` ou `application.yml` :

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/service1db
    username: service1user
    password: service1pass
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true
```

---

## 8. Déploiement PostgreSQL sur Kubernetes

Fichiers :
- `postgres-deployment-microservice-catalogue.yaml`
- `postgres-deployment-microservice-paiement.yaml`

Déployer :
```
kubectl apply -f postgres-deployment-microservice-catalogue.yaml
kubectl apply -f postgres-deployment-microservice-paiement.yaml
```

---

## 9. Ingress pour le routage

Activer l’addon :
```
minikube addons enable ingress
```
Créer et appliquer le fichier `gateway-ingress.yaml` :
```
kubectl apply -f gateway-ingress.yaml
```
Ajouter l’IP de Minikube dans le fichier hosts :
```
minikube ip
# Ajoute l’IP dans C:\Windows\System32\drivers\etc\hosts
192.168.49.2 minikube.local
```

---

## 10. Vérifications et accès

Lister les pods/services :
```
kubectl get pods
kubectl get svc
kubectl get pvc
kubectl get pv
```
Accéder à un microservice via NodePort :
```
minikube service gateway-service
```

---

## 11. Suppression

```
kubectl delete -f <fichier>.yaml
kubectl delete pvc catalogue-pvc commande-pvc paiement-pvc
minikube stop
```

---

## 12. Dépôt GitHub

1. Initialise le dépôt :
```
git init
```
2. Ajoute le remote :
```
git remote add origin https://github.com/Meli-ileM/minikube-lab.git
```
3. Ajoute tous les fichiers :
```
git add .
```
4. Commit :
```
git commit -m "TP Kubernetes Minikube"
```
5. Push :
```
git push -u origin master
```

---

**Tout le TP est prêt dans ce dossier. Suis ce README pour chaque étape.**