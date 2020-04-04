const tokenKey = 'token'
const usernameKey = 'username'

export function getToken () {
  return localStorage.getItem(tokenKey)
}

export function setToken (token) {
  return localStorage.setItem(tokenKey, token)
}

export function removeToken () {
  return localStorage.removeItem(tokenKey)
}

export function getUsername () {
  return localStorage.getItem(usernameKey)
}

export function setUsername (username) {
  return localStorage.setItem(usernameKey, username)
}

export function removeUsername () {
  return localStorage.removeItem(usernameKey)
}
