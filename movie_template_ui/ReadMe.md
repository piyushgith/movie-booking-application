# Movie Booking Application UI

A modern web-based movie ticket booking interface built with HTML, CSS, and JavaScript, containerized with Docker.

## Features

- User Authentication
- Movie Listings & Details
- Show Time Selection
- Seat Selection
- Booking Confirmation
- Booking History
- Admin Dashboard
  - Movie Management
  - Show Management
  - Theater Management
  - User Management

## Prerequisites

- Docker installed on your system
- Web browser (Chrome, Firefox, Safari, or Edge)
- Backend API server running (refer to backend repository)

## Docker Setup

### Building the Docker Image

```bash
# Clone the repository
git clone https://github.com/yourusername/movie-booking-application.git

# Navigate to the UI directory
cd movie-booking-application/movie_template_ui

# Build the Docker image
sudo docker build -t movie-booking-ui:latest .
```

### Running the Container

```bash
# Run the container
sudo docker run -d -p 9000:80 --name movie-booking-ui movie-booking-ui:latest

# To stop the container
sudo docker stop movie-booking-ui

# To start an existing container
sudo docker start movie-booking-ui
```

The application will be available at: http://localhost:9000

## Development Setup

If you want to run the application without Docker for development:

1. Install a local web server (like Live Server for VS Code)
2. Open the project in VS Code
3. Right-click on `index.html` and select "Open with Live Server"

## Configuration

The application expects the backend API to be running at `http://localhost:4000`. If your backend is running on a different URL, update the API URL in `config.js`.

```javascript
// config.js
const API_BASE_URL = 'http://localhost:4000/api';
```

## Directory Structure

```
movie_template_ui/
├── admin/              # Admin dashboard pages
├── user/               # User-facing pages
├── error/             # Error pages
├── index.html         # Entry point
├── config.js          # Configuration file
├── Dockerfile         # Docker configuration
└── README.md          # Documentation
```

## Security Notes

1. CSRF tokens are required for API requests
2. Ensure proper CORS configuration in the backend
3. Use HTTPS in production
4. Keep dependencies updated

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For support, please open an issue in the GitHub repository or contact the maintainers.
