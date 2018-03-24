/*
 * blancoDb
 * Copyright (C) 2004-2006 Yasuo Nakanishi
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
package blanco.db;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import blanco.cg.BlancoCgObjectFactory;
import blanco.cg.BlancoCgTransformer;
import blanco.cg.transformer.BlancoCgTransformerFactory;
import blanco.cg.valueobject.BlancoCgSourceFile;
import blanco.commons.util.BlancoNameAdjuster;
import blanco.commons.util.BlancoStringUtil;
import blanco.db.common.BlancoDbXml2SqlInfo;
import blanco.db.common.IBlancoDbProgress;
import blanco.db.common.stringgroup.BlancoDbDriverNameStringGroup;
import blanco.db.common.stringgroup.BlancoDbSqlInfoTypeStringGroup;
import blanco.db.common.util.BlancoDbUtil;
import blanco.db.common.valueobject.BlancoDbSetting;
import blanco.db.common.valueobject.BlancoDbSqlInfoStructure;
import blanco.db.expander.exception.DeadlockExceptionClass;
import blanco.db.expander.exception.IntegrityConstraintExceptionClass;
import blanco.db.expander.exception.LockTimeoutExceptionClass;
import blanco.db.expander.exception.NoRowFoundExceptionClass;
import blanco.db.expander.exception.NoRowModifiedExceptionClass;
import blanco.db.expander.exception.NotSingleRowExceptionClass;
import blanco.db.expander.exception.TimeoutExceptionClass;
import blanco.db.expander.exception.TooManyRowsFoundExceptionClass;
import blanco.db.expander.exception.TooManyRowsModifiedExceptionClass;
import blanco.db.expander.query.caller.QueryCallerClass;
import blanco.db.expander.query.invoker.QueryInvokerClass;
import blanco.db.expander.query.iterator.QueryIteratorClass;
import blanco.db.util.BlancoDbMappingUtilJava;
import blanco.db.util.BlancoDbUtilClassJava;
import blanco.dbmetadata.valueobject.BlancoDbMetaDataColumnStructure;
import blanco.valueobject.BlancoValueObjectXml2JavaClass;
import blanco.valueobject.valueobject.BlancoValueObjectClassStructure;
import blanco.valueobject.valueobject.BlancoValueObjectFieldStructure;

/**
 * ����XML�t�@�C������\�[�X�R�[�h�𐶐����܂��B
 */
public abstract class BlancoDbXml2JavaClass implements IBlancoDbProgress {
    private BlancoDbSetting fDbSetting = null;

    /**
     * XML�t�@�C������\�[�X�R�[�h�𐶐����܂��B
     * 
     * @param connDef
     *            �f�[�^�x�[�X�ڑ����B
     * @param blancoSqlDirectory
     *            SQL XML�t�@�C�����i�[����Ă���f�B���N�g���B
     * @param rootPackage
     *            ���[�g�ƂȂ��p�b�P�[�W�B
     * @param runtimePackage
     *            blanco�ɐݒ肷�郉���^�C���p�b�P�[�W�Bnull�Ȃ�f�t�H���g�ɏo�́B
     * @param statementTimeout
     *            �X�e�[�g�����g�^�C���A�E�g�l�B
     * @param blancoTargetSourceDirectory
     *            �o�͐�f�B���N�g���B
     * @throws SQLException
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws ClassNotFoundException
     * @throws TransformerException
     */
    public void process(final BlancoDbSetting argDbSetting,
            final File blancoSqlDirectory) throws SQLException, SAXException,
            IOException, ParserConfigurationException, ClassNotFoundException,
            TransformerException {
        System.out.println(BlancoDbConstants.PRODUCT_NAME + " ("
                + BlancoDbConstants.VERSION + ") �\�[�X�R�[�h����: �J�n.");

        fDbSetting = argDbSetting;

        if (BlancoStringUtil.null2Blank(fDbSetting.getRuntimePackage()).trim()
                .length() == 0) {
            fDbSetting.setRuntimePackage(null);
        }

        Connection conn = null;
        try {
            conn = BlancoDbUtil.connect(fDbSetting);
            BlancoDbUtil.getDatabaseVersionInfo(conn, fDbSetting);

            if (blancoSqlDirectory != null) {
                // �w�肪����ꍇ�ɂ̂� SQL��`���t�@�C���i�[�f�B���N�g�����������܂��B

                // ValueObject�����i�[����f�B���N�g�����쐬���܂��B
                new File(blancoSqlDirectory.getAbsolutePath() + "/valueobject")
                        .mkdirs();

                final File[] fileSettingXml = blancoSqlDirectory.listFiles();
                for (int index = 0; index < fileSettingXml.length; index++) {
                    if (fileSettingXml[index].getName().endsWith(".xml") == false) {
                        // �t�@�C���̊g���q�� xml �ł�����̂̂ݏ������܂��B
                        continue;
                    }
                    if (progress(index + 1, fileSettingXml.length,
                            fileSettingXml[index].getName()) == false) {
                        break;
                    }

                    try {
                        // �����̓t�@�C�����ɍs���܂��B
                        processEveryFile(conn, fileSettingXml[index], new File(
                                blancoSqlDirectory.getAbsolutePath()
                                        + "/valueobject"));
                    } catch (IllegalArgumentException ex) {
                        if (argDbSetting.getFailonerror()) {
                            // SQL ��`���̏����ɂ����ė�O�����������̂ŏ������f���܂��B
                            throw ex;
                        } else {
                            // �W���G���[�o�͂ɃG���[�\���̏㏈�����s���܂��B
                            System.err.println("SQL ��`����O: " + ex.getMessage());
                        }
                    }
                }
            }

        } finally {
            BlancoDbUtil.close(conn);
            conn = null;
            System.out.println("�\�[�X�R�[�h����: �I��.");
        }
    }

    /**
     * �ʂ�XML�t�@�C�����������܂��B
     * 
     * @param dbInfoCollector
     * @param rootPackage
     * @param fileSqlForm
     * @param outputDirectory
     * @throws IOException
     * @throws SAXException
     * @throws TransformerException
     * @throws SQLException
     * @throws ParserConfigurationException
     */
    private void processEveryFile(final Connection conn,
            final File fileSqlForm, final File outputDirectory)
            throws IOException, SAXException, TransformerException,
            SQLException, ParserConfigurationException {

        System.out.println("�t�@�C��[" + fileSqlForm.getAbsolutePath() + "]���������܂�");

        final BlancoDbXml2SqlInfo collector = new BlancoDbXml2SqlInfo();
        final List<BlancoDbSqlInfoStructure> definition = collector.process(
                conn, fDbSetting, fileSqlForm);

        final String packageNameException = BlancoDbUtil
                .getRuntimePackage(fDbSetting) + ".exception";

        // �]���ƌ݊������������邽�߁A/main�T�u�t�H���_�ɏo�͂��܂��B
        final File fileBlancoMain = new File(fDbSetting.getTargetDir()
                + "/main");

        final BlancoCgObjectFactory cgFactory = BlancoCgObjectFactory
                .getInstance();

        final BlancoCgTransformer transformer = BlancoCgTransformerFactory
                .getJavaSourceTransformer();

        // exception�n
        transformer.transform(adjust(new DeadlockExceptionClass(cgFactory,
                packageNameException).expand()), fileBlancoMain);
        transformer.transform(adjust(new IntegrityConstraintExceptionClass(
                cgFactory, packageNameException).expand()), fileBlancoMain);
        transformer.transform(adjust(new NoRowFoundExceptionClass(cgFactory,
                packageNameException).expand()), fileBlancoMain);
        transformer.transform(adjust(new NoRowModifiedExceptionClass(cgFactory,
                packageNameException).expand()), fileBlancoMain);
        transformer.transform(adjust(new NotSingleRowExceptionClass(cgFactory,
                packageNameException).expand()), fileBlancoMain);
        transformer.transform(adjust(new TimeoutExceptionClass(cgFactory,
                packageNameException).expand()), fileBlancoMain);
        transformer.transform(adjust(new TooManyRowsFoundExceptionClass(
                cgFactory, packageNameException).expand()), fileBlancoMain);
        transformer.transform(adjust(new TooManyRowsModifiedExceptionClass(
                cgFactory, packageNameException).expand()), fileBlancoMain);

        switch (fDbSetting.getDriverName()) {
        case BlancoDbDriverNameStringGroup.SQLSERVER_2000:
        case BlancoDbDriverNameStringGroup.SQLSERVER_2005:
            // SQL Server 2000/2005�̏ꍇ�ɂ̂݁ALockTimeoutException�N���X�𐶐����܂��B
            transformer.transform(adjust(new LockTimeoutExceptionClass(
                    cgFactory, packageNameException).expand()), fileBlancoMain);
            break;
        default:
            break;
        }

        // util�n
        transformer.transform(adjust(new BlancoDbUtilClassJava(cgFactory,
                BlancoDbUtil.getRuntimePackage(fDbSetting) + ".util",
                fDbSetting).expand()), fileBlancoMain);

        // iterator, invoker, caller
        for (int index = 0; index < definition.size(); index++) {
            final BlancoDbSqlInfoStructure sqlInfo = definition.get(index);
            switch (sqlInfo.getType()) {
            case BlancoDbSqlInfoTypeStringGroup.ITERATOR:
                createRowObjectClass(
                        BlancoDbUtil.getBasePackage(sqlInfo, fDbSetting),
                        sqlInfo, outputDirectory, fDbSetting);

                transformer.transform(adjust(new QueryIteratorClass(fDbSetting,
                        sqlInfo, cgFactory).expand()), fileBlancoMain);
                break;
            case BlancoDbSqlInfoTypeStringGroup.INVOKER:
                transformer.transform(adjust(new QueryInvokerClass(fDbSetting,
                        sqlInfo, cgFactory).expand()), fileBlancoMain);
                break;
            case BlancoDbSqlInfoTypeStringGroup.CALLER:
                transformer.transform(adjust(new QueryCallerClass(fDbSetting,
                        sqlInfo, cgFactory).expand()), fileBlancoMain);
                break;
            default:
                throw new IllegalArgumentException(
                        "�z��O�̃G���[�B�s���ȃN�G���I�u�W�F�N�g���^�����܂����B" + sqlInfo.toString());
            }
        }
    }

    /**
     * �s�I�u�W�F�N�g���쐬���܂��B
     * 
     * @param className
     * @param packageName
     * @param sqlInfo
     * @param outputDirectory
     * @throws SAXException
     * @throws IOException
     * @throws TransformerException
     */
    public static void createRowObjectClass(final String rootPackage,
            final BlancoDbSqlInfoStructure sqlInfo, final File outputDirectory, final BlancoDbSetting dbSetting)
            throws SAXException, IOException, TransformerException {
        final String packageName = rootPackage + ".row";
        final String className = BlancoNameAdjuster.toClassName(sqlInfo
                .getName()) + "Row";

        final List<String[]> listFieldTypes = new ArrayList<String[]>();
        for (int index = 0; index < sqlInfo.getResultSetColumnList().size(); index++) {
            final BlancoDbMetaDataColumnStructure columnStructure = sqlInfo
                    .getResultSetColumnList().get(index);

            try {
                listFieldTypes.add(new String[] {
                        columnStructure.getName(),
                        BlancoDbMappingUtilJava
                                .getFullClassName(columnStructure) });
            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException("SQL��`[" + sqlInfo.getName()
                        + "] ���ږ�[" + columnStructure.getName()
                        + "] �f�[�^�\�[�X�ˑ��̌^��[" + columnStructure.getTypeName()
                        + "] �������ł��܂���B:" + ex.toString(), ex);
            }
        }

        final BlancoValueObjectClassStructure voClass = new BlancoValueObjectClassStructure();

        voClass.setName(className);
        voClass.setPackage(packageName);
        voClass.setDescription("SQL��`��(blancoDb)����쐬���ꂽ�s�N���X�B");
        voClass.getDescriptionList().add("'" + className + "'�s��\�����܂��B");
        for (int index = 0; index < listFieldTypes.size(); index++) {
            final String[] columnTypes = listFieldTypes.get(index);
            final String columnName = columnTypes[0];
            final String columnType = columnTypes[1];

            voClass.getDescriptionList().add(
                    "(" + String.valueOf(index + 1) + ") '" + columnName
                            + "'�� �^:" + columnType);
        }

        for (int index = 0; index < listFieldTypes.size(); index++) {
            final String[] columnTypes = listFieldTypes.get(index);
            final String columnName = columnTypes[0];
            final String columnType = columnTypes[1];

            final BlancoValueObjectFieldStructure voField = new BlancoValueObjectFieldStructure();
            voField.setName(columnName);
            voField.setType(columnType);
            voField.setDescription("�t�B�[���h[" + columnName + "]�ł��B");
            voClass.getFieldList().add(voField);
        }

        final BlancoValueObjectXml2JavaClass xml2javaclass = new BlancoValueObjectXml2JavaClass();
        xml2javaclass.setEncoding(dbSetting.getEncoding());
        if (dbSetting.getTargetDir() == null) {
            throw new IllegalArgumentException(
                    "BlancoDbGenerator: blanco�o�͐�t�H���_�����ݒ�(null)�ł��B");
        }
        xml2javaclass.structure2Source(voClass,
                new File(dbSetting.getTargetDir()));
    }

    /**
     * �\�[�X�E�I�u�W�F�N�g�̓��e�𒲐��B
     * 
     * <UL>
     * <LI>�\�[�X�R�[�h�̃G���R�[�f�B���O��ݒ�B
     * </UL>
     * 
     * @param arg
     * @return
     */
    private BlancoCgSourceFile adjust(final BlancoCgSourceFile arg) {
        if (BlancoStringUtil.null2Blank(fDbSetting.getEncoding()).length() > 0) {
            arg.setEncoding(fDbSetting.getEncoding());
        }
        return arg;
    }
}
