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
import blanco.commons.util.BlancoNameAdjuster;
import blanco.commons.util.BlancoNameUtil;
import blanco.db.common.expander.BlancoDbAbstractMethod;
import blanco.db.common.stringgroup.BlancoDbLoggingModeStringGroup;
import blanco.db.common.util.BlancoDbUtil;
import blanco.db.common.valueobject.BlancoDbSetting;
import blanco.db.common.valueobject.BlancoDbSqlInfoStructure;
import blanco.db.expander.exception.NoRowFoundExceptionClass;
import blanco.db.expander.exception.TooManyRowsFoundExceptionClass;
import blanco.db.util.BlancoDbCgUtilJava;

/**
 * �ʂ̃��\�b�h��W�J���邽�߂̃N���X�B
 * 
 * �V���O��������true�̏ꍇ�ɂ̂݁A���̃N���X�͗��p����܂�
 * 
 * @author Tosiki Iga
 */
public class GetSingleRowMethod extends BlancoDbAbstractMethod {
    public GetSingleRowMethod(final BlancoDbSetting argDbSetting,
            final BlancoDbSqlInfoStructure argSqlInfo,
            final BlancoCgObjectFactory argCgFactory,
            final BlancoCgSourceFile argCgSourceFile,
            final BlancoCgClass argCgClass) {
        super(argDbSetting, argSqlInfo, argCgFactory, argCgSourceFile,
                argCgClass);
    }

    public void expand() {
        final BlancoCgMethod cgMethod = fCgFactory.createMethod("getSingleRow",
                "���݂̍s�̃f�[�^���I�u�W�F�N�g�Ƃ��Ď擾���܂��B");
        fCgClass.getMethodList().add(cgMethod);

        // �s�I�u�W�F�N�g�̌^�����擾���܂��B
        final String rowObjectType = BlancoDbUtil.getBasePackage(fSqlInfo,
                fDbSetting)
                + ".row."
                + BlancoNameAdjuster.toClassName(fSqlInfo.getName())
                + "Row";

        cgMethod.setReturn(fCgFactory.createReturn(rowObjectType, "�s�I�u�W�F�N�g�B"));

        cgMethod.getThrowList().add(
                fCgFactory.createException(BlancoDbUtil
                        .getRuntimePackage(fDbSetting)
                        + ".exception.NoRowFoundException",
                        "�f�[�^�x�[�X�̏����̌��ʁA1�s���f�[�^����������Ȃ������ꍇ�B"));
        cgMethod.getThrowList().add(
                fCgFactory.createException(BlancoDbUtil
                        .getRuntimePackage(fDbSetting)
                        + ".exception.TooManyRowsFoundException",
                        "�f�[�^�x�[�X�̏����̌��ʁA1�s�𒴂���f�[�^����������Ă��܂����ꍇ�B"));

        BlancoDbCgUtilJava.addExceptionToMethodSqlException(fCgFactory,
                cgMethod);

        final List<String> listDesc = cgMethod.getLangDoc()
                .getDescriptionList();

        listDesc.add("SQL���̎��s���ʂ�1�s�ł��邱�Ƃ��m�F���܂��B���s���ʂ�1�s�ȊO�ł���ꍇ�ɂ͗�O�𔭐������܂��B<br>");
        listDesc.add("�V���O���������L���ƂȂ��Ă���̂Ő�������܂��B<br>");

        final List<String> listLine = cgMethod.getLineList();

        if (fDbSetting.getLogging()) {
            switch (fDbSetting.getLoggingMode()) {
            case BlancoDbLoggingModeStringGroup.DEBUG:
                BlancoDbCgUtilJava.addBeginLogToMethod(cgMethod);
            }
        }

        fCgSourceFile.getImportList().add(
                BlancoDbUtil.getRuntimePackage(fDbSetting) + ".exception."
                        + NoRowFoundExceptionClass.CLASS_NAME);

        listLine.add("if (next() == false) {");
        listLine
                .add("throw new NoRowFoundException(\"�f�[�^�x�[�X�̏����̌��ʁA1�s���f�[�^����������܂���ł����B\");");
        listLine.add("}");
        listLine.add("");

        listLine.add(BlancoNameUtil.trimJavaPackage(rowObjectType)
                + " result = getRow();");
        listLine.add("");

        fCgSourceFile.getImportList().add(
                BlancoDbUtil.getRuntimePackage(fDbSetting) + ".exception."
                        + TooManyRowsFoundExceptionClass.CLASS_NAME);

        // 1�s�𒴂��ĕύX�����������ǂ������`�F�b�N�B
        listLine.add("if (next()) {");
        listLine
                .add("throw new TooManyRowsFoundException(\"�f�[�^�x�[�X�̏����̌��ʁA1�s�𒴂���f�[�^����������܂����B\");");
        listLine.add("}");
        listLine.add("");

        listLine.add("return result;");
    }
}