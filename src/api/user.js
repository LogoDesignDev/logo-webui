import request from '../utils/request'

export function login (params) {
  return request.post('/login', params)
}

export function register (params) {
  return request.post('/register', params)
}

export function getUserInfo (params) {
  return request.get('/getUserInfo', params)
}
