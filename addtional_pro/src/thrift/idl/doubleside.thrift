namespace java thrift.doubleside1
service CommunicateService{
    oneway void say(1: string msg);
}