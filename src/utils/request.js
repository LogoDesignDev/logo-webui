import axios from 'axios'
import { Message } from 'element-ui'
import store from '../store'
import { getToken } from './auth'
import { postmanAPIKey, baseURL } from './default'

/**
 * 创建axios实例
 */
const service = axios.create({
  baseURL: baseURL,
  timeout: 5000,
  withCredentials: true,
  headers: {
    'content-type': 'application/json',
    'x-api-key': postmanAPIKey
  }
})

/**
 * request拦截器
 */
service.interceptors.request.use(
  (config) => {
    if (store.getters.token) {
      config.headers.token = getToken() // 让每个请求携带自定义token
    }
    return config
  },
  (error) => {
    // Do something with request error
    console.log(error) // for debug
    Promise.reject(error)
  }
)

/**
 * response拦截器
 */
service.interceptors.response.use(
  (response) => {
    const data = response.data

    // code为非20000是抛错
    if (data.code !== 20000) {
      Message({
        message: data.message,
        type: 'error',
        duration: 5 * 1000
      })

      // token相关
      if (data.code === 50008) {
        Message({
          message: '您未登录，请登录后再进行操作',
          type: 'error',
          duration: 5 * 1000
        })
      }
      return Promise.reject(Error('error'))
    } else {
      return response
    }
  },
  (error) => {
    console.log('err' + error) // for debug
    Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service
