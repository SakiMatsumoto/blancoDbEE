/*
 * blancoDb
 * Copyright (C) 2004-2006 Yasuo Nakanishi
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
package blanco.db.expander.query.iterator;

import java.util.List;

import blanco.cg.BlancoCgObjectFactory;
import blanco.cg.valueobject.BlancoCgClass;
import blanco.cg.valueobject.BlancoCgMethod;
import blanco.cg.valueobject.BlancoCgSourceFile;
import blanco.db.common.expander.BlancoDbAbstractMethod;
import blanco.db.common.stringgroup.BlancoDbLoggingModeStringGroup;
import blanco.db.common.valueobject.BlancoDbSetting;
import blanco.db.common.valueobject.BlancoDbSqlInfoStructure;
import blanco.db.util.BlancoDbCgUtilJava;

/**
 * �ʂ̃��\�b�h��W�J���邽�߂̃N���X�B
 * 
 * �J�[�\��������true�̏ꍇ�ɁA���̃��\�b�h�͍쐬����܂��B
 * 
 * @author Tosiki Iga
 */
public class RelativeMethod extends BlancoDbAbstractMethod {
    public RelativeMethod(final BlancoDbSetting argDbSetting,
            final BlancoDbSqlInfoStructure argSqlInfo,
            final BlancoCgObjectFactory argCgFactory,
            final BlancoCgSourceFile argCgSourceFile,
            final BlancoCgClass argCgClass) {
        super(argDbSetting, argSqlInfo, argCgFactory, argCgSourceFile,
                argCgClass);
    }

    public void expand() {
        final BlancoCgMethod cgMethod = fCgFactory.createMethod("relative",
                null);
        fCgClass.getMethodList().add(cgMethod);

        /*
         * �V���O���������L���ł���ꍇ�ɂ� protected�Ƃ��܂��B
         */
        if (fSqlInfo.getSingle()) {
            cgMethod.setAccess("protected");
        }

        cgMethod.setReturn(fCgFactory.createReturn("boolean",
                "�V�������݂̍s���L���ȏꍇ��true�A����ȏ�̍s���Ȃ��ꍇ��false�B"));

        BlancoDbCgUtilJava.addExceptionToMethodDeadlockTimeoutException(
                fCgFactory, cgMethod, fDbSetting);
        BlancoDbCgUtilJava.addExceptionToMethodSqlException(fCgFactory,
                cgMethod);

        cgMethod
                .getParameterList()
                .add(
                        fCgFactory
                                .createParameter("rows", "int",
                                        "���݂̍s����ړ����鑊�΍s�����w�肵�܂��B���̐��ł̓J�[�\�����������Ɉړ����A���̐��ł̓J�[�\�����t�����Ɉړ����܂��B"));

        cgMethod.getLangDoc().getDescriptionList().add(
                "�J�[�\�������ʃZ�b�g�̑��΍s�������ړ����܂��B");
        cgMethod.getLangDoc().getDescriptionList().add("");
        if (fSqlInfo.getSingle()) {
            cgMethod.getLangDoc().getDescriptionList().add(
                    "�V���O���������L���Ȃ̂ŃX�R�[�v��protected�Ƃ��܂��B<br>");
        }

        cgMethod.getLangDoc().getDescriptionList().add(
                "relative(1)��next()���Ăяo���̂Ɠ����ł��B<br>");
        cgMethod.getLangDoc().getDescriptionList().add(
                "relative(-1)��previous()���Ăяo���̂Ɠ����ł��B<br>");

        final List<String> listLine = cgMethod.getLineList();

        if (fDbSetting.getLogging()) {
            switch (fDbSetting.getLoggingMode()) {
            case BlancoDbLoggingModeStringGroup.DEBUG:
                listLine.add("if (fLog.isDebugEnabled()) {");
                listLine.add("fLog.debug(\"" + cgMethod.getName()
                        + ": rows = \" + rows);");
                listLine.add("}");
                listLine.add("");
            }
        }

        // resultSet�����m�ۂł���΂����A�����I��executeQuery���Ăяo���܂��B
        listLine.add("if (fResultSet == null) {");
        listLine.add("executeQuery();");
        listLine.add("}");

        listLine.add("");
        listLine.add("try {");
        listLine.add("return fResultSet.relative(rows);");
        listLine.add("} catch (SQLException ex) {");
        listLine.add("throw BlancoDbUtil.convertToBlancoException(ex);");
        listLine.add("}");
    }
}