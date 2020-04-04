import request from '../utils/request'

export function login (params) {
  return request.post('', params)
}

export function logout (params) {
  return request.post('', params)
}

export function getUserInfo (params) {
  return request.get('/getUserInfo', params)
}
