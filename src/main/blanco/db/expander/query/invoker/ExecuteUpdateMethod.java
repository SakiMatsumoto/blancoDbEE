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
public class ExecuteUpdateMethod extends BlancoDbAbstractMethod {
    public ExecuteUpdateMethod(final BlancoDbSetting argDbSetting,
            final BlancoDbSqlInfoStructure argSqlInfo,
            final BlancoCgObjectFactory argCgFactory,
            final BlancoCgSourceFile argCgSourceFile,
            final BlancoCgClass argCgClass) {
        super(argDbSetting, argSqlInfo, argCgFactory, argCgSourceFile,
                argCgClass);
    }

    public void expand() {
        final BlancoCgMethod cgMethod = fCgFactory.createMethod(
                "executeUpdate", "SQL�������s���܂��B");
        fCgClass.getMethodList().add(cgMethod);

        cgMethod.setReturn(fCgFactory.createReturn("int", "�������ꂽ�s��"));

        /*
         * �V���O���������L���ł���ꍇ�ɂ� protected�Ƃ��܂��B
         */
        if (fSqlInfo.getSingle()) {
            cgMethod.setAccess("protected");
        } else {
            // public�̂܂܂ł��B
        }

        BlancoDbCgUtilJava.addExceptionToMethodIntegrityConstraintException(
                fCgFactory, cgMethod, fDbSetting);
        BlancoDbCgUtilJava.addExceptionToMethodDeadlockTimeoutException(
                fCgFactory, cgMethod, fDbSetting);
        BlancoDbCgUtilJava.addExceptionToMethodSqlException(fCgFactory,
                cgMethod);

        if (fSqlInfo.getSingle()) {
            cgMethod.getLangDoc().getDescriptionList().add(
                    "�V���O���������L���Ȃ̂ŃX�R�[�v��protected�Ƃ��܂��B<br>");
            cgMethod.getLangDoc().getDescriptionList().add(
                    "���̃��\�b�h�̑���� executeSingleUpdate���\�b�h�𗘗p���Ă��������B<br>");
        }

        final List<String> listLine = cgMethod.getLineList();

        if (fDbSetting.getLogging()) {
            switch (fDbSetting.getLoggingMode()) {
            case BlancoDbLoggingModeStringGroup.DEBUG:
                BlancoDbCgUtilJava.addBeginLogToMethod(cgMethod);
            }
        }

        // statement�����m�ۂł���΂����A�����I��prepareStatement���Ăяo���܂��B
        listLine.add("if (fStatement == null) {");
        listLine
                .add("// PreparedStatement�����擾�̏�ԂȂ̂ŁAPreparedStatement.executeUpdate()���s�ɐ旧��prepareStatement()���\�b�h���Ăяo���Ď擾���܂��B");
        listLine.add("prepareStatement();");
        listLine.add("}");

        listLine.add("");

        if(fDbSetting.getLoggingsql()) {
        	// �W���o�͂ɏo�́B 
			listLine.add("System.out.println(\"SQL: ["
					+ fSqlInfo.getName()
					+ "](Invoker) "
					+ BlancoJavaSourceUtil
							.escapeStringAsJavaSource(BlancoStringUtil
									.null2Blank(fSqlInfo.getDescription()))
					+ ": \" + fLogSqlInParam + \": ["
					+ (fSqlInfo.getDynamicSql() == false ? BlancoJavaSourceUtil.escapeStringAsJavaSource(fSqlInfo
							.getQuery().replace('\n', ' ')) : "\" + (\"\" + fLogSqlDynamicSql).replace('\\n', ' ') + \"") + "]\");");
		}

        if (fDbSetting.getLogging()) {
            switch (fDbSetting.getLoggingMode()) {
            case BlancoDbLoggingModeStringGroup.PERFORMANCE:
            case BlancoDbLoggingModeStringGroup.SQLID:
                listLine.add("final Runtime runtime = Runtime.getRuntime();");
                listLine
                        .add("final long usedMemoryStart = BlancoDbUtil.getUsedMemory(runtime);");
                listLine
                        .add("final long startTime = System.currentTimeMillis();");
                listLine.add("fLog.info(\"" + fSqlInfo.getName() + "�J�n\");");
                break;
            }
            listLine.add("");
        }

        // ��O�������܂߂ēW�J���܂��B
        listLine.add("try {");
        listLine.add("return fStatement.executeUpdate();");
        listLine.add("} catch (SQLException ex) {");
        listLine.add("throw BlancoDbUtil.convertToBlancoException(ex);");

        if (fDbSetting.getLogging()) {
            switch (fDbSetting.getLoggingMode()) {
            case BlancoDbLoggingModeStringGroup.PERFORMANCE:
            case BlancoDbLoggingModeStringGroup.SQLID:
                listLine.add("} finally {");
                listLine
                        .add("final long endTime = System.currentTimeMillis();");
                listLine
                        .add("final long usedMemoryEnd = BlancoDbUtil.getUsedMemory(runtime);");
                listLine
                        .add("fLog.info(\""
                                + fSqlInfo.getName()
                                + "�I�� ���v���ԁF\" + BlancoDbUtil.getTimeString(endTime - startTime) + \" �I�����g�p�������F\" + BlancoDbUtil.getMemorySizeString(usedMemoryEnd) + \" �g�p�����������F\" + BlancoDbUtil.getMemorySizeString(usedMemoryEnd - usedMemoryStart));");
                break;
            }
        }

        listLine.add("}");
    }
}