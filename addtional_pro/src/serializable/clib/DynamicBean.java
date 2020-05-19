package serializable.clib;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.beans.BeanMap;

public class DynamicBean {

 private  Object object = null;//��̬���ɵ���
 private  BeanMap beanMap = null;//������������Լ����Ե�����

 public DynamicBean() {
  super();
 }
 
 @SuppressWarnings("rawtypes")
 public DynamicBean(Map propertyMap) {
  this.object = generateBean(propertyMap);
  this.beanMap = BeanMap.create(this.object);
 }

 /**
  * ��bean���Ը�ֵ
  * @param property ������
  * @param value ֵ
  */
 public void setValue(Object property, Object value) {
  beanMap.put(property, value);
 }

 /**
  * ͨ���������õ�����ֵ
  * @param property ������
  * @return ֵ
  */
 public Object getValue(String property) {
  return beanMap.get(property);
 }

 /**
  * �õ���ʵ��bean����
  * @return
  */
 public Object getObject() {
  return this.object;
 }

 /**
  * @param propertyMap
  * @return
  */
 @SuppressWarnings("rawtypes")
 private Object generateBean(Map propertyMap) {
  BeanGenerator generator = new BeanGenerator();
  Set keySet = propertyMap.keySet();
  for (Iterator i = keySet.iterator(); i.hasNext();) {
    String key = (String) i.next();
    generator.addProperty(key, (Class) propertyMap.get(key));
  }
  return generator.create();
 }
 
}