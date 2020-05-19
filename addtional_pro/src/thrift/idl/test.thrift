namespace java thrift.doubleside
service ClientHandshakeService{
    oneway void HandShake();
}
 
service ServerCallbackService{
    oneway void Push(1: string msg);
}