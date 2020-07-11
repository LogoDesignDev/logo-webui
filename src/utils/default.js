// 开发环境跨域
export const baseURL = '/api'
export const nodeBaseURL = '/node'
export const serverPrx = 'http://47.115.52.184:8900'
// export const baseURL = 'http://localhost:8080/'

/**
 * 把十进制数转换成xxK的表达形式
 * 不足1000的不处理
 */
export function transition (num) {
  return num < 1000 ? num : (num / 1000).toFixed(1) + 'K'
}
