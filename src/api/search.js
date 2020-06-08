import request from 'utils/request'

export function searchAuthor (params) {
  return request.post('/search/author', params)
}

export function searchProd (params) {
  return request.post('/search/prod', params)
}
