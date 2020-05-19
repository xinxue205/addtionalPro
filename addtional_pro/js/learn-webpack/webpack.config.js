module.exports = {
  entry:  __dirname + "/app/main.js",//已多次提及的唯一入口文件
  output: {
    path: __dirname + "/public",//打包后的文件存放的地方
    filename: "bundle.js"//打包后输出文件的文件名
  },
  
  devServer: {
	host: '0.0.0.0',
    port: 8001,
    hot: true,
    disableHostCheck: true,
    contentBase: "./public",//本地服务器所加载的页面所在的目录
    historyApiFallback: true,//不跳转
    inline: true,//实时刷新
	proxy: {
      '/api/*': {
        target: 'http://localhost:8080/',
        changeOrigin: true,
        secure: false
      }
    }
  }
}