import axios from "axios";
import { config } from "../../Constants";

export const kudconnectApi = {
  saveUserDetails,
  getUserDetails,
};

const instance = axios.create({
  baseURL: config.url.API_BASE_URL,
});

function bearerAuth(token) {
  return `Bearer ${token}`;
}

function getUserDetails(token) {
  return instance.get(`/user/me`, {
    headers: { Authorization: bearerAuth(token) },
  });
}

function saveUserDetails(token, userDetails) {
  return instance.put(`/user/me`, userDetails, {
    headers: { Authorization: bearerAuth(token) },
  });
}
