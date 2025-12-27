# Production Dockerfile to serve static site using nginx
FROM nginx:alpine

# Copy nginx config (relative to build context, not Dockerfile location)
COPY docker/nginx.conf /etc/nginx/conf.d/default.conf

# Copy static site files from src folder
COPY src/ /usr/share/nginx/html/

RUN chown -R nginx:nginx /usr/share/nginx/html

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]