const path = require('path')
const resolve = dir => path.join(__dirname, dir)

module.exports = {
  devServer: {
    open: true,
    host: 'localhost',
    port: '8080',
    https: true,
    hotOnly: false,
    proxy: {
      '/api': {
        // target: 'https://85c83ee2-a252-4599-8305-ee8b82a7907c.mock.pstmn.io', // API服务器的地址
        target: 'http://0.0.0.0:5003',
        //target: 'http://localhost:80', // API服务器的地址
        ws: true, // 代理websockets
        changeOrigin: true, // 虚拟的站点需要更管origin
        pathRewrite: { // 重写路径 比如'/api/aaa/ccc'重写为'/aaa/ccc'
          '^/api': ''
        }
      }
    }
  },

  chainWebpack: config => {
    config.resolve.alias
      .set('@', resolve('src'))
      .set('api', resolve('src/api'))
      .set('store', resolve('src/store'))
      .set('utils', resolve('src/utils'))
  }
}
