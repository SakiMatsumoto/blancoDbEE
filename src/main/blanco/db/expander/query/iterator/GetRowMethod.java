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

import java.sql.Types;
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
import blanco.db.util.BlancoDbCgUtilJava;
import blanco.db.util.BlancoDbMappingUtilJava;
import blanco.dbmetadata.valueobject.BlancoDbMetaDataColumnStructure;

/**
 * �ʂ̃��\�b�h��W�J���邽�߂̃N���X�B
 * 
 * @author Tosiki Iga
 */
public class GetRowMethod extends BlancoDbAbstractMethod {
    public GetRowMethod(final BlancoDbSetting argDbSetting,
            final BlancoDbSqlInfoStructure argSqlInfo,
            final BlancoCgObjectFactory argCgFactory,
            final BlancoCgSourceFile argCgSourceFile,
            final BlancoCgClass argCgClass) {
        super(argDbSetting, argSqlInfo, argCgFactory, argCgSourceFile,
                argCgClass);
    }

    public void expand() {
        final BlancoCgMethod cgMethod = fCgFactory.createMethod("getRow",
                "���݂̍s�̃f�[�^���I�u�W�F�N�g�Ƃ��Ď擾���܂��B");
        fCgClass.getMethodList().add(cgMethod);

        /*
         * �V���O���������L���ł���ꍇ�ɂ� protected�Ƃ��܂��B
         */
        if (fSqlInfo.getSingle()) {
            cgMethod.setAccess("protected");
        }

        // �s�I�u�W�F�N�g�̌^�����擾���܂��B
        final String rowObjectType = BlancoDbUtil.getBasePackage(fSqlInfo,
                fDbSetting)
                + ".row."
                + BlancoNameAdjuster.toClassName(fSqlInfo.getName())
                + "Row";

        cgMethod.setReturn(fCgFactory.createReturn(rowObjectType, "�s�I�u�W�F�N�g�B"));

        BlancoDbCgUtilJava.addExceptionToMethodSqlException(fCgFactory,
                cgMethod);

        final List<String> listDesc = cgMethod.getLangDoc()
                .getDescriptionList();

        if (fSqlInfo.getSingle()) {
            listDesc.add("�V���O���������L���Ȃ̂ŃX�R�[�v��protected�Ƃ��܂��B<br>");
            listDesc.add("���̃��\�b�h�̑���� getSingleRow���\�b�h�𗘗p���Ă��������B<br>");
        } else {
            listDesc.add("���̃��\�b�h���Ăяo���O�ɁAnext()�Ȃǂ̃J�[�\���𑀍삷�郁�\�b�h���Ăяo���K�v������܂��B");
        }

        final List<String> listLine = cgMethod.getLineList();

        if (fDbSetting.getLogging()) {
            switch (fDbSetting.getLoggingMode()) {
            case BlancoDbLoggingModeStringGroup.DEBUG:
                BlancoDbCgUtilJava.addBeginLogToMethod(cgMethod);
            }
        }

        listLine.add(BlancoNameUtil.trimJavaPackage(rowObjectType)
                + " result = new "
                + BlancoNameUtil.trimJavaPackage(rowObjectType) + "();");

        int indexCol = 1;
        for (int index = 0; index < fSqlInfo.getResultSetColumnList().size(); index++) {
            final BlancoDbMetaDataColumnStructure columnStructure = (BlancoDbMetaDataColumnStructure) fSqlInfo
                    .getResultSetColumnList().get(index);

            listLine
                    .add("result.set"
                            + BlancoNameAdjuster.toClassName(columnStructure
                                    .getName())
                            + "("
                    + (fDbSetting.getConvertStringToMsWindows31jUnicode()
                            && (columnStructure.getDataType() == Types.CHAR || columnStructure.getDataType() == Types.VARCHAR) ? "blanco.db.runtime.util.BlancoDbRuntimeStringUtil.convertToMsWindows31jUnicode("
                            : "")
                    + BlancoDbMappingUtilJava.mapPrimitiveIntoWrapperClass(columnStructure, "fResultSet."
                            + BlancoDbMappingUtilJava.getGetterMethodNameForResultSet(columnStructure) + "(" + indexCol
                            + ")")
                    + (fDbSetting.getConvertStringToMsWindows31jUnicode()
                            && (columnStructure.getDataType() == Types.CHAR || columnStructure.getDataType() == Types.VARCHAR) ? ")"
                            : "")                            + ");");

            if (BlancoDbMappingUtilJava
                    .getPrimitiveAndNullable(columnStructure)) {
                listLine.add("if (fResultSet.wasNull()) {");
                listLine.add("result.set"
                        + BlancoNameAdjuster.toClassName(columnStructure
                                .getName()) + "(null);");
                listLine.add("}");
            }
            indexCol++;
        }

        listLine.add("");

        listLine.add("return result;");
    }
}