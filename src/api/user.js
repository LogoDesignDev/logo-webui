import request from 'utils/request'

export function login (params) {
  return request.post('/user/login', params)
}

export function register (params) {
  return request.post('/user/register', params)
}

export function getUserInfo (params) {
  return request.post('/user/getUserInfo', params)
}

export function getUserPic (params) {
  return request.post('/user/getUserPic', params)
}

export function uploadUserPic (params) {
  return request.post('/user/uploadUserPic', params)
}

export function changePassword (params) {
  return request.post('/user/changePassword', params)
}

export function changeEmail (params) {
  return request.post('/user/changeEmail', params)
}

export function changePhone (params) {
  return request.post('/user/changePhone', params)
}

export function getDetailInfo (params) {
  return request.post('/getDetailInfo', params)
}
