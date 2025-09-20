# Kubernetes manifests for movie-ui

This folder contains simple manifests to run the static `movie-ui` site on Kubernetes.

Included files:
- `deployment.yaml` - Deployment with 2 replicas, readiness/liveness probes, and resource requests/limits.
- `service.yaml` - ClusterIP Service that exposes port 80. Change to `type: LoadBalancer` for cloud.
- `ingress.yaml` - Example Ingress resource (nginx ingress class). Configure `host` and TLS as needed.

Quick apply (for local clusters):

    kubectl apply -f k8s/deployment.yaml
    kubectl apply -f k8s/service.yaml
    kubectl apply -f k8s/ingress.yaml

Notes and tips:
- Image: update `image: movie-ui:latest` in `deployment.yaml` to point at your registry (for example `ghcr.io/ORG/movie-ui:tag` or `docker.io/ORG/movie-ui:tag`).
- For cloud deployments, change `service.yaml` to `type: LoadBalancer` so the cloud provider provisions an external IP.
- Local clusters:
  - kind: You can load local images into kind (`kind load docker-image movie-ui:latest`) or push to a local registry.
  - minikube: use `minikube image load movie-ui:latest` or set `minikube docker-env` to build inside the minikube daemon.
  - Docker Desktop: images built locally are available to the cluster by default.
- Ingress TLS: uncomment TLS section in `ingress.yaml` and configure cert-manager or your cloud certs to obtain a certificate.
- Scaling: adjust `replicas` in `deployment.yaml` to meet traffic needs; configure an HPA if desired.
