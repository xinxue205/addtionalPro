import React from 'react';
import ReactDOM from 'react-dom';
const util = require('util');
const fs = require('fs');
const readFile = util.promisify(fs.readFile);

readFile('file.txt')
  .then(data => console.log(data))
  .catch(err => console.log(err));
  
ReactDOM.render(
  <h1>Hello, world!</h1>,
  document.getElementById('content')
);