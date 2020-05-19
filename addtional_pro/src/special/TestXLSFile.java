package special;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.framework5.action.FormBean;
import com.framework5.dao.TCommand;
import com.framework5.dao.TQuery;
import com.framework5.entity.LogsBean;
import com.framework5.entity.PageEntity;
import com.framework5.entity.SearchEntity;
import com.framework5.entity.TEntity;
import com.framework5.entity.annotation.EntityBaseFieldJoinAnnotation;
import com.framework5.entity.annotation.EntityDateFieldJoinAnnotation;
import com.framework5.entity.annotation.EntityFieldAnnotation;
import com.framework5.entity.annotation.EntityTableAnnotation;
import com.framework5.exception.DataException;
import com.framework5.resource.SysLogger;
import com.framework5.system.BeanUtils;
import com.framework5.system.DateUtils;
import com.framework5.system.LogsThread;

public class TestXLSFile<Q extends TQuery, C extends TCommand> extends CoreService<Q, C> {

	public final boolean accessPower(String powermodel, String rolesid, String userid, String menuid) {
		try {
			if (powermodel.equals("1")) {
				String sql = "select id from system_power where userid = '" + userid + "' and menuid = '" + menuid + "' and ispower = 1";
				Object obj = this.getCommand().getByField(sql);
				if (BeanUtils.isObjectNotNull(obj))
					return true;
			} else {
				String value = BeanUtils.replaceStr(rolesid, ",", "','");
				value.substring(0, value.length() - 1);
				
				String sql = "select id from system_power where roleid in ('" + value + "') and menuid = '" + menuid + "' and ispower = 1";
				Object obj = this.getCommand().getByField(sql);
				if (BeanUtils.isObjectNotNull(obj))
					return true;
			}
		} catch (DataException e) {
			e.printStackTrace();
		}
		return false;
	}
	
    final public List<TEntity<?>> query(TEntity<?> e, SearchEntity s) throws DataException {
    	return this.query(e, s, null, null);
    }
    
    final public List<TEntity<?>> query(TEntity<?> e, SearchEntity s, PageEntity p) throws DataException {
    	return this.query(e, s, p,null);
    }
    
    final public List<TEntity<?>> query(TEntity<?> e, SearchEntity s, PageEntity p, LogsBean l) throws DataException {
    	List<TEntity<?>> list = this.getQuery().query(e, s, p, l);
    	LogsThread logsThread = new LogsThread();
    	logsThread.setLogsBean(l);
    	logsThread.start();
    	
    	return list;
    }
    
	final public void getMaster(TEntity<?> e, SearchEntity s, LogsBean l) throws DataException {
    	this.getQuery().getMaster(e, s, l);
    	
    	LogsThread logsThread = new LogsThread();
    	logsThread.setLogsBean(l);
    	logsThread.start();
    }
	
	final public void getDetail(List<TEntity<?>> list, SearchEntity s, LogsBean l, String selectid) throws DataException {
    	this.getQuery().getDetail(list, s, l, selectid);
    	
    	LogsThread logsThread = new LogsThread();
    	logsThread.setLogsBean(l);
    	logsThread.start();
    }
	
	final public void get(TEntity<?> e, String k, String v, SearchEntity s, LogsBean l) throws DataException {
    	this.getQuery().get(e, k, v, s, l);
    	
    	LogsThread logsThread = new LogsThread();
    	logsThread.setLogsBean(l);
    	logsThread.start();
    }
	
	final public int delete(TEntity<?> e) throws DataException {
    	return this.delete(e, null);
    }
	
	final public int delete(TEntity<?> e, LogsBean l) throws DataException {
		int i = this.getQuery().delete(e, l);
		
		if (BeanUtils.isObjectNotNull(l)) {
			LogsThread logsThread = new LogsThread();
	    	logsThread.setLogsBean(l);
	    	logsThread.start();
		}
    	
    	return i;
    }
	
	public void save(LogsBean l) throws DataException {
		String selectid = null;
		
		// Post Primary (Object = TEntity)
		for (FormBean form : entitys) { 
			if (form.isPrimary()) {
				TEntity<?> e = (TEntity<?>)form.getObject();
				String state = saveState();
				this.getQuery().save(e, l, state);
				
				LogsThread logsThread = new LogsThread();
		    	logsThread.setLogsBean(l);
		    	logsThread.start();
				
				selectid = e.getId().toString();
			} else if (! form.isPrimary()) {  // Post not Primary (Object =List)
				List ls = (List)form.getObject();
				
				for (int i = 0; i < ls.size(); i ++) {
					TEntity<?> e = (TEntity<?>)ls.get(i);
					String state = saveState(e);  // XSH
					entityLike(e);

					String tableName = e.getClass().getAnnotation(EntityTableAnnotation.class).TableName();
					String currentEntityField = e.getClass().getAnnotation(EntityTableAnnotation.class).currentEntityField();
					
					if (i == 0)
						this.getCommand().updateLinkFlag(tableName, currentEntityField, selectid);
					
					this.getQuery().save(e, l, state);
					
					LogsThread logsThread = new LogsThread();
			    	logsThread.setLogsBean(l);
			    	logsThread.start();
					
					if (i == (ls.size() - 1)) 
						this.getCommand().deleteLinkFlag(tableName, currentEntityField);
				}
			}
		}
		
	}
	
	final public SearchEntity search(TEntity<?> e, SearchEntity s) throws DataException {
		StringBuffer searchCmd  = new StringBuffer(1000);
		
		Class<?> clazz = e.getClass();
		List<String> fieldzz = new ArrayList<String>();
		
	    while (BeanUtils.isSubClass(clazz, Object.class)) {
	    	Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				if (BeanUtils.listExists(fieldzz, field.getName())) {
					continue;
				} else {
					fieldzz.add(field.getName());

					// 处理 EntityFieldAnnotation Begin
					if(field.isAnnotationPresent(EntityFieldAnnotation.class)) {
						EntityFieldAnnotation entityFieldAnnotation = field.getAnnotation(EntityFieldAnnotation.class);
						
						String split = entityFieldAnnotation.Split();
						String tableName = e.getClass().getAnnotation(EntityTableAnnotation.class).TableName();
						String fieldName = field.getName();
						Object value = null;
						
						if (entityFieldAnnotation.Field() && BeanUtils.isEmpty(entityFieldAnnotation.Join())) {
														
							Method[] methods = e.getClass().getMethods();
        					for (Method method : methods)
        						if (method.getName().startsWith("get")) 
        							if (method.getName().substring(3).equalsIgnoreCase(fieldName))
										try {
											value = (Object)method.invoke(e);
											if (BeanUtils.isObjectNotNull(value)) {
												String v = value.toString();
												if (BeanUtils.isNotEmpty(v)) {
												      v  = StringFilter(v);//清除特殊字符
													if (entityFieldAnnotation.Date()) {
														
														String temp = entityFieldAnnotation.DateFunction();
														searchCmd.append(tableName + "." + fieldName + " = " + BeanUtils.replaceStr(temp, "?", "'" + DateUtils.getDateToString((Date)value) + "'") + " and ");
														
													} else if (entityFieldAnnotation.DateTime()) {
														
														String temp = entityFieldAnnotation.DateFunction();
														searchCmd.append(tableName + "." + fieldName + " = " + BeanUtils.replaceStr(temp, "?", "'" + DateUtils.getDateTimeToString((Date)value) + "'") + " and ");
														
													} else {
														if (BeanUtils.isNotEmpty(split)) {
															for (String vs: v.split(split)) 
																searchCmd.append(tableName + "." + fieldName + " like '%" + vs.trim() + "%' and ");
														} else {
															if (v.trim().indexOf("*") >= 0)
											    	    		searchCmd.append(tableName + "." + fieldName + " like '" + BeanUtils.getDBLink(v.trim()) + "' and ");
												    		else
											    	    		searchCmd.append(tableName + "." + fieldName + " = '" + v.trim() + "' and ");
														}
													}
													
												}
													
											}
										} catch (Exception ex) {
											SysLogger.error("search failed!", ex);
										    throw new DataException(ex);
										}
										
						} else if (entityFieldAnnotation.Field() && BeanUtils.isNotEmpty(entityFieldAnnotation.Join())) {
							String joinTable = null;
							String joinReturn = entityFieldAnnotation.JoinReturn().toLowerCase();
							String[] joinReturns = joinReturn.split(" as ");
							
							try {
								joinTable = Class.forName(entityFieldAnnotation.Join()).newInstance().getClass().getAnnotation(EntityTableAnnotation.class).TableName();
							} catch (Exception e1) {
								e1.printStackTrace();
							}

							Method[] methods = e.getClass().getMethods();
        					for (Method method : methods)
        						if (method.getName().startsWith("get")) 
        							
        							if (method.getName().substring(3).equalsIgnoreCase(fieldName)) {
										try {
											value = (Object)method.invoke(e);
											if (BeanUtils.isObjectNotNull(value)) {
												String v = value.toString();
												
												if (BeanUtils.isNotEmpty(v)) {
													v  = StringFilter(v);//清除特殊字符
													if (entityFieldAnnotation.Date()) {
														
														String temp = entityFieldAnnotation.DateFunction();
														searchCmd.append(tableName + "." + fieldName + " = " + BeanUtils.replaceStr(temp, "?", "'" + DateUtils.getDateToString((Date)value) + "'") + " and ");
														
													} else if (entityFieldAnnotation.DateTime()) {
														
														String temp = entityFieldAnnotation.DateFunction();
														searchCmd.append(tableName + "." + fieldName + " = " + BeanUtils.replaceStr(temp, "?", "'" + DateUtils.getDateTimeToString((Date)value) + "'") + " and ");
														
													} else {
														if (BeanUtils.isNotEmpty(split)) {
															for (String vs: v.split(split)) 
																searchCmd.append(tableName + "." + fieldName + " like '%" + vs.trim() + "%' and ");
														} else {
															if (v.trim().indexOf("*") >= 0)
											    	    		searchCmd.append(tableName + "." + fieldName + " like '" + BeanUtils.getDBLink(v.trim()) + "' and ");
												    		else
											    	    		searchCmd.append(tableName + "." + fieldName + " = '" + v.trim() + "' and ");
														}
													}
													
												}
													
											}
										} catch (Exception ex) {
											SysLogger.error("search failed!", ex);
										    throw new DataException(ex);
										}
        							} else if (joinReturns.length>1 && method.getName().substring(3).equalsIgnoreCase(joinReturns[1])) {
										try {
											value = (Object)method.invoke(e);
											if (BeanUtils.isObjectNotNull(value)) {
												String v = value.toString();
												if (BeanUtils.isNotEmpty(v)) {
													v  = StringFilter(v);//清除特殊字符
														if (BeanUtils.isNotEmpty(split)) {
															for (String vs: v.split(split)) {
																searchCmd.append(tableName + "." + fieldName);
																searchCmd.append(" in (");
																searchCmd.append(" select " + entityFieldAnnotation.JoinKey() + " from " + joinTable);
																searchCmd.append(" where " + entityFieldAnnotation.JoinWhere());
																searchCmd.append(" and " + joinReturns[0] + " like '%" + vs.trim() + "%') and ");
															}
														} else {
															if (v.trim().indexOf("*") >= 0) {
																searchCmd.append(tableName + "." + fieldName);
																searchCmd.append(" in (");
																searchCmd.append(" select " + entityFieldAnnotation.JoinKey() + " from " + joinTable);
																searchCmd.append(" where " + entityFieldAnnotation.JoinWhere());
																searchCmd.append(" and " + joinReturns[0] + " like '" + BeanUtils.getDBLink(v.trim()) + "') and ");
															} else {
																searchCmd.append(tableName + "." + fieldName);
																searchCmd.append(" in (");
																searchCmd.append(" select " + entityFieldAnnotation.JoinKey() + " from " + joinTable);
																searchCmd.append(" where " + entityFieldAnnotation.JoinWhere());
																searchCmd.append(" and " + joinReturns[0] + " = '" + v.trim() + "') and ");
															}
														}
													
												}
													
											}
										} catch (Exception ex) {
											SysLogger.error("search failed!", ex);
										    throw new DataException(ex);
										}
        							}
						}
					}
					// 处理 EntityFieldAnnotation End
					
					// 处理 EntityDateFieldJoinAnnotation Begin
					if(field.isAnnotationPresent(EntityDateFieldJoinAnnotation.class)) {
						EntityDateFieldJoinAnnotation entityDateFieldJoinAnnotation = field.getAnnotation(EntityDateFieldJoinAnnotation.class);
						if (BeanUtils.isNotEmpty(entityDateFieldJoinAnnotation.StartDate()) && BeanUtils.isNotEmpty(entityDateFieldJoinAnnotation.EndDate())) {
							String tableName = e.getClass().getAnnotation(EntityTableAnnotation.class).TableName();
							String fieldName = field.getName();
							String startDate = entityDateFieldJoinAnnotation.StartDate();
							String endDate = entityDateFieldJoinAnnotation.EndDate();
							
							Method[] methods = e.getClass().getMethods();
        					for (Method method : methods)
        						if (method.getName().startsWith("get")) {
        							try {
        								if (method.getName().substring(3).equalsIgnoreCase(startDate)) {
											String value = (String)method.invoke(e);
											if (BeanUtils.isNotEmpty(value)) 
							    	    		searchCmd.append(tableName + "." + fieldName + " >= to_date('" + value + "','" + entityDateFieldJoinAnnotation.Format() + "') and ");
        								}
        								
        								if (method.getName().substring(3).equalsIgnoreCase(endDate)) {
											String value = (String)method.invoke(e);
											if (BeanUtils.isNotEmpty(value)) 
												searchCmd.append(tableName + "." + fieldName + " <= to_date('" + value + "','" + entityDateFieldJoinAnnotation.Format() + "') and ");
        								}
        							} catch (Exception ex) {
										SysLogger.error("search failed!", ex);
									    throw new DataException(ex);
									}
        						}
						}
					}
					// 处理 EntityDateFieldJoinAnnotation End
					
					
					
					
					// 处理 EntityBaseFieldJoinAnnotation Begin
					if(field.isAnnotationPresent(EntityBaseFieldJoinAnnotation.class)) {
						EntityBaseFieldJoinAnnotation entityBaseFieldJoinAnnotation = field.getAnnotation(EntityBaseFieldJoinAnnotation.class);
						if (BeanUtils.isNotEmpty(entityBaseFieldJoinAnnotation.StartField()) && BeanUtils.isNotEmpty(entityBaseFieldJoinAnnotation.EndField())) {
							String tableName = e.getClass().getAnnotation(EntityTableAnnotation.class).TableName();
							String fieldName = field.getName();
							String startDate = entityBaseFieldJoinAnnotation.StartField();
							String endDate = entityBaseFieldJoinAnnotation.EndField();
							
							Method[] methods = e.getClass().getMethods();
        					for (Method method : methods)
        						if (method.getName().startsWith("get")) {
        							try {
										Object value = (Object)method.invoke(e);
        								Class<?> type=method.getReturnType();
										if (BeanUtils.isObjectNotNull(value)&&BeanUtils.isNotEmpty(value.toString())) {
											if(type.equals(Date.class)){
		        								if (method.getName().substring(3).equalsIgnoreCase(startDate)) {
													Date v = (Date)method.invoke(e);
									    	    	searchCmd.append(tableName + "." + fieldName + " >= to_date('"  + DateUtils.getDateTimeToString(v) + "','yyyy/MM/dd hh24:mi:ss') and ");
		        								}
									    	    if (method.getName().substring(3).equalsIgnoreCase(endDate)){ 
													Date v = (Date)method.invoke(e);
									    	    	searchCmd.append(tableName + "." + fieldName + " <= to_date('"  + DateUtils.getDateTimeToString(v) + "','yyyy/MM/dd hh24:mi:ss') and ");
									    	    }
											}else{
		        								if (method.getName().substring(3).equalsIgnoreCase(startDate)) {
													String v = (String)method.invoke(e);
									    	    	searchCmd.append(tableName + "." + fieldName + " >= '" + v + "' and ");
		        								}
		        								if (method.getName().substring(3).equalsIgnoreCase(endDate)){
													String v = (String)method.invoke(e); 
													searchCmd.append(tableName + "." + fieldName + " <= '" + v + "' and ");
		        								}
											}
										}
        							} catch (Exception ex) {
										SysLogger.error("search failed!", ex);
									    throw new DataException(ex);
									}
        						}
						}
					}
					// 处理 EntityBaseFieldJoinAnnotation End
					
				}
			}
			clazz = clazz.getSuperclass();
	    }
    	
	    if (BeanUtils.isNotEmpty(searchCmd.toString())) {
	    	s.setSearchCmd(searchCmd.substring(0, searchCmd.length() - 4));
	    	s.setSearchDesc("查询:" + s.getSearchCmd());
	    }
    	
    	return s;
    }

    final public SearchEntity quick(TEntity<?> e, SearchEntity s) throws DataException {
    	List<String> fieldList = getQucikField(e);
    	StringBuffer searchCmd  = new StringBuffer(1000);

    	String[] value = s.getSearchCmd().split(" ");
    	for (String v : value)
    		if (v.trim().indexOf("*") > 0)
    			for (String field: fieldList)
    	    		searchCmd.append(field + " like '" + BeanUtils.getDBLink(v.trim()) + "' or ");
    		else
    			for (String field: fieldList)
    	    		searchCmd.append(field + " = '" + v.trim() + "' or ");
    	
    	s.setSearchCmd(searchCmd.substring(0, searchCmd.length() - 4));
    	s.setSearchDesc("查询:" + s.getSearchCmd());
    	
    	return s;
    }
    
    final public Object[] getPrior(TEntity<?> e, SearchEntity s, String selectid, int rowno) throws DataException {
		return getQuery().getPrior(e, s, selectid, rowno);
	}
	
    final public Object[] getNext(TEntity<?> e, SearchEntity s, String selectid, int rowno) throws DataException {
		return getQuery().getNext(e, s, selectid, rowno);
	}
    
    final public String exprot(List<TEntity<?>> list) throws DataException {
    	if (list == null || list.size() <= 0)
    		return null;
    	
    	String file = "Excel.xls";
    	List<String> titleList = new ArrayList<String>();
    	
    	Class<?> clazz = list.get(0).getClass();
    	List<String> fieldzz = new ArrayList<String>();
	    while (BeanUtils.isSubClass(clazz, Object.class)) {
	    	Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				if (BeanUtils.listExists(fieldzz, field.getName()))
	    			continue;
	    		fieldzz.add(field.getName());
				if (field.isAnnotationPresent(EntityFieldAnnotation.class)) {
					EntityFieldAnnotation entityFieldAnnotation = field.getAnnotation(EntityFieldAnnotation.class);
					if (entityFieldAnnotation.Export())
	        			titleList.add(entityFieldAnnotation.DisplayLable());
	        	}
			}			
			clazz = clazz.getSuperclass();
	    }

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("Sheet1");
		
		for (int i = 0; i < titleList.size(); i++)
			sheet.setColumnWidth((short) i, (short) (20 * 256));
		
		HSSFFont font = wb.createFont();
		font.setFontName("宋体");
		
		HSSFCellStyle style = wb.createCellStyle();
		style.setFont(font);
		
		//总Title
		//HSSFRow rowTitle = sheet.createRow(0);
		//HSSFCell descCell = rowTitle.createCell((short) 0);
		//descCell.setEncoding(HSSFCell.ENCODING_UTF_16);
		//descCell.setCellValue("导出数据");
		//descCell.setCellStyle(style);
		
		
		//取出title的数据，写成题目，每条记录就是一个列名
		HSSFRow rowTitle = sheet.createRow(0);
		for (int i = 0; i < titleList.size(); i++) {
			HSSFCell cell = rowTitle.createCell((short) i);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue((String) titleList.get(i));
			cell.setCellStyle(style);
		}
		
		//取出date的数据，每个list代表一条记录，由ArrayList封装. 也可用对象来代表一条记录
		for (int i = 0; i < list.size(); i++) {
			HSSFRow row = sheet.createRow(i + 1);
			
			// 画表
			int j = 0;
			
			TEntity<?> e = list.get(i);
			clazz = e.getClass();
			fieldzz = new ArrayList<String>();
		    while (BeanUtils.isSubClass(clazz, Object.class)) {
		    		Field[] fields = clazz.getDeclaredFields();
		    		    		
					for (Field field : fields) {
						if (field.isAnnotationPresent(EntityFieldAnnotation.class)) {
							if (BeanUtils.listExists(fieldzz, field.getName()))
				    			continue;
				    		fieldzz.add(field.getName());
				    		
				    		EntityFieldAnnotation entityFieldAnnotation = field.getAnnotation(EntityFieldAnnotation.class);
			        		if (entityFieldAnnotation.Export()) {
			        			
			        			HSSFCell cell = row.createCell((short)j);
			        			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			        			cell.setCellStyle(style);
			        			
			        			Method[] methods = clazz.getMethods();
			        			for (Method method : methods)
			        				if (method.getName().startsWith("get"))
				        				if (method.getName().substring(3).equalsIgnoreCase(field.getName())) {
				        					try {
				        						String s = "";
				        						if (method.invoke(e) != null)
				        							s = method.invoke(e).toString();
												
												if (field.getType().equals(Date.class))
													s = DateUtils.getDateTimeToString((Date)method.invoke(e));
				        						
				        						cell.setCellValue(s);
				        					} catch (Exception ex) {
				        						SysLogger.error("export EXCEL error!", ex);
				        						throw new DataException(ex);
				        					}
				        				}
			        			j ++ ;
			        		}
			        		
			        	}
					}
					clazz = clazz.getSuperclass();
		    }
		}

		try {
			FileOutputStream fileOut = new FileOutputStream(file);
			wb.write(fileOut);
			// fileOut.flush();
			fileOut.close();
		} catch (Exception ex) {
			SysLogger.error("export EXCEL error (out file)!", ex);
			throw new DataException(ex);
		}
		
		return file;
	}
    
    public  byte[] getImgStream(String sql) throws DataException{
		try{
		Object zpObj=this.getCommand().getByField(sql);
		if(zpObj!=null){
			if(zpObj instanceof Blob){
				Blob zp=(Blob)zpObj;
				byte[] zpbyte=new byte[(int) zp.length()];
				InputStream input=zp.getBinaryStream();
				input.read(zpbyte);
				input.close();
				return zpbyte;
			}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
    
    /**
     * 导出列表到excel
     * @param filename   导出文件名称，可不填   
     * @param title      excel的标题和对应的ID   eg:[姓名,name]
     * @param data       数据集
     * @return
     * @throws DataException
     */
    @SuppressWarnings("deprecation")
	public String export(String filename,List<String[]> title,List<Map<String, Object>> data) throws DataException{
    	String file = "Excel.xls";
    	if(BeanUtils.isNotEmpty(filename))
    		file=filename+".xls";
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("Sheet1");
		for (int i = 0; i < title.size(); i++) {
			sheet.setColumnWidth((short)i, (short) (20 * 256));
		}
		HSSFFont font = wb.createFont();
		font.setFontName("宋体");
		
		HSSFCellStyle style = wb.createCellStyle();
		style.setFont(font);
		
		//写成标题
		HSSFRow rowTitle = sheet.createRow(0);
		for (int i = 0; i < title.size(); i++) {
			HSSFCell cell = rowTitle.createCell((short) i);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue((String) title.get(i)[0]);
			cell.setCellStyle(style);
		}
		for (int i = 0; i < data.size(); i++) {
			HSSFRow row = sheet.createRow(i + 1);
			Map<String, Object> m=data.get(i);
			for (int r = 0; r < title.size(); r++) {
				Object o=m.get(title.get(r)[1]);
				HSSFCell cell = row.createCell((short)r);
				cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell.setCellStyle(style);
				cell.setCellValue(o==null?"无":o.toString());
			}
		}
		
		try {
			FileOutputStream fileOut = new FileOutputStream(file);
			wb.write(fileOut);
			// fileOut.flush();
			fileOut.close();
		} catch (Exception ex) {
			SysLogger.error("export EXCEL error (out file)!", ex);
			throw new DataException(ex);
		} 
		return file;
	}
	 /**
	  * 清除掉所有特殊字符 
	  * @param str
	  * @return
	  * @throws PatternSyntaxException
	  */
	public  String StringFilter(String str) throws PatternSyntaxException   {      
	        // 只允许字母和数字        
	        // String   regEx  =  "[^a-zA-Z0-9]";                      
	           // 清除掉所有特殊字符   
	  String regEx="[`~!@#$%^&()+=|{}':;',\\[\\].<>/?~！@#￥%……&（）——+|{}【】‘；：”“’。，、？]";   
	  Pattern p = Pattern.compile(regEx);      
	  Matcher m = p.matcher(str);      
	  return m.replaceAll("").trim();      
	  }	   
    
    
}
