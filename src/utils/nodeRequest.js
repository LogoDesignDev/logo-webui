import axios from 'axios'
import { Message } from 'element-ui'
import { getToken } from './auth'
import { nodeBaseURL } from './default'

/**
 * 创建axios实例
 */
const service = axios.create({
  baseURL: nodeBaseURL,
  timeout: 5000,
  withCredentials: true,
  headers: {
    'content-type': 'application/json'
  }
})

/**
 * request拦截器
 */
service.interceptors.request.use(
  (config) => {
    config.headers.token = getToken() // 让每个请求携带自定义token
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

    // token相关
    if (data.code === 50008) {
      Message({
        message: '您未登录，请登录后再进行操作',
        type: 'error',
        duration: 5 * 1000
      })
    }
    return response
  },
  (error) => {
    return Promise.reject(error)
  }
)

export default service
