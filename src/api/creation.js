import request from 'utils/request'

export function generate (params) {
  return request.post('/creation/generate', params)
}
