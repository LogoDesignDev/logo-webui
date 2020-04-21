import request from 'utils/request'

export function getFollowList (params) {
  return request.get('/personal/getFollowList', params)
}

export function getFansList (params) {
  return request.get('/personal/getFansList', params)
}

export function follow (params) {
  return request.get('/personal/follow', params)
}

export function unfollow (params) {
  return request.get('/personal/unfollow', params)
}
