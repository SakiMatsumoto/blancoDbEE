package blanco.db.task;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import blanco.commons.util.BlancoStringUtil;
import blanco.db.BlancoDbConstants;
import blanco.db.BlancoDbXml2JavaClass;
import blanco.db.common.BlancoDbMeta2Xml;
import blanco.db.common.BlancoDbTableMeta2Xml;
import blanco.db.common.stringgroup.BlancoDbExecuteSqlStringGroup;
import blanco.db.common.stringgroup.BlancoDbLoggingModeStringGroup;
import blanco.db.common.valueobject.BlancoDbSetting;
import blanco.db.resourcebundle.BlancoDbResourceBundle;
import blanco.db.task.valueobject.BlancoDbProcessInput;

public class BlancoDbProcessImpl implements BlancoDbProcess {
    /**
     * ���\�[�X�o���h���A�N�Z�T�I�u�W�F�N�g���L�����܂��B
     */
    private final BlancoDbResourceBundle fBundle = new BlancoDbResourceBundle();

    public int execute(final BlancoDbProcessInput input) throws IOException,
            IllegalArgumentException {
        System.out.println("- " + BlancoDbConstants.PRODUCT_NAME + " ("
                + BlancoDbConstants.VERSION + ")");

        try {
            System.out.println("db: begin.");
            final long startMills = System.currentTimeMillis();

            final File blancoTmpDbTableDirectory = new File(input.getTmpdir()
                    + "/db/table");
            final File blancoTmpDbSqlDirectory = new File(input.getTmpdir()
                    + "/db/sql");
            blancoTmpDbTableDirectory.mkdirs();
            blancoTmpDbSqlDirectory.mkdirs();

            final BlancoDbSetting dbSetting = new BlancoDbSetting();
            dbSetting.setTargetDir(input.getTargetdir());
            dbSetting.setBasePackage(input.getBasepackage());
            dbSetting.setRuntimePackage(input.getRuntimepackage());

            dbSetting.setJdbcdriver(input.getJdbcdriver());
            dbSetting.setJdbcurl(input.getJdbcurl());
            dbSetting.setJdbcuser(input.getJdbcuser());
            dbSetting.setJdbcpassword(input.getJdbcpassword());
            if (BlancoStringUtil.null2Blank(input.getJdbcdriverfile()).length() > 0) {
                dbSetting.setJdbcdriverfile(input.getJdbcdriverfile());
            }
            dbSetting.setEncoding(input.getEncoding());

            if ("true".equals(input.getConvertStringToMsWindows31jUnicode())) {
                dbSetting.setConvertStringToMsWindows31jUnicode(true);
            }
            
            // �������� SQL ��`���̗�O�����������ꍇ�ɏ������f���邩�ǂ����B 
            dbSetting.setFailonerror("true".equals(input.getFailonerror()));

            if (input.getLog().equals("true")) {
                dbSetting.setLogging(true);
                dbSetting.setLoggingMode(new BlancoDbLoggingModeStringGroup()
                        .convertToInt(input.getLogmode()));
                if (dbSetting.getLoggingMode() == BlancoDbLoggingModeStringGroup.NOT_DEFINED) {
                    throw new IllegalArgumentException("���M���O���[�h�Ƃ��Ďw�肳�ꂽ�l["
                            + input.getLogmode() + "]�̓T�|�[�g����܂���B�������f���܂��B");
                }
            }
			if (input.getLogsql().equals("true")) {
				// �ǐ��̍������M���O��L�������܂��B
				dbSetting.setLoggingsql(true);
			}
            if (BlancoStringUtil.null2Blank(input.getStatementtimeout())
                    .length() > 0) {
                try {
                    dbSetting.setStatementTimeout(Integer.parseInt(input
                            .getStatementtimeout()));
                } catch (NumberFormatException ex) {
                    throw new IllegalArgumentException(
                            "�X�e�[�g�����g�^�C���A�E�g�l�Ƃ��Ďw�肳�ꂽ�l["
                                    + input.getStatementtimeout()
                                    + "]�͐��l�Ƃ��ĉ�͂ł��܂���ł����B�������f���܂��B:"
                                    + ex.toString());
                }
            }
            dbSetting.setExecuteSql(new BlancoDbExecuteSqlStringGroup()
                    .convertToInt(input.getExecutesql()));
            if (dbSetting.getExecuteSql() == BlancoDbExecuteSqlStringGroup.NOT_DEFINED) {
                throw new IllegalArgumentException("executesql�Ƃ��ĕs���Ȓl("
                        + input.getExecutesql() + ")���^�����܂����B");
            }

            if (input.getSchema() != null) {
                // �X�L�[�}�����w��B
                dbSetting.setSchema(input.getSchema());
            }

            if (input.getTable() == null || input.getTable().equals("true")) {
                // �P��\�A�N�Z�X����������
                final BlancoDbTableMeta2Xml tableMeta2Xml = new BlancoDbTableMeta2Xml() {
                    public boolean progress(int progressCurrent,
                            int progressTotal, String progressItem) {
                        // ���true��Ԃ��܂��B
                        return true;
                    }
                };
                tableMeta2Xml.process(dbSetting, blancoTmpDbTableDirectory);

                // XML�t�@�C��������R/O�}�b�s���O����������
                final BlancoDbXml2JavaClass generator = new BlancoDbXml2JavaClass() {
                    public boolean progress(int progressCurrent,
                            int progressTotal, String progressItem) {
                        // ���true��Ԃ��܂��B
                        return true;
                    }
                };

                generator.process(dbSetting, blancoTmpDbTableDirectory);
            }

            if (input.getSql() == null || input.getSql().equals("true")) {
                final File fileMetadir = new File(input.getMetadir());
                if (fileMetadir.exists() == false) {
                    throw new IllegalArgumentException("���^�f�B���N�g��["
                            + input.getMetadir() + "]�����݂��܂���B");
                }

                final BlancoDbMeta2Xml meta2Xml = new BlancoDbMeta2Xml();
                meta2Xml.setCacheMeta2Xml(input.getCache().equals("true"));
                meta2Xml.processDirectory(fileMetadir, blancoTmpDbSqlDirectory
                        .getAbsolutePath());

                // XML�t�@�C��������R/O�}�b�s���O����������
                final BlancoDbXml2JavaClass generator = new BlancoDbXml2JavaClass() {
                    public boolean progress(int progressCurrent,
                            int progressTotal, String progressItem) {
                        // ���true��Ԃ��܂��B
                        return true;
                    }
                };
                generator.process(dbSetting, blancoTmpDbSqlDirectory);
            }

            final long endMills = System.currentTimeMillis() - startMills;
            System.out.println("db: end: " + (endMills / 1000) + " sec.");
        } catch (SQLException e) {
            throw new IllegalArgumentException(fBundle.getTaskErr001()
                    + e.toString());
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(fBundle.getTaskErr002()
                    + e.toString());
        } catch (SAXException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(fBundle.getTaskErr003()
                    + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(fBundle.getTaskErr004()
                    + e.toString());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(fBundle.getTaskErr005()
                    + e.toString());
        } catch (TransformerException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(fBundle.getTaskErr006()
                    + e.toString());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("���͒l�G���[:" + e.toString());
        }
        return BlancoDbBatchProcess.END_SUCCESS;
    }

    /**
     * {@inheritDoc}
     */
    public boolean progress(final String argProgressMessage) {
        System.out.println(argProgressMessage);
        return false;
    }
}
