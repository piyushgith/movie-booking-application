# Production Docker image (nginx) for static site

Build the image (run from `movie_template_ui/docker` or adjust paths):

Windows PowerShell example (from project root `movie_template_ui`):

    sudo docker build -f docker/Dockerfile -t movie-ui:latest .

Run locally mapping port 8080 to container 80:

    sudo docker run --rm -p 8080:80 movie-ui:latest

Notes:
- The Dockerfile copies files from `../src/` and `../index.html` into the image. If your build artifacts are in a different folder (for example `dist/`), update the `COPY` paths in `docker/Dockerfile` accordingly.
- The `nginx.conf` includes SPA fallback (try_files) so client-side routing should work. Adjust caching headers as necessary for your environment.
- For production, build artifacts should be minified and fingerprinted so long cache lifetimes are safe for static assets.
