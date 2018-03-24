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
import blanco.db.common.expander.BlancoDbAbstractMethod;
import blanco.db.common.stringgroup.BlancoDbLoggingModeStringGroup;
import blanco.db.common.util.BlancoDbQueryParserUtil;
import blanco.db.common.valueobject.BlancoDbSetting;
import blanco.db.common.valueobject.BlancoDbSqlInfoStructure;
import blanco.db.util.BlancoDbCgUtilJava;
import blanco.dbmetadata.BlancoDbMetaDataUtil;
import blanco.dbmetadata.valueobject.BlancoDbMetaDataColumnStructure;

/**
 * �ʂ̃��\�b�h��W�J���邽�߂̃N���X�B
 * 
 * @author tosiki iga
 */
public class PrepareCallMethod2 extends BlancoDbAbstractMethod {
    public PrepareCallMethod2(final BlancoDbSetting argDbSetting,
            final BlancoDbSqlInfoStructure argSqlInfo,
            final BlancoCgObjectFactory argCgFactory,
            final BlancoCgSourceFile argCgSourceFile,
            final BlancoCgClass argCgClass) {
        super(argDbSetting, argSqlInfo, argCgFactory, argCgSourceFile,
                argCgClass);
    }

    public void expand() {
        final BlancoCgMethod cgMethod = fCgFactory.createMethod("prepareCall",
                "�^����ꂽSQL�����������ăv���R���p�C�������{(���ISQL)���܂��B");
        fCgClass.getMethodList().add(cgMethod);

        cgMethod
                .getParameterList()
                .add(
                        fCgFactory
                                .createParameter("query", "java.lang.String",
                                        "�v���R���p�C�������{��������SQL���B���ISQL�̏ꍇ�ɂ́A���̈����ɂ͉��H���ꂽ��̎��s�\��SQL����^���܂��B"));

        BlancoDbCgUtilJava.addExceptionToMethodSqlException(fCgFactory,
                cgMethod);

        final List<String> listDesc = cgMethod.getLangDoc()
                .getDescriptionList();
        listDesc.add("���I�ɓ��e���ω�����悤��SQL�����s����K�v������ꍇ�ɂ̂݁A������̃��\�b�h�𗘗p���܂��B<br>");
        listDesc
                .add("�����ł͂Ȃ��ꍇ�ɂ́A���̃��\�b�h�̗��p�͔����� prepareCall()���\�b�h (�����Ȃ�)���Ăяo���Ă��������B<br>");
        listDesc
                .add("�Ȃ��Ȃ�A���̃��\�b�h�ł�SQL�����̂��̂��p�����[�^�Ƃ��ė^���邱�Ƃ��ł��Ď��R�x����������ASQL�C���W�F�N�V�����ƌĂ΂��Z�L�����e�B�z�[������������\���������N�����Ă��܂�����ł��B<br>");
        listDesc.add("�����I��Connection.prepareCall���Ăяo���܂��B<br>");

        final List<String> listLine = cgMethod.getLineList();

        if (fDbSetting.getLogging()) {
            switch (fDbSetting.getLoggingMode()) {
            case BlancoDbLoggingModeStringGroup.DEBUG:
                listLine.add("if (fLog.isDebugEnabled()) {");
                listLine.add("fLog.debug(\"" + cgMethod.getName()
                        + ": query = \" + query);");
                listLine.add("}");
                break;
            case BlancoDbLoggingModeStringGroup.PERFORMANCE:
                listLine.add("fLog.info(\"" + cgMethod.getName()
                        + ": query = \" + query);");
                break;
            }
            listLine.add("");
        }

        listLine.add("close();");

        listLine.add("fStatement = fConnection.prepareCall(query);");

        final BlancoDbQueryParserUtil query = new BlancoDbQueryParserUtil(
                fSqlInfo.getQuery());

        for (int indexParameter = 0; indexParameter < fSqlInfo
                .getOutParameterList().size(); indexParameter++) {
            // ����A�Ƃ肠�����^����ꂽ�����œo��Ɖ��肪������Ă��܂��B
            final BlancoDbMetaDataColumnStructure columnStructure = (BlancoDbMetaDataColumnStructure) fSqlInfo
                    .getOutParameterList().get(indexParameter);

            final int[] listCol = query.getSqlParameters(columnStructure
                    .getName());
            if (listCol == null) {
                System.out.println("[" + fSqlInfo.getName() + "]�� SQL�o�̓p�����[�^["
                        + columnStructure.getName() + "]�����т��Ă��܂���.");
                continue;
            }
            for (int iteSame = 0; iteSame < listCol.length; iteSame++) {
                final int index = listCol[iteSame];

                String stmtLine = "fStatement.registerOutParameter("
                        + index
                        + ", java.sql.Types."
                        + BlancoDbMetaDataUtil
                                .convertJdbcDataTypeToString(columnStructure
                                        .getDataType());
                stmtLine += ");";
                listLine.add(stmtLine);
            }
        }
    }
}