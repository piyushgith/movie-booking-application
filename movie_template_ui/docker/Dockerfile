# Production Dockerfile to serve static site using nginx
# Multi-stage build is unnecessary for a pure static HTML/CSS/JS site, but
# we'll use a small nginx base image and copy site files into /usr/share/nginx/html

FROM nginx:stable-alpine

# Remove default nginx config (optional) and replace with our custom config
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Copy static site files
# Expect the website root files (index.html, css/, js/, user/, admin/, etc.) to be
# at the repository root where the Docker build context is executed. Adjust paths
# if your project uses a different build output directory.
COPY ../src/ /usr/share/nginx/html/
COPY ../index.html /usr/share/nginx/html/

# Set proper permissions (nginx on alpine runs as nginx user typically)
RUN chown -R nginx:nginx /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]