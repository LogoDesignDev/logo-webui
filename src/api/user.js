import request from 'utils/request'

export function login (params) {
  return request.post('/login', params)
}

export function register (params) {
  return request.post('/register', params)
}

export function getUserInfo (params) {
  return request.get('/getUserInfo', params)
}

export function getUserPic (params) {
  return request.get('/getUserPic', params)
}

export function uploadUserPic (params) {
  return request.post('/uploadUserPic', params)
}

export function changePassword (params) {
  return request.post('/changePassword', params)
}

export function changeEmail (params) {
  return request.post('/changePassword', params)
}

export function changePhone (params) {
  return request.post('/changePassword', params)
}

export function getDetailInfo (params) {
  return request.post('/getDetailInfo', params)
}
