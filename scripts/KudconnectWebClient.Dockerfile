FROM node:20.8-slim as react_builder

COPY kudconnect-web-client kudconnect-web-client
WORKDIR kudconnect-web-client
RUN npm install

# Build the React app
ENV NODE_ENV=development_local
RUN npm run build

# Stage 2: Create the Nginx server
FROM nginx:alpine

# Copy the built React app from the previous stage to Nginx's default directory
COPY --from=react_builder kudconnect-web-client/build /usr/share/nginx/html

# Expose port 80 (default for Nginx)
EXPOSE 80

# Start Nginx server
CMD ["nginx", "-g", "daemon off;"]