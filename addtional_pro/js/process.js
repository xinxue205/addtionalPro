// ������ն�
process.stdout.write("Hello World!" + "\n");

// ͨ��������ȡ
process.argv.forEach(function(val, index, array) {
   console.log(index + ': ' + val);
});

// ��ȡִ��·��
console.log(process.execPath);


// ƽ̨��Ϣ
console.log(process.platform);