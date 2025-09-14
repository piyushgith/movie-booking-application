

`
docker build -t movie-booking-app .
`

`
docker run -p 4000:4000 -v movie-booking-data:/app/data movie-booking-app
`

`
Application: http://localhost:4000

H2 Console: http://localhost:4000/h2-console

Swagger UI: http://localhost:4000/swagger-ui/index.html

`