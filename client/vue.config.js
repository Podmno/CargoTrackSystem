const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true
})
module.exports = {
  pwa: {
      iconPaths: {
          favicon32: 'favicon.ico',
          favicon16: 'favicon.ico',
          appleTouchIcon: 'favicon.ico',
          maskIcon: 'favicon.ico',
          msTileImage: 'favicon.ico'
      }
  },
  devServer: {
    proxy: {
      '/server': {
        // Windows 改成 localhost:8088
        target: 'http://127.0.0.1:8088',
        changeOrigin: true,
        ws: true,
        secure: false,
        pathRewrite: {
          '^/server':''
        }
      }
    }
  }
};

