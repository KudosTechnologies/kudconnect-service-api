FROM node:20.4-slim as react_builder

# Copy package.json and package-lock.json first to leverage Docker's caching
COPY package*.json ./
RUN npm install

# Copy the rest of the app's source code
COPY . .

# Build the React app
RUN npm run build

# Stage 2: Create the Nginx server
FROM nginx:alpine

# Copy the built React app from the previous stage to Nginx's default directory
COPY --from=react_builder /build /usr/share/nginx/html

# Expose port 80 (default for Nginx)
EXPOSE 80

# Start Nginx server
CMD ["nginx", "-g", "daemon off;"]