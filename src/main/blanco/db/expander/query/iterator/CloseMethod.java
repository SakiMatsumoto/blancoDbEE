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
 * @author Yasuo Nakanishi
 */
public class CloseMethod extends BlancoDbAbstractMethod {
    public CloseMethod(final BlancoDbSetting argDbSetting,
            final BlancoDbSqlInfoStructure argSqlInfo,
            final BlancoCgObjectFactory argCgFactory,
            final BlancoCgSourceFile argCgSourceFile,
            final BlancoCgClass argCgClass) {
        super(argDbSetting, argSqlInfo, argCgFactory, argCgSourceFile,
                argCgClass);
    }

    public void expand() {
        final BlancoCgMethod cgMethod = fCgFactory.createMethod("close",
                "���̃N���X�̃N���[�Y�����������Ȃ��܂��B");
        fCgClass.getMethodList().add(cgMethod);

        BlancoDbCgUtilJava.addExceptionToMethodSqlException(fCgFactory,
                cgMethod);

        cgMethod.getLangDoc().getDescriptionList().add(
                "�����I�ɐ������Ă���JDBC���\�[�X�̃I�u�W�F�N�g�ɑ΂��� close()���\�b�h�̌Ăяo���������Ȃ��܂��B<br>");
        cgMethod.getLangDoc().getDescriptionList().add(
                "�N���X�̗��p���I�������A�K�����̃��\�b�h���Ăяo���悤�ɂ��܂��B");

        final List<String> listLine = cgMethod.getLineList();

        if (fDbSetting.getLogging()) {
            switch (fDbSetting.getLoggingMode()) {
            case BlancoDbLoggingModeStringGroup.DEBUG:
                BlancoDbCgUtilJava.addBeginLogToMethod(cgMethod);
            }
        }

        listLine.add("try {");
        listLine.add("if (fResultSet != null) {");
        listLine.add("fResultSet.close();");
        listLine.add("fResultSet = null;");
        listLine.add("}");
        listLine.add("} finally {");
        listLine.add("if (fStatement != null) {");
        listLine.add("fStatement.close();");
        listLine.add("fStatement = null;");
        listLine.add("}");
        listLine.add("}");
    }
}