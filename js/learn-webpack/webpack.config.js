module.exports = {
  entry:  __dirname + "/app/main.js",//�Ѷ���ἰ��Ψһ����ļ�
  output: {
    path: __dirname + "/public",//�������ļ���ŵĵط�
    filename: "bundle.js"//���������ļ����ļ���
  },
  
  devServer: {
	host: '0.0.0.0',
    port: 8001,
    hot: true,
    disableHostCheck: true,
    contentBase: "./public",//���ط����������ص�ҳ�����ڵ�Ŀ¼
    historyApiFallback: true,//����ת
    inline: true,//ʵʱˢ��
	proxy: {
      '/api/*': {
        target: 'http://localhost:8080/',
        changeOrigin: true,
        secure: false
      }
    }
  }
}