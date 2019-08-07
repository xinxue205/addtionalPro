var path = require('path');
var webpack = require('webpack');
 
module.exports = {
    entry: [
        'webpack/hot/dev-server',
        path.resolve(__dirname, './app/index.js')
    ],
    output: {
        path: path.resolve(__dirname, './build'),
        filename: 'bundle.js',
    },
  module: {
    loaders: [
      {
        test: /\.jsx?$/,
        loader: 'babel-loader',
        exclude: /node_modules/,
        query: {presets: ['es2015', 'react'] }
      }
    ]
  },
  resolve: {
    extensions: ['','.js', '.jsx'],
  }
};