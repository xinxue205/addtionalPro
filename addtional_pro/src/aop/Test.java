package aop;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect    
@Component    
public  class Test {    
    //ע��Service���ڰ���־�������ݿ�    
    @Resource    
    //�����쳣��־��¼����    
     private  static  final Logger logger = LoggerFactory.getLogger(Test. class);    
    
    //Service���е�    
    @Pointcut("@annotation(com.annotation.SystemServiceLog)")    
     public  void serviceAspect() {    
    }    
    
    //Controller���е�    
    @Pointcut("@annotation(com.annotation.SystemControllerLog)")    
     public  void controllerAspect() {    
    }    
    
    /**  
     * ǰ��֪ͨ ��������Controller���¼�û��Ĳ���  
     *  
     * @param joinPoint �е�  
     */    
    @Before("controllerAspect()")    
     public  void doBefore(JoinPoint joinPoint) {    
    
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();    
        HttpSession session = request.getSession();    
        //��ȡsession�е��û�    
        UserDemo user = (UserDemo) session.getAttribute(WebConstants.CURRENT_USER);    
        //�����IP    
        String ip = request.getRemoteAddr();    
         try {    
            //*========����̨���=========*//    
            System.out.println("=====ǰ��֪ͨ��ʼ=====");    
            System.out.println("���󷽷�:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));    
            System.out.println("��������:" + getControllerMethodDescription(joinPoint));    
            System.out.println("������:" + user.getName());    
            System.out.println("����IP:" + ip);    
            //*========���ݿ���־=========*//    
            Log log = SpringContextHolder.getBean("logxx");    
            log.setDescription(getControllerMethodDescription(joinPoint));    
            log.setMethod((joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));    
            log.setType("0");    
            log.setRequestIp(ip);    
            log.setExceptionCode( null);    
            log.setExceptionDetail( null);    
            log.setParams( null);    
            log.setCreateBy(user);    
            log.setCreateDate(DateUtil.getCurrentDate());    
            //�������ݿ�    
            logService.add(log);    
            System.out.println("=====ǰ��֪ͨ����=====");    
        }  catch (Exception e) {    
            //��¼�����쳣��־    
            logger.error("==ǰ��֪ͨ�쳣==");    
            logger.error("�쳣��Ϣ:{}", e.getMessage());    
        }    
    }    
    
    /**  
     * �쳣֪ͨ ��������service���¼�쳣��־  
     *  
     * @param joinPoint  
     * @param e  
     */    
    @AfterThrowing(pointcut = "serviceAspect()", throwing = "e")    
     public  void doAfterThrowing(JoinPoint joinPoint, Throwable e) {    
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();    
        HttpSession session = request.getSession();    
        //��ȡsession�е��û�    
        UserDemo user = (UserDemo) session.getAttribute(WebConstants.CURRENT_USER);    
        //��ȡ����ip    
        String ip = request.getRemoteAddr();    
        //��ȡ�û����󷽷��Ĳ��������л�ΪJSON��ʽ�ַ���    
        String params = "";    
         if (joinPoint.getArgs() !=  null && joinPoint.getArgs().length > 0) {    
             for ( int i = 0; i < joinPoint.getArgs().length; i++) {    
                params += JSONUtil.toJsonString(joinPoint.getArgs()[i]) + ";";    
            }    
        }    
         try {    
              /*========����̨���=========*/    
            System.out.println("=====�쳣֪ͨ��ʼ=====");    
            System.out.println("�쳣����:" + e.getClass().getName());    
            System.out.println("�쳣��Ϣ:" + e.getMessage());    
            System.out.println("�쳣����:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));    
            System.out.println("��������:" + getServiceMthodDescription(joinPoint));    
            System.out.println("������:" + user.getName());    
            System.out.println("����IP:" + ip);    
            System.out.println("�������:" + params);    
               /*==========���ݿ���־=========*/    
            Log log = SpringContextHolder.getBean("logxx");    
            log.setDescription(getServiceMthodDescription(joinPoint));    
            log.setExceptionCode(e.getClass().getName());    
            log.setType("1");    
            log.setExceptionDetail(e.getMessage());    
            log.setMethod((joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));    
            log.setParams(params);    
            log.setCreateBy(user);    
            log.setCreateDate(DateUtil.getCurrentDate());    
            log.setRequestIp(ip);    
            //�������ݿ�    
            logService.add(log);    
            System.out.println("=====�쳣֪ͨ����=====");    
        }  catch (Exception ex) {    
            //��¼�����쳣��־    
            logger.error("==�쳣֪ͨ�쳣==");    
            logger.error("�쳣��Ϣ:{}", ex.getMessage());    
        }    
         /*==========��¼�����쳣��־==========*/    
        logger.error("�쳣����:{}�쳣����:{}�쳣��Ϣ:{}����:{}", joinPoint.getTarget().getClass().getName() + joinPoint.getSignature().getName(), e.getClass().getName(), e.getMessage(), params);    
    
    }    
    
    
    /**  
     * ��ȡע���жԷ�����������Ϣ ����service��ע��  
     *  
     * @param joinPoint �е�  
     * @return ��������  
     * @throws Exception  
     */    
     public  static String getServiceMthodDescription(JoinPoint joinPoint)    
             throws Exception {    
        String targetName = joinPoint.getTarget().getClass().getName();    
        String methodName = joinPoint.getSignature().getName();    
        Object[] arguments = joinPoint.getArgs();    
        Class targetClass = Class.forName(targetName);    
        Method[] methods = targetClass.getMethods();    
        String description = "";    
         for (Method method : methods) {    
             if (method.getName().equals(methodName)) {    
                Class[] clazzs = method.getParameterTypes();    
                 if (clazzs.length == arguments.length) {    
                    description = method.getAnnotation(SystemServiceLog. class).description();    
                     break;    
                }    
            }    
        }    
         return description;    
    }    
    
    /**  
     * ��ȡע���жԷ�����������Ϣ ����Controller��ע��  
     *  
     * @param joinPoint �е�  
     * @return ��������  
     * @throws Exception  
     */    
     public  static String getControllerMethodDescription(JoinPoint joinPoint)  throws Exception {    
        String targetName = joinPoint.getTarget().getClass().getName();    
        String methodName = joinPoint.getSignature().getName();    
        Object[] arguments = joinPoint.getArgs();    
        Class targetClass = Class.forName(targetName);    
        Method[] methods = targetClass.getMethods();    
        String description = "";    
         for (Method method : methods) {    
             if (method.getName().equals(methodName)) {    
                Class[] clazzs = method.getParameterTypes();    
                 if (clazzs.length == arguments.length) {    
                    description = method.getAnnotation(SystemControllerLog. class).description();    
                     break;    
                }    
            }    
        }    
         return description;    
    }    
}
