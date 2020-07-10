import request from 'utils/request'

export function getComplex () {
  return request.get('/rank/complex')
}

export function getLike () {
  return request.get('/rank/like')
}

export function getCollect () {
  return request.get('/rank/collect')
}

export function getDesigner () {
  return request.get('/rank/designer')
}
