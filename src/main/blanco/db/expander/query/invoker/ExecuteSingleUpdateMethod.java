/*
 * blancoDb
 * Copyright (C) 2004-2006 Yasuo Nakanishi
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
package blanco.db.expander.query.invoker;

import java.util.List;

import blanco.cg.BlancoCgObjectFactory;
import blanco.cg.valueobject.BlancoCgClass;
import blanco.cg.valueobject.BlancoCgMethod;
import blanco.cg.valueobject.BlancoCgSourceFile;
import blanco.db.common.expander.BlancoDbAbstractMethod;
import blanco.db.common.stringgroup.BlancoDbLoggingModeStringGroup;
import blanco.db.common.util.BlancoDbUtil;
import blanco.db.common.valueobject.BlancoDbSetting;
import blanco.db.common.valueobject.BlancoDbSqlInfoStructure;
import blanco.db.expander.exception.NoRowModifiedExceptionClass;
import blanco.db.expander.exception.TooManyRowsModifiedExceptionClass;
import blanco.db.util.BlancoDbCgUtilJava;

/**
 * �ʂ̃��\�b�h��W�J���邽�߂̃N���X�B
 * 
 * @author Tosiki Iga
 */
public class ExecuteSingleUpdateMethod extends BlancoDbAbstractMethod {
    public ExecuteSingleUpdateMethod(final BlancoDbSetting argDbSetting,
            final BlancoDbSqlInfoStructure argSqlInfo,
            final BlancoCgObjectFactory argCgFactory,
            final BlancoCgSourceFile argCgSourceFile,
            final BlancoCgClass argCgClass) {
        super(argDbSetting, argSqlInfo, argCgFactory, argCgSourceFile,
                argCgClass);
    }

    public void expand() {
        final BlancoCgMethod cgMethod = fCgFactory.createMethod(
                "executeSingleUpdate", "SQL�������s���܂��B");
        fCgClass.getMethodList().add(cgMethod);

        cgMethod.getThrowList().add(
                fCgFactory.createException(BlancoDbUtil
                        .getRuntimePackage(fDbSetting)
                        + ".exception.NoRowModifiedException",
                        "�f�[�^�x�[�X�̏����̌��ʁA1�s���f�[�^���ύX����Ȃ������ꍇ�B"));

        cgMethod.getThrowList().add(
                fCgFactory.createException(BlancoDbUtil
                        .getRuntimePackage(fDbSetting)
                        + ".exception.TooManyRowsModifiedException",
                        "�f�[�^�x�[�X�̏����̌��ʁA1�s�𒴂���f�[�^���ύX����Ă��܂����ꍇ�B"));

        BlancoDbCgUtilJava.addExceptionToMethodIntegrityConstraintException(
                fCgFactory, cgMethod, fDbSetting);
        BlancoDbCgUtilJava.addExceptionToMethodDeadlockTimeoutException(
                fCgFactory, cgMethod, fDbSetting);
        BlancoDbCgUtilJava.addExceptionToMethodSqlException(fCgFactory,
                cgMethod);

        cgMethod.getLangDoc().getDescriptionList().add(
                "SQL���̎��s���ʂ�1�s�ł��邱�Ƃ��m�F���܂��B���s���ʂ�1�s�ȊO�ł���ꍇ�ɂ͗�O�𔭐������܂��B<br>");
        cgMethod.getLangDoc().getDescriptionList().add(
                "�V���O���������L���ƂȂ��Ă���̂Ő�������܂��B<br>");

        final List<String> listLine = cgMethod.getLineList();

        if (fDbSetting.getLogging()) {
            switch (fDbSetting.getLoggingMode()) {
            case BlancoDbLoggingModeStringGroup.DEBUG:
                BlancoDbCgUtilJava.addBeginLogToMethod(cgMethod);
            }
        }

        listLine.add("int result = 0;");
        listLine.add("result = executeUpdate();");
        listLine.add("");

        fCgSourceFile.getImportList().add(
                BlancoDbUtil.getRuntimePackage(fDbSetting) + ".exception."
                        + NoRowModifiedExceptionClass.CLASS_NAME);
        fCgSourceFile.getImportList().add(
                BlancoDbUtil.getRuntimePackage(fDbSetting) + ".exception."
                        + TooManyRowsModifiedExceptionClass.CLASS_NAME);

        listLine.add("if (result == 0) {");
        listLine.add("throw new " + NoRowModifiedExceptionClass.CLASS_NAME
                + "(\"�f�[�^�x�[�X�̏����̌��ʁA1�s���f�[�^���ύX����܂���ł����B\");");
        listLine.add("} else if (result > 1) {");
        listLine
                .add("String message = \"�f�[�^�x�[�X�̏����̌��ʁA1�s�𒴂���f�[�^���ύX����܂����B�ύX����:\" + result;");
        listLine.add("throw new "
                + TooManyRowsModifiedExceptionClass.CLASS_NAME + "(message);");
        listLine.add("}");
    }
}