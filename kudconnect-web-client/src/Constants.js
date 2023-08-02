const prod = {
    url: {
      KEYCLOAK_BASE_URL: "https://keycloak.herokuapp.com",
      API_BASE_URL: 'https://myapp.herokuapp.com',
      AVATARS_DICEBEAR_URL: 'https://api.dicebear.com/6.x'
    }
  }
  
  const dev = {
    url: {
      KEYCLOAK_BASE_URL: "http://localhost:9080",
      API_BASE_URL: 'http://localhost:8080',
      AVATARS_DICEBEAR_URL: 'https://api.dicebear.com/6.x'
    }
  }
  
  export const config = process.env.NODE_ENV === 'development' ? dev : prod