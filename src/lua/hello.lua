local transcoder =  {}
-- �޷��ض���ĵ������
function helloWithoutTranscoder()
print 'hello, sky -- no transcoder '
end
-- �޲�
function transcoder.hello()
print 'hello'
return 'hello, sky'
end
-- ����Ϊstring����
function transcoder.test(str)
print('data from java is:'..str)
return 'the parameter is '..str
end
-- ����һ��lua����
function transcoder.getInfo()
   return {
        ['userId'] = '9999', 
        ['services'] = 
            {{
                'eat',
                'drink'
            }, {
                'eat2',
                'drink2'
            }}
    }
end
--[[
   infoObj-jsonʾ��:
        {
            'userId': '1111',   
            'services': [{                
               '0' : 'eat-test',
               '1' : 'drink-test'    
            }]
      }
--]]
-- ����һ��lua����
function transcoder.readInfo(infoObj)
   return infoObj.userId
end

return transcoder