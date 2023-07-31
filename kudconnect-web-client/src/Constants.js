const prod = {
    url: {
      KEYCLOAK_BASE_URL: "https://keycloak.herokuapp.com",
      API_BASE_URL: 'https://myapp.herokuapp.com',
    }
  }
  
  const dev = {
    url: {
      KEYCLOAK_BASE_URL: "http://localhost:9080",
      API_BASE_URL: 'http://localhost:8080',
    }
  }
  
  export const config = process.env.NODE_ENV === 'development' ? dev : prod