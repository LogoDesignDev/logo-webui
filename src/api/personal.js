import request from 'utils/request'

export function getFollowList (params) {
  return request.get('/getFollowList', params)
}

export function getFansList (params) {
  return request.get('/getFansList', params)
}

export function follow (params) {
  return request.get('/follow', params)
}

export function unfollow (params) {
  return request.get('/unfollow', params)
}
