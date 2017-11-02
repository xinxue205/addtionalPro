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
 * ���ܸ�������ȡָ��·�����ض���ʽ���ļ����������������ݸ��µ����ݿ���
 * ODS������ͬ�� ��ȡODS������ GDB_BASE_APPROVE ͬ�������ݿ��� CL_CREDITCARD_APPLY
 * �ӿڲο���ODS�ӿ�_����ţ�xq2012050702_������(�漰1��Դϵͳ��2�ű�)_20121012.xls
 * ���ù�����ODS��ʼ�Ĵ����ݺ���Сʱִ��һ�α�����
 * 
 * @version
 * @author
 */
public class ODSAppRoveFileParseAction extends LianaJDBCAction {
	/**
	 * ÿ��������������
	 */
	private static final int MAX_RECORD_PER_BATCH = 1000;
	/**
	 * �ֶηָ���
	 */
	private static final String FIELD_SEPERATOR = "\\|";
	private static final char a = 27;
	private static final String DATA_SEPERATOR = a + "";

	public String execute(Context context) throws EMPException {

		// �ļ��Ƿ񵽴�
		/*
		 * ȡODS�Ľ���ļ�·����������ļ��Ƿ��Ѿ����ص���·�� ·���ṹ YYMMDD\CAP\GDB_BASE_PROPOSER.dat
		 * GDB_BASE_PROPOSER.ok
		 */
		/* ȡsettings.xml ��Ĳ���ODS�Ľ���ļ�·�� */

		String rootPath = LianaStandard
				.getSelfDefineSettingsValue("ODSAppRoveFile");

		/* ���YYMMDD\CAP\GDB_BASE_PROPOSER.ok �Ƿ���ڣ���������˵���ļ���û���� */
		String ODSProposerFileOkFullPath = rootPath
				+ LianaStandard.getServerTime("yyyyMMdd")
				+ "/CAP/GDB_BASE_PROPOSER.ok";
		File ODSProposerFileOk = new File(ODSProposerFileOkFullPath);
		if (!ODSProposerFileOk.exists()) {// ����˵���ļ��ļ��Ѿ��������
			Trace.logInfo(Trace.COMPONENT_FILE, ODSProposerFileOkFullPath
					+ " �ļ������ڣ�ODS�����ļ���û����");
			return "1";
		}

		/* ���YYMMDD\CAP\GDB_BASE_PROPOSER.dat �Ƿ���ڣ���������˵���ļ���û���� */
		String ODSProposerFileFullPath = rootPath
				+ LianaStandard.getServerTime("yyyyMMdd")
				+ "/CAP/GDB_BASE_PROPOSER.dat";

		File ODSProposerFile = new File(ODSProposerFileFullPath);
		if (!ODSProposerFile.exists()) {// ����˵���ļ��ļ��Ѿ��������
			Trace.logError(Trace.COMPONENT_FILE, ODSProposerFileFullPath
					+ " �ļ������ڣ�ODS�����ȡ�ļ�����");
			return "1";
		}

		/* ���YYMMDD\CAP\GDB_BASE_APPROVE.ok �Ƿ���ڣ���������˵���ļ���û���� */
		String ODSAppRoveFileOkFullPath = rootPath
				+ LianaStandard.getServerTime("yyyyMMdd")
				+ "/CAP/GDB_BASE_APPROVE.ok";
		File ODSAppRoveFileOk = new File(ODSAppRoveFileOkFullPath);
		if (!ODSAppRoveFileOk.exists()) {// ����˵���ļ��ļ��Ѿ��������
			Trace.logInfo(Trace.COMPONENT_FILE, ODSAppRoveFileOkFullPath
					+ " �ļ������ڣ�ODS�����ļ���û����");
			return "1";
		}

		/* ���YYMMDD\CAP\GDB_BASE_APPROVE.dat �Ƿ���ڣ���������˵���ļ���û���� */
		String ODSAppRoveFileFullPath = rootPath
				+ LianaStandard.getServerTime("yyyyMMdd")
				+ "/CAP/GDB_BASE_APPROVE.dat";

		File ODSAppRoveFile = new File(ODSAppRoveFileFullPath);
		if (!ODSAppRoveFile.exists()) {// ����˵���ļ��ļ��Ѿ��������
			Trace.logError(Trace.COMPONENT_FILE, ODSAppRoveFileFullPath
					+ " �ļ������ڣ�ODS�����ȡ�ļ�����");
			return "1";
		}
		// �ļ�����
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
			 * GDB_BASE_APPROVE.dat ������Ϣ 3 GDB_BARCODE ������
			 * ������CL_CREDITCARD_MESSAGE.CMG_DID ȡ�� ����
			 * CL_CREDITCARD_MESSAGE.CMG_APPLYNO ������
			 * ����CL_CREDITCARD_APPLY.CAP_APPLYNO 15 APPROVE_RESULT ������� ����
			 * ����CL_CREDITCARD_APPLY.CAP_AUDITSTATUS 56 TX_DATE �������� ����
			 * ����CL_CREDITCARD_APPLY.CAP_PROCUPDATE
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
				// �����к��ļ�ÿһ�н���ΪKColl���ӵ�IColl��
				KeyedCollection kcoll = new KeyedCollection();
				kcoll = parser.parse(outputFieldName, outputFieldValue);

				// ����������ȡ�� CMG_APPLYNO ������
				String CMG_APPLYNO = ((String) kcoll.getDataValue("CMG_DID"))
						.trim();
				rs = stmt
						.executeQuery("SELECT CMG_APPLYNO FROM CL_CREDITCARD_MESSAGE WHERE CMG_DID = '"
								+ CMG_APPLYNO + "'");
				if (rs.next()) {
					strApplyNo = rs.getString(1).trim();
				}

				if (strApplyNo.length() > 0) {// �����Ų���������Ը�����¼
					kcoll.addDataField("CAP_APPLYNO", strApplyNo);
					String date = (String) kcoll.getDataValue("CAP_PROCUPDATE");
					String formatedTime=date.replaceAll("-", "")+"000000";
					kcoll.setDataValue("CAP_PROCUPDATE", formatedTime);
					icoll.addDataElement(kcoll);
					j++;
				} else {
					Trace.logWarn(Trace.COMPONENT_FILE, CMG_APPLYNO
							+ " �����Ų���������Ը�����¼!");
				}
				// ÿ�� ִ��һ�����ݿ����
				if (((j + 1) % MAX_RECORD_PER_BATCH) == 0) {
					addBatchApprove(connection, icoll);
					icoll = new IndexedCollection();
				}
				i++;
			}
			// ���µ�ִ��һ�����ݿ����
			if (icoll.size() > 0) {
				addBatchApprove(connection, icoll);
				icoll = new IndexedCollection();
			}

			/*
			 * GDB_BASE_PROPOSER.dat ���������� 1 GDB_BARCODE ������
			 * ������CL_CREDITCARD_MESSAGE.CMG_DID ȡ�� ����
			 * CL_CREDITCARD_MESSAGE.CMG_APPLYNO ������
			 * ����CL_CREDITCARD_PERSON.CPS_APPLYNO 15 EMAIL_ADDR �ʼ���ַ ����
			 * ����CL_CREDITCARD_PERSON.CPS_EMAIL
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
				// �����к��ļ�ÿһ�н���ΪKColl���ӵ�IColl��
				KeyedCollection kcoll = new KeyedCollection();
				kcoll = parser.parse(outputFieldName, outputFieldValue);

				// ����������ȡ�� CMG_APPLYNO ������
				String CMG_APPLYNO = ((String) kcoll.getDataValue("CMG_DID"))
						.trim();
				rs = stmt
						.executeQuery("SELECT CMG_APPLYNO FROM CL_CREDITCARD_MESSAGE WHERE CMG_DID = '"
								+ CMG_APPLYNO + "'");
				if (rs.next()) {
					strApplyNo = rs.getString(1).trim();
				}

				if (strApplyNo.length() > 0) {// �����Ų���������Ը�����¼
					//�������˱��в�ѯ��Ӧ�������ţ�
					rs = stmt.executeQuery("select cca.CAP_APPLYUNION from CL_CREDITCARD_APPLY cca, CL_CREDITCARD_MESSAGE ccm where cca.CAP_APPLYNO=ccm.CMG_APPLYNO and ccm.CMG_DID='"+CMG_APPLYNO+"'");
					if (rs.next()) {
						strApplyNo = rs.getString(1).trim();
						kcoll.addDataField("CPS_APPLYNO", strApplyNo);
						icoll.addDataElement(kcoll);
					} else {
						Trace.logWarn(Trace.COMPONENT_FILE, CMG_APPLYNO
								+ " �����˱��������Ų����ڣ����Ը�����¼!");
					}
					j++;
				} else {
					Trace.logWarn(Trace.COMPONENT_FILE, CMG_APPLYNO
							+ " �����Ų���������Ը�����¼!");
				}
				// ÿ�� ִ��һ�����ݿ����
				if (((j + 1) % MAX_RECORD_PER_BATCH) == 0) {
					addBatchProposer(connection, icoll);
					icoll = new IndexedCollection();
				}
				i++;
			}
			// ���µ�ִ��һ�����ݿ����
			if (icoll.size() > 0) {
				addBatchProposer(connection, icoll);
				icoll = new IndexedCollection();
			}

		} catch (IOException ex) {
			Trace.logError(Trace.COMPONENT_FILE, "�ļ���ȡʧ��", ex);
			throw new TranFailException(LianaConstants.DEFAULT_ERROR_CODE,
					"�ļ���ȡʧ��", ex);
		} catch (TranFailException ex) {
			Trace.logError(Trace.COMPONENT_MAPPING, ex.getErrorCode() + "��"
					+ ex.getMessage(), ex);
			handleException(context, ex, true);
		} catch (SQLException e) {
			Trace.logError(Trace.COMPONENT_FILE, "���ݿ����ʧ��", e);
			throw new TranFailException(LianaConstants.DEFAULT_ERROR_CODE,
					"���ݿ����ʧ��", e);
		} finally {
			try {
				if (freader != null) {
					freader.close();
				}
				if (freader1 != null) {
					freader1.close();
				}
			} catch (IOException ex) {
				Trace.logWarning(Trace.COMPONENT_FILE, "�ļ�����ر��쳣", ex);
			}
			LianaDBAccess.releaseConnection(getDataSource(), connection);
		}

		return DEFAULT_RETURN_VALUE;
	}

	private void addBatchApprove(Connection connection, IndexedCollection icoll)
			throws TranFailException {

		// <!--����������״̬ �������� ����״̬ -->
		TableConfig tableConfig = DataMappingProvider
				.getTableConfig("updateCL_CREDITCARD_APPLY");
		DataMappingProvider.doTableBatchOperation(connection, tableConfig,
				LoafConstants.OP_TYPE_UPDATE, icoll);
		// �����������ÿ�������ύ
		if (this.getTransactionType() == EMPTransactionDef.TRX_REQUIRE_NEW) {
			try {
				connection.commit();
			} catch (SQLException ex) {
				Trace.logError(Trace.COMPONENT_MAPPING, "�ļ��������ݿ������ύʧ��", ex);
				throw new TranFailException(LianaConstants.DEFAULT_ERROR_CODE,
						"�ļ��������ݿ������ύʧ��", ex);
			}
		}
	}

	private void addBatchProposer(Connection connection, IndexedCollection icoll)
			throws TranFailException {

		// <!--����������״̬ �ʼ���ַ -->
		TableConfig tableConfig1 = DataMappingProvider
				.getTableConfig("updateCL_CREDITCARD_PERSON");
		DataMappingProvider.doTableBatchOperation(connection, tableConfig1,
				LoafConstants.OP_TYPE_UPDATE, icoll);
		// �����������ÿ�������ύ
		if (this.getTransactionType() == EMPTransactionDef.TRX_REQUIRE_NEW) {
			try {
				connection.commit();
			} catch (SQLException ex) {
				Trace.logError(Trace.COMPONENT_MAPPING, "�ļ��������ݿ������ύʧ��", ex);
				throw new TranFailException(LianaConstants.DEFAULT_ERROR_CODE,
						"�ļ��������ݿ������ύʧ��", ex);
			}
		}
	}

}
