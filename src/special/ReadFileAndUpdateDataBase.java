package special;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ecc.emp.core.Context;
import com.ecc.emp.core.EMPException;
import com.ecc.emp.data.IndexedCollection;
import com.ecc.emp.data.KeyedCollection;
import com.ecc.emp.transaction.EMPTransactionDef;
import com.ecc.liana.action.LianaJDBCAction;
import com.ecc.liana.base.LianaConstants;
import com.ecc.liana.base.LianaDBAccess;
import com.ecc.liana.base.LianaStandard;
import com.ecc.liana.base.Trace;
import com.ecc.liana.exception.TranFailException;
import com.ecc.liana.loaf.DataMappingProvider;
import com.ecc.liana.loaf.LoafConstants;
import com.ecc.liana.loaf.TableConfig;
import com.ecc.liana.scheduler.file.parser.CommonFileParser;
import com.ecc.liana.scheduler.file.parser.FileParser;

/**
 * 功能概述：读取指定路径下特定格式的文件，解析后把相关内容更新到数据库中
 * ODS信审结果同步 读取ODS信审结果 GDB_BASE_APPROVE 同步到数据库中 CL_CREDITCARD_APPLY
 * 接口参考：ODS接口_需求号：xq2012050702_需求名(涉及1个源系统，2张表)_20121012.xls
 * 调用规则：在ODS开始的传数据后两小时执行一次本处理
 * 
 * @version
 * @author
 */
public class ODSAppRoveFileParseAction extends LianaJDBCAction {
	/**
	 * 每批包含的最大笔数
	 */
	private static final int MAX_RECORD_PER_BATCH = 1000;
	/**
	 * 字段分隔符
	 */
	private static final String FIELD_SEPERATOR = "\\|";
	private static final char a = 27;
	private static final String DATA_SEPERATOR = a + "";

	public String execute(Context context) throws EMPException {

		// 文件是否到达
		/*
		 * 取ODS的结果文件路径，并检查文件是否已经下载到该路径 路径结构 YYMMDD\CAP\GDB_BASE_PROPOSER.dat
		 * GDB_BASE_PROPOSER.ok
		 */
		/* 取settings.xml 里的参数ODS的结果文件路径 */

		String rootPath = LianaStandard
				.getSelfDefineSettingsValue("ODSAppRoveFile");

		/* 检查YYMMDD\CAP\GDB_BASE_PROPOSER.ok 是否存在，不存在则说明文件还没下载 */
		String ODSProposerFileOkFullPath = rootPath
				+ LianaStandard.getServerTime("yyyyMMdd")
				+ "/CAP/GDB_BASE_PROPOSER.ok";
		File ODSProposerFileOk = new File(ODSProposerFileOkFullPath);
		if (!ODSProposerFileOk.exists()) {// 存在说明文件文件已经下载完成
			Trace.logInfo(Trace.COMPONENT_FILE, ODSProposerFileOkFullPath
					+ " 文件不存在，ODS信审文件还没到达");
			return "1";
		}

		/* 检查YYMMDD\CAP\GDB_BASE_PROPOSER.dat 是否存在，不存在则说明文件还没下载 */
		String ODSProposerFileFullPath = rootPath
				+ LianaStandard.getServerTime("yyyyMMdd")
				+ "/CAP/GDB_BASE_PROPOSER.dat";

		File ODSProposerFile = new File(ODSProposerFileFullPath);
		if (!ODSProposerFile.exists()) {// 存在说明文件文件已经下载完成
			Trace.logError(Trace.COMPONENT_FILE, ODSProposerFileFullPath
					+ " 文件不存在，ODS信审读取文件出错");
			return "1";
		}

		/* 检查YYMMDD\CAP\GDB_BASE_APPROVE.ok 是否存在，不存在则说明文件还没下载 */
		String ODSAppRoveFileOkFullPath = rootPath
				+ LianaStandard.getServerTime("yyyyMMdd")
				+ "/CAP/GDB_BASE_APPROVE.ok";
		File ODSAppRoveFileOk = new File(ODSAppRoveFileOkFullPath);
		if (!ODSAppRoveFileOk.exists()) {// 存在说明文件文件已经下载完成
			Trace.logInfo(Trace.COMPONENT_FILE, ODSAppRoveFileOkFullPath
					+ " 文件不存在，ODS信审文件还没到达");
			return "1";
		}

		/* 检查YYMMDD\CAP\GDB_BASE_APPROVE.dat 是否存在，不存在则说明文件还没下载 */
		String ODSAppRoveFileFullPath = rootPath
				+ LianaStandard.getServerTime("yyyyMMdd")
				+ "/CAP/GDB_BASE_APPROVE.dat";

		File ODSAppRoveFile = new File(ODSAppRoveFileFullPath);
		if (!ODSAppRoveFile.exists()) {// 存在说明文件文件已经下载完成
			Trace.logError(Trace.COMPONENT_FILE, ODSAppRoveFileFullPath
					+ " 文件不存在，ODS信审读取文件出错");
			return "1";
		}
		// 文件导入
		FileParser parser = new CommonFileParser();

		BufferedReader freader = null;
		BufferedReader freader1 = null;
		Connection connection = null;
		try {
			connection = LianaDBAccess.getConnection(getDataSource());
			Statement stmt = null;
			stmt = connection.createStatement();
			ResultSet rs = null;

			/*
			 * GDB_BASE_APPROVE.dat 审批信息 3 GDB_BARCODE 条形码
			 * －》＝CL_CREDITCARD_MESSAGE.CMG_DID 取出 －》
			 * CL_CREDITCARD_MESSAGE.CMG_APPLYNO 申请编号
			 * ＝＝CL_CREDITCARD_APPLY.CAP_APPLYNO 15 APPROVE_RESULT 审批结果 更新
			 * －》CL_CREDITCARD_APPLY.CAP_AUDITSTATUS 56 TX_DATE 审批日期 更新
			 * －》CL_CREDITCARD_APPLY.CAP_PROCUPDATE
			 */
			freader = new BufferedReader(new InputStreamReader(
					new FileInputStream(ODSAppRoveFileFullPath)));
			String outputFields = "|||CMG_DID||||||||||||CAP_AUDITSTATUS|||||||||||||||||||||||||||||||||||||||||CAP_PROCUPDATE";
			String[] outputFieldName = outputFields.split(FIELD_SEPERATOR, -1);
			String strTemp;
			String strApplyNo = "";
			int i = 0, j = 0;
			IndexedCollection icoll = new IndexedCollection();
			while ((strTemp = freader.readLine()) != null) {
				String[] outputFieldValue = strTemp.split(DATA_SEPERATOR, -1);
				// 将联行号文件每一行解析为KColl并加到IColl中
				KeyedCollection kcoll = new KeyedCollection();
				kcoll = parser.parse(outputFieldName, outputFieldValue);

				// 根据条形码取出 CMG_APPLYNO 申请编号
				String CMG_APPLYNO = ((String) kcoll.getDataValue("CMG_DID"))
						.trim();
				rs = stmt
						.executeQuery("SELECT CMG_APPLYNO FROM CL_CREDITCARD_MESSAGE WHERE CMG_DID = '"
								+ CMG_APPLYNO + "'");
				if (rs.next()) {
					strApplyNo = rs.getString(1).trim();
				}

				if (strApplyNo.length() > 0) {// 申请编号不存在则忽略该条记录
					kcoll.addDataField("CAP_APPLYNO", strApplyNo);
					String date = (String) kcoll.getDataValue("CAP_PROCUPDATE");
					String formatedTime=date.replaceAll("-", "")+"000000";
					kcoll.setDataValue("CAP_PROCUPDATE", formatedTime);
					icoll.addDataElement(kcoll);
					j++;
				} else {
					Trace.logWarn(Trace.COMPONENT_FILE, CMG_APPLYNO
							+ " 申请编号不存在则忽略该条记录!");
				}
				// 每批 执行一次数据库操作
				if (((j + 1) % MAX_RECORD_PER_BATCH) == 0) {
					addBatchApprove(connection, icoll);
					icoll = new IndexedCollection();
				}
				i++;
			}
			// 余下的执行一次数据库操作
			if (icoll.size() > 0) {
				addBatchApprove(connection, icoll);
				icoll = new IndexedCollection();
			}

			/*
			 * GDB_BASE_PROPOSER.dat 申请人资料 1 GDB_BARCODE 条形码
			 * －》＝CL_CREDITCARD_MESSAGE.CMG_DID 取出 －》
			 * CL_CREDITCARD_MESSAGE.CMG_APPLYNO 申请编号
			 * ＝＝CL_CREDITCARD_PERSON.CPS_APPLYNO 15 EMAIL_ADDR 邮件地址 更新
			 * －》CL_CREDITCARD_PERSON.CPS_EMAIL
			 */
			freader1 = new BufferedReader(new InputStreamReader(
					new FileInputStream(ODSProposerFileFullPath)));

			outputFields = "|CMG_DID||||||||||||||CPS_EMAIL|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||";
			outputFieldName = outputFields.split(FIELD_SEPERATOR, -1);
			i = 0;
			j = 0;
			icoll = new IndexedCollection();
			while ((strTemp = freader1.readLine()) != null) {
				String[] outputFieldValue = strTemp.split(DATA_SEPERATOR, -1);
				// 将联行号文件每一行解析为KColl并加到IColl中
				KeyedCollection kcoll = new KeyedCollection();
				kcoll = parser.parse(outputFieldName, outputFieldValue);

				// 根据条形码取出 CMG_APPLYNO 申请编号
				String CMG_APPLYNO = ((String) kcoll.getDataValue("CMG_DID"))
						.trim();
				rs = stmt
						.executeQuery("SELECT CMG_APPLYNO FROM CL_CREDITCARD_MESSAGE WHERE CMG_DID = '"
								+ CMG_APPLYNO + "'");
				if (rs.next()) {
					strApplyNo = rs.getString(1).trim();
				}

				if (strApplyNo.length() > 0) {// 申请编号不存在则忽略该条记录
					//在申请人表中查询对应的申请编号；
					rs = stmt.executeQuery("select cca.CAP_APPLYUNION from CL_CREDITCARD_APPLY cca, CL_CREDITCARD_MESSAGE ccm where cca.CAP_APPLYNO=ccm.CMG_APPLYNO and ccm.CMG_DID='"+CMG_APPLYNO+"'");
					if (rs.next()) {
						strApplyNo = rs.getString(1).trim();
						kcoll.addDataField("CPS_APPLYNO", strApplyNo);
						icoll.addDataElement(kcoll);
					} else {
						Trace.logWarn(Trace.COMPONENT_FILE, CMG_APPLYNO
								+ " 申请人表中申请编号不存在，忽略该条记录!");
					}
					j++;
				} else {
					Trace.logWarn(Trace.COMPONENT_FILE, CMG_APPLYNO
							+ " 申请编号不存在则忽略该条记录!");
				}
				// 每批 执行一次数据库操作
				if (((j + 1) % MAX_RECORD_PER_BATCH) == 0) {
					addBatchProposer(connection, icoll);
					icoll = new IndexedCollection();
				}
				i++;
			}
			// 余下的执行一次数据库操作
			if (icoll.size() > 0) {
				addBatchProposer(connection, icoll);
				icoll = new IndexedCollection();
			}

		} catch (IOException ex) {
			Trace.logError(Trace.COMPONENT_FILE, "文件读取失败", ex);
			throw new TranFailException(LianaConstants.DEFAULT_ERROR_CODE,
					"文件读取失败", ex);
		} catch (TranFailException ex) {
			Trace.logError(Trace.COMPONENT_MAPPING, ex.getErrorCode() + "："
					+ ex.getMessage(), ex);
			handleException(context, ex, true);
		} catch (SQLException e) {
			Trace.logError(Trace.COMPONENT_FILE, "数据库操作失败", e);
			throw new TranFailException(LianaConstants.DEFAULT_ERROR_CODE,
					"数据库操作失败", e);
		} finally {
			try {
				if (freader != null) {
					freader.close();
				}
				if (freader1 != null) {
					freader1.close();
				}
			} catch (IOException ex) {
				Trace.logWarning(Trace.COMPONENT_FILE, "文件对象关闭异常", ex);
			}
			LianaDBAccess.releaseConnection(getDataSource(), connection);
		}

		return DEFAULT_RETURN_VALUE;
	}

	private void addBatchApprove(Connection connection, IndexedCollection icoll)
			throws TranFailException {

		// <!--更新信审结果状态 审批日期 审批状态 -->
		TableConfig tableConfig = DataMappingProvider
				.getTableConfig("updateCL_CREDITCARD_APPLY");
		DataMappingProvider.doTableBatchOperation(connection, tableConfig,
				LoafConstants.OP_TYPE_UPDATE, icoll);
		// 如果独立事务，每个批次提交
		if (this.getTransactionType() == EMPTransactionDef.TRX_REQUIRE_NEW) {
			try {
				connection.commit();
			} catch (SQLException ex) {
				Trace.logError(Trace.COMPONENT_MAPPING, "文件导入数据库批量提交失败", ex);
				throw new TranFailException(LianaConstants.DEFAULT_ERROR_CODE,
						"文件导入数据库批量提交失败", ex);
			}
		}
	}

	private void addBatchProposer(Connection connection, IndexedCollection icoll)
			throws TranFailException {

		// <!--更新信审结果状态 邮件地址 -->
		TableConfig tableConfig1 = DataMappingProvider
				.getTableConfig("updateCL_CREDITCARD_PERSON");
		DataMappingProvider.doTableBatchOperation(connection, tableConfig1,
				LoafConstants.OP_TYPE_UPDATE, icoll);
		// 如果独立事务，每个批次提交
		if (this.getTransactionType() == EMPTransactionDef.TRX_REQUIRE_NEW) {
			try {
				connection.commit();
			} catch (SQLException ex) {
				Trace.logError(Trace.COMPONENT_MAPPING, "文件导入数据库批量提交失败", ex);
				throw new TranFailException(LianaConstants.DEFAULT_ERROR_CODE,
						"文件导入数据库批量提交失败", ex);
			}
		}
	}

}
