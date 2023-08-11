const prod = {
    url: {
        KEYCLOAK_BASE_URL: "http://kudconnect.local:9080",
        API_BASE_URL: 'http://kudconnect.local:8080',
        AVATARS_DICEBEAR_URL: 'https://api.dicebear.com/6.x'
    }
}

const dev = {
    url: {
        // KEYCLOAK_BASE_URL: "http://kudconnect.local:9080",
        // API_BASE_URL: 'http://kudconnect.local:8080',
        KEYCLOAK_BASE_URL: "http://localhost:9080",
        API_BASE_URL: 'http://localhost:8080',
        AVATARS_DICEBEAR_URL: 'https://api.dicebear.com/6.x'
    }
}

export const config = process.env.NODE_ENV === 'development' ? dev : prod