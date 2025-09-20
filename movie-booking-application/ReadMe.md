sudo docker build -t my-web-app:1.0 .
sudo docker run -d -p 8080:80 --name my-html-container my-web-app:1.0
sudo docker run -p 4000:4000 -v movie-booking-data:/app/data movie-booking-app

`
sudo docker build -t movie-booking-app .
`

`
sudo docker run -p 4000:4000 -v movie-booking-data:/app/data movie-booking-app
`

`
Application: http://localhost:4000

H2 Console: http://localhost:4000/h2-console

Swagger UI: http://localhost:4000/swagger-ui/index.html

`