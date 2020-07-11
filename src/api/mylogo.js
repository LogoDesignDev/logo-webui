import request from 'utils/request'

export function upload (params) {
  return request.post('/upload', params, {
    'Content-Type': 'multipart/form-data'
  })
}

export function getGalleryInfo (params) {
  return request.post('/mylogo', params)
}

export function getGalleryDetailInfo (params) {
  return request.post('/mylogo/id', params)
}

export function addGallery (params) {
  return request.post('/mylogo/addgallery', params)
}

export function updateGalleryInfo (params) {
  return request.post('/mylogo/updategallery', params)
}

export function deleteGalleryInfo (params) {
  return request.post('/mylogo/deletegallery', params)
}

export function addlogo (params) {
  return request.post('/mylogo/galleryid/addlogo', params)
}

export function deletelogoById (params) {
  return request.post('/mylogo/deletelogo', params)
}

export function getLogoDetail (params) {
  return request.post('/mylogo/galleryid/logoid', params)
}

export function collectPlus (params) {
  return request.post('/mylogo/collect', params)
}

export function collectLess (params) {
  return request.post('/mylogo/uncollect', params)
}

export function likePlus (params) {
  return request.post('/mylogo/like', params)
}
