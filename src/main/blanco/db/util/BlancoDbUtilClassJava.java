/*
 * blancoDb
 * Copyright (C) 2004-2006 Yasuo Nakanishi
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
package blanco.db.util;

import java.util.List;

import blanco.cg.BlancoCgObjectFactory;
import blanco.cg.valueobject.BlancoCgClass;
import blanco.cg.valueobject.BlancoCgMethod;
import blanco.cg.valueobject.BlancoCgSourceFile;
import blanco.db.common.stringgroup.BlancoDbDriverNameStringGroup;
import blanco.db.common.stringgroup.BlancoDbLoggingModeStringGroup;
import blanco.db.common.util.BlancoDbUtil;
import blanco.db.common.valueobject.BlancoDbSetting;
import blanco.db.expander.query.BlancoPerfomanceCommonUtil;

/**
 * blancoDb�����ʓI�ɗ��p���郆�[�e�B���e�B�N���X�B
 * 
 * ���̃N���X����������N���X��blancoDb�����������\�[�X�R�[�h�ŗ��p����܂�
 * 
 * @since 2006.03.02
 * @author IGA Tosiki
 */
public class BlancoDbUtilClassJava {
    /**
     * ���̃N���X���g�̃N���X��
     */
    public static final String CLASS_NAME = "BlancoDbUtil";

    /**
     * blancoCg �I�u�W�F�N�g�t�@�N�g���B
     */
    private BlancoCgObjectFactory fCgFactory = null;

    /**
     * ���̃N���X���܂܂��\�[�X�R�[�h�B
     */
    private BlancoCgSourceFile fCgSourceFile = null;

    private BlancoDbSetting fDbSetting = null;

    public BlancoDbUtilClassJava(final BlancoCgObjectFactory cgFactory,
            final String argPackage, final BlancoDbSetting argDbSetting) {
        fCgFactory = cgFactory;
        fCgSourceFile = fCgFactory.createSourceFile(argPackage,
                "This code is generated by blanco Framework.");

        this.fDbSetting = argDbSetting;
    }

    public BlancoCgSourceFile expand() {
        final BlancoCgClass cgClass = fCgFactory.createClass(CLASS_NAME, null);
        fCgSourceFile.getClassList().add(cgClass);

        {
            final List<String> listDesc = cgClass.getLangDoc()
                    .getDescriptionList();

            listDesc.add("blancoDb�����ʓI�ɗ��p���郆�[�e�B���e�B�N���X�B");
            listDesc.add("");
            listDesc.add("���̃N���X��blancoDb�����������\�[�X�R�[�h�ŗ��p����܂� <br>");
            listDesc
                    .add("���̃N���X�� blancoDb�����������\�[�X�R�[�h���痘�p����܂��B���ڌĂяo�����Ƃ͐�������܂���B");
            listDesc.add("");
            listDesc.add("@since 2006.03.02");
            listDesc.add("@author blanco Framework");
        }

        {
            boolean fIsSQLServer = false;
            final boolean IS_DEBUG = false;

            fCgSourceFile.getImportList().add(
                    BlancoDbUtil.getRuntimePackage(fDbSetting)
                            + ".exception.IntegrityConstraintException");
            fCgSourceFile.getImportList().add(
                    BlancoDbUtil.getRuntimePackage(fDbSetting)
                            + ".exception.DeadlockException");
            fCgSourceFile.getImportList().add(
                    BlancoDbUtil.getRuntimePackage(fDbSetting)
                            + ".exception.TimeoutException");
            fCgSourceFile.getImportList().add("java.sql.SQLException");

            switch (fDbSetting.getDriverName()) {
            case BlancoDbDriverNameStringGroup.SQLSERVER_2000:
            case BlancoDbDriverNameStringGroup.SQLSERVER_2005:
                fIsSQLServer = true;
                if (IS_DEBUG) {
                    System.out.println("TRACE: SQL Server�ł��B");
                }
                break;
            default:
                if (IS_DEBUG) {
                    System.out.println("TRACE: ��SQL Server�ł��B�h���C�o�l("
                            + fDbSetting.getDriverName() + "]");
                }
                break;
            }

            final BlancoCgMethod cgMethod = fCgFactory.createMethod(
                    "convertToBlancoException", null);
            cgClass.getMethodList().add(cgMethod);

            cgMethod.setStatic(true);

            final List<String> listDesc = cgMethod.getLangDoc()
                    .getDescriptionList();

            listDesc.add("SQL��O��blanco Framework��O�I�u�W�F�N�g�ɕϊ����܂��B<br>");
            listDesc.add("");

            if (fIsSQLServer) {
                listDesc
                        .add("��Microsoft SQL Server 2000/2005�p�̔����ǉ����Đ�������Ă��܂��B<br>");
            }
            listDesc
                    .add("SQL��O�̂Ȃ��ŁAblanco Framework�̗�O�I�u�W�F�N�g�ɕϊ����ׂ����̂ɂ��ĕϊ����܂��B<br>");
            listDesc.add("�ϊ����ׂ��悪�����ꍇ�ɂ́A���̂܂܌��̃I�u�W�F�N�g��ԋp���܂��B");

            cgMethod.getParameterList().add(
                    fCgFactory.createParameter("ex", "java.sql.SQLException",
                            "JDBC����ԋp���ꂽ��O�I�u�W�F�N�g�B"));
            cgMethod
                    .setReturn(fCgFactory
                            .createReturn(
                                    "java.sql.SQLException",
                                    "�ϊ����SQL��O�I�u�W�F�N�g�BSQLException�܂��͂��̌p���N���X�ł��� IntegrityConstraintException, DeadlockException, TimeoutException���߂�܂��B"));

            final List<String> listLine = cgMethod.getLineList();

            listLine.add("if (ex.getSQLState() != null) {");
            listLine.add("if (ex.getSQLState().startsWith(\"23\")) {");
            listLine
                    .add("final IntegrityConstraintException exBlanco = new IntegrityConstraintException(\"�f�[�^�x�[�X����ᔽ�ɂ��ύX�����s���܂����B\" + ex.toString(), ex.getSQLState(), ex.getErrorCode());");
            listLine.add("exBlanco.initCause(ex);");
            listLine.add("return exBlanco;");
            listLine.add("} else if (ex.getSQLState().equals(\"40001\")) {");
            listLine
                    .add("final DeadlockException exBlanco = new DeadlockException(\"�f�[�^�x�[�X�f�b�h���b�N�ɂ��ύX�����s���܂����B\" + ex.toString(), ex.getSQLState(), ex.getErrorCode());");
            listLine.add("exBlanco.initCause(ex);");
            listLine.add("return exBlanco;");
            listLine.add("} else if (ex.getSQLState().equals(\"HYT00\")) {");
            listLine
                    .add("final TimeoutException exBlanco = new TimeoutException(\"�f�[�^�x�[�X�^�C���A�E�g�ɂ��ύX�����s���܂����B\" + ex.toString(), ex.getSQLState(), ex.getErrorCode());");
            listLine.add("exBlanco.initCause(ex);");
            listLine.add("return exBlanco;");

            if (fIsSQLServer) {
                // ���b�N�^�C���A�E�g�ŗL�̔���B
                // ���̏����� SQL Server 2000/2005�ɂ����Ă̂ݗL���ł��B
                // SQL Server 2000/2005�̏ꍇ�ɂ̂݁ALockTimeoutException����������܂��B
                fCgSourceFile.getImportList().add(
                        BlancoDbUtil.getRuntimePackage(fDbSetting)
                                + ".exception.LockTimeoutException");
                listLine
                        .add("} else if (ex.getSQLState().equals(\"HY000\") && ex.getErrorCode() == 1222) {");
                listLine.add("// SQL Server�ŗL�̃��b�N�^�C���A�E�g��O�R�[�h�̔�����s���܂��B");
                listLine
                        .add("final LockTimeoutException exBlanco = new LockTimeoutException(\"�f�[�^�x�[�X���b�N�^�C���A�E�g�ɂ��ύX�����s���܂����B\" + ex.toString(), ex.getSQLState(), ex.getErrorCode());");
                listLine.add("exBlanco.initCause(ex);");
                listLine.add("return exBlanco;");
            }
            listLine.add("}");
            listLine.add("}");
            listLine.add("return ex;");
        }

        {
            final BlancoCgMethod cgMethod = fCgFactory.createMethod(
                    "convertTimestampToDate", "JDBC��Timestamp��Date�^�ɕϊ����܂��B");
            cgClass.getMethodList().add(cgMethod);

            cgMethod.setStatic(true);
            cgMethod.setFinal(true);
            cgMethod.getLangDoc().getDescriptionList().add(
                    "java.sql.Timestamp�^����java.util.Date�^�ւƕϊ����܂��B<br>");
            cgMethod.getLangDoc().getDescriptionList().add(
                    "���̃��\�b�h�� blancoDb�����������\�[�X�R�[�h���痘�p����܂��B���ڌĂяo�����Ƃ͐�������܂���B");

            cgMethod.getParameterList().add(
                    fCgFactory.createParameter("argTimestamp",
                            "java.sql.Timestamp", "JDBC��Timestamp�^��^���܂��B"));
            cgMethod.setReturn(fCgFactory.createReturn("java.util.Date",
                    "�ϊ����java.util.Date�^��߂��܂��B"));

            final List<String> listLine = cgMethod.getLineList();

            listLine.add("if (argTimestamp == null) {");
            listLine.add("return null;");
            listLine.add("}");
            listLine.add("return new Date(argTimestamp.getTime());");
        }

        if (fDbSetting.getLogging()) {
            switch (fDbSetting.getLoggingMode()) {
            case BlancoDbLoggingModeStringGroup.PERFORMANCE:
            case BlancoDbLoggingModeStringGroup.SQLID:
                BlancoPerfomanceCommonUtil.addPerfomanceFieldMethod(fCgFactory,
                        fCgSourceFile, cgClass);
                break;
            }
        }

        return fCgSourceFile;
    }
}