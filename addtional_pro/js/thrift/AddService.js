//
// Autogenerated by Thrift Compiler (0.9.2)
//
// DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
//


//HELPER FUNCTIONS AND STRUCTURES

AddService_addFunc_args = function(args) {
  this.param1 = null;
  this.param2 = null;
  if (args) {
    if (args.param1 !== undefined) {
      this.param1 = args.param1;
    }
    if (args.param2 !== undefined) {
      this.param2 = args.param2;
    }
  }
};
AddService_addFunc_args.prototype = {};
AddService_addFunc_args.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
      if (ftype == Thrift.Type.STRING) {
        this.param1 = input.readString().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 2:
      if (ftype == Thrift.Type.STRING) {
        this.param2 = input.readString().value;
      } else {
        input.skip(ftype);
      }
      break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

AddService_addFunc_args.prototype.write = function(output) {
  output.writeStructBegin('AddService_addFunc_args');
  if (this.param1 !== null && this.param1 !== undefined) {
    output.writeFieldBegin('param1', Thrift.Type.STRING, 1);
    output.writeString(this.param1);
    output.writeFieldEnd();
  }
  if (this.param2 !== null && this.param2 !== undefined) {
    output.writeFieldBegin('param2', Thrift.Type.STRING, 2);
    output.writeString(this.param2);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

AddService_addFunc_result = function(args) {
  this.success = null;
  if (args) {
    if (args.success !== undefined) {
      this.success = args.success;
    }
  }
};
AddService_addFunc_result.prototype = {};
AddService_addFunc_result.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 0:
      if (ftype == Thrift.Type.STRING) {
        this.success = input.readString().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 0:
        input.skip(ftype);
        break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

AddService_addFunc_result.prototype.write = function(output) {
  output.writeStructBegin('AddService_addFunc_result');
  if (this.success !== null && this.success !== undefined) {
    output.writeFieldBegin('success', Thrift.Type.STRING, 0);
    output.writeString(this.success);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

AddServiceClient = function(input, output) {
    this.input = input;
    this.output = (!output) ? input : output;
    this.seqid = 0;
};
AddServiceClient.prototype = {};
AddServiceClient.prototype.addFunc = function(param1, param2, callback) {
  this.send_addFunc(param1, param2, callback); 
  if (!callback) {
    return this.recv_addFunc();
  }
};

AddServiceClient.prototype.send_addFunc = function(param1, param2, callback) {
  this.output.writeMessageBegin('addFunc', Thrift.MessageType.CALL, this.seqid);
  var args = new AddService_addFunc_args();
  args.param1 = param1;
  args.param2 = param2;
  args.write(this.output);
  this.output.writeMessageEnd();
  if (callback) {
    var self = this;
    this.output.getTransport().flush(true, function() {
      var result = null;
      try {
        result = self.recv_addFunc();
      } catch (e) {
        result = e;
      }
      callback(result);
    });
  } else {
    return this.output.getTransport().flush();
  }
};

AddServiceClient.prototype.recv_addFunc = function() {
  var ret = this.input.readMessageBegin();
  var fname = ret.fname;
  var mtype = ret.mtype;
  var rseqid = ret.rseqid;
  if (mtype == Thrift.MessageType.EXCEPTION) {
    var x = new Thrift.TApplicationException();
    x.read(this.input);
    this.input.readMessageEnd();
    throw x;
  }
  var result = new AddService_addFunc_result();
  result.read(this.input);
  this.input.readMessageEnd();

  if (null !== result.success) {
    return result.success;
  }
  throw 'addFunc failed: unknown result';
};
