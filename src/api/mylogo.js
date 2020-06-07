import request from 'utils/request'

export function upload (params) {
  return request.post('/upload', params, {
    'Content-Type': 'multipart/form-data'
  })
}

export function getGalleryInfo (params) {
  return request.post('/mylogo', params)
}

export function addlogo (params) {
  return request.post('/mylogo/galleryid/addlogo', params)
}
