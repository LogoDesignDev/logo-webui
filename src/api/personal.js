import request from 'utils/request'
import nodeRequest from 'utils/nodeRequest'

export function getFollowList (params) {
  return request.post('/personal/getFollowList', params)
}

export function getFansList (params) {
  return request.post('/personal/getFansList', params)
}

export function follow (params) {
  return request.post('/personal/follow', params)
}

export function unfollow (params) {
  return request.post('/personal/unfollow', params)
}

export function getProdCount (params) {
  return request.post('/personal/getProdCount', params)
}

export function getFansCount (params) {
  return request.post('/personal/getFansCount', params)
}

export function getFollowCount (params) {
  return request.post('/personal/getFollowCount', params)
}

export function getUserBeLikeCount (params) {
  return request.post('/personal/getUserLikeCount', params)
}

export function getUserBeCollectedCount (params) {
  return request.post('/personal/getUserCollectCount', params)
}

export function getUserProdsByUid (params) {
  return nodeRequest.get('/personal/getUserProdsByUid', { params })
}
