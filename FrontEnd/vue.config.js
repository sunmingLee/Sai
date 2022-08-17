const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave: false,
  devServer: {
    allowedHosts: ["i7a305.p.ssafy.io"],
    // proxy: {
    //   '/': {
    //     target: 'http://localhost:8080',
    //     changeOrign: true,
    //     ws: false
    //   }
    // }
  },
})
