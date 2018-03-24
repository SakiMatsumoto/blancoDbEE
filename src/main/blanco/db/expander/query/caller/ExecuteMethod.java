/*
 * blancoDb
 * Copyright (C) 2004-2006 Yasuo Nakanishi
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
package blanco.db.expander.query.caller;

import java.util.List;

import blanco.cg.BlancoCgObjectFactory;
import blanco.cg.valueobject.BlancoCgClass;
import blanco.cg.valueobject.BlancoCgMethod;
import blanco.cg.valueobject.BlancoCgSourceFile;
import blanco.commons.util.BlancoJavaSourceUtil;
import blanco.commons.util.BlancoStringUtil;
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
public class ExecuteMethod extends BlancoDbAbstractMethod {
    public ExecuteMethod(final BlancoDbSetting argDbSetting,
            final BlancoDbSqlInfoStructure argSqlInfo,
            final BlancoCgObjectFactory argCgFactory,
            final BlancoCgSourceFile argCgSourceFile,
            final BlancoCgClass argCgClass) {
        super(argDbSetting, argSqlInfo, argCgFactory, argCgSourceFile,
                argCgClass);
    }

    public void expand() {
        final BlancoCgMethod cgMethod = fCgFactory
                .createMethod("execute", null);
        fCgClass.getMethodList().add(cgMethod);

        BlancoDbCgUtilJava.addExceptionToMethodIntegrityConstraintException(
                fCgFactory, cgMethod, fDbSetting);
        BlancoDbCgUtilJava.addExceptionToMethodDeadlockTimeoutException(
                fCgFactory, cgMethod, fDbSetting);
        BlancoDbCgUtilJava.addExceptionToMethodSqlException(fCgFactory,
                cgMethod);

        cgMethod.getLangDoc().getDescriptionList().add("�X�g�A�h�v���V�[�W�������s���܂��B<br>");

        cgMethod.setReturn(fCgFactory.createReturn("boolean",
                "CallableStatement.execute���\�b�h�̖߂�l�B"));

        final List<String> listLine = cgMethod.getLineList();

		if (fDbSetting.getLoggingsql()) {
			// �W���o�͂ɏo�́B
			listLine.add("System.out.println(\"SQL: ["
					+ fSqlInfo.getName()
					+ "](Caller) "
					+ BlancoJavaSourceUtil
							.escapeStringAsJavaSource(BlancoStringUtil
									.null2Blank(fSqlInfo.getDescription()))
					+ ": \" + fLogSqlInParam + \": ["
					+ (fSqlInfo.getDynamicSql() == false ? BlancoJavaSourceUtil.escapeStringAsJavaSource(fSqlInfo
							.getQuery().replace('\n', ' ')) : "\" + (\"\" + fLogSqlDynamicSql).replace('\\n', ' ') + \"") + "]\");");
		}

        if (fDbSetting.getLogging()) {
            switch (fDbSetting.getLoggingMode()) {
            case BlancoDbLoggingModeStringGroup.DEBUG:
                BlancoDbCgUtilJava.addBeginLogToMethod(cgMethod);
            }
        }

        listLine.add("if (fStatement == null) {");
        listLine
                .add("// CallableStatement�����擾�̏�ԂȂ̂ŁACallableStatement.execute()���s�ɐ旧��prepareCall()���\�b�h���Ăяo���Ď擾���܂��B");
        listLine.add("prepareCall();");
        listLine.add("}");

        // ��O�������܂߂ēW�J���܂��B
        listLine.add("");
        listLine.add("// execute���\�b�h�̎��s���ʂɂ��Ă͈���Ȃ����ƂƂ��܂��B");
        listLine.add("try {");
        listLine.add("return fStatement.execute();");
        listLine.add("} catch (SQLException ex) {");
        listLine.add("throw BlancoDbUtil.convertToBlancoException(ex);");
        listLine.add("}");
    }
}