import request from 'utils/request'

export function getFirstUrl (params) {
  return request.get('/home/getFirstUrl', params)
}
