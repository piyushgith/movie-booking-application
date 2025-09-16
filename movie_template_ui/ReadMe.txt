FROM nginx:alpine
COPY . /usr/share/nginx/html

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]


# Use a specific version of nginx for better stability
FROM nginx:1.25-alpine

# Add labels for better maintainability
LABEL maintainer="Ironman"
LABEL description="Movie Booking Application UI"

# Create a non-root user for better security
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Copy static files with proper permissions
COPY --chown=appuser:appgroup . /usr/share/nginx/html/

# Use custom nginx configuration if needed
# COPY nginx.conf /etc/nginx/conf.d/default.conf

# Expose port 80
EXPOSE 80

# Switch to non-root user
USER appuser

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]