/*
 * blancoDb
 * Copyright (C) 2004-2006 Yasuo Nakanishi
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
package blanco.db.expander.query;

import java.util.List;

import blanco.cg.BlancoCgObjectFactory;
import blanco.cg.valueobject.BlancoCgClass;
import blanco.cg.valueobject.BlancoCgMethod;
import blanco.cg.valueobject.BlancoCgSourceFile;
import blanco.db.common.expander.BlancoDbAbstractMethod;
import blanco.db.common.stringgroup.BlancoDbLoggingModeStringGroup;
import blanco.db.common.stringgroup.BlancoDbSqlInfoScrollStringGroup;
import blanco.db.common.stringgroup.BlancoDbSqlInfoTypeStringGroup;
import blanco.db.common.valueobject.BlancoDbSetting;
import blanco.db.common.valueobject.BlancoDbSqlInfoStructure;
import blanco.db.util.BlancoDbCgUtilJava;

/**
 * �ʂ̃��\�b�h��W�J���邽�߂̃N���X�B
 * 
 * @author tosiki iga
 */
public class PrepareStatementMethod2 extends BlancoDbAbstractMethod {
    public PrepareStatementMethod2(final BlancoDbSetting argDbSetting,
            final BlancoDbSqlInfoStructure argSqlInfo,
            final BlancoCgObjectFactory argCgFactory,
            final BlancoCgSourceFile argCgSourceFile,
            final BlancoCgClass argCgClass) {
        super(argDbSetting, argSqlInfo, argCgFactory, argCgSourceFile,
                argCgClass);
    }

    public void expand() {
        final BlancoCgMethod cgMethod = fCgFactory.createMethod(
                "prepareStatement", "�^����ꂽSQL�����������ăv���R���p�C�������{(���ISQL)���܂��B");
        fCgClass.getMethodList().add(cgMethod);

        if (fSqlInfo.getDynamicSql() == false) {
            // ���I SQL ���p�t���O�� OFF �̏ꍇ�A���I SQL �̂��߂̂��̃��\�b�h�� protected �����܂��B
            cgMethod.setAccess("protected");
        }

        cgMethod.getParameterList()
                .add(fCgFactory
                        .createParameter("query", "java.lang.String",
                                "�v���R���p�C�������{��������SQL���B���ISQL�̏ꍇ�ɂ́A���̈����ɂ͉��H���ꂽ��̎��s�\��SQL����^���܂��B"));

        BlancoDbCgUtilJava.addExceptionToMethodSqlException(fCgFactory,
                cgMethod);

        final List<String> listDesc = cgMethod.getLangDoc()
                .getDescriptionList();

        listDesc.add("���̃��\�b�h�́A���I�ɓ��e���ω�����悤�� SQL �����s����K�v������ꍇ�ɂ̂ݗ��p���܂��B<br>");
        if (fSqlInfo.getDynamicSql() == false) {
            listDesc.add("���I SQL �𗘗p����K�v������ꍇ�ɂ́ASQL ��`���Łu���ISQL�v���u�g�p����v�ɕύX���Ă��������B�ύX��͊O�����痘�p�\�ɂȂ�܂��B<br>");
        } else {
            listDesc.add("SQL ��`���Łu���ISQL�v���u�g�p����v�ɐݒ肳��Ă��܂��B<br>");
        }
        listDesc.add("�����I�� JDBC �h���C�o�� Connection.prepareStatement ���Ăяo���܂��B<br>");

        if (fSqlInfo.getType() == BlancoDbSqlInfoTypeStringGroup.ITERATOR) {
            // �����^�̏ꍇ�ɂ̂ݏo�͂��܂��B

            // TODO
            // BlancoDbSqlInfoScrollStringGroup.NOT_DEFINED�̏ꍇ�ɂ͉����o�͂��ׂ��ł͂Ȃ��̂����A1.6.4�Ƃ̌݊����m�ۂ̂���
            // �X�N���[��������LangDoc�ɏo�͂��Ă��܂��B

            if (fSqlInfo.getScroll() == BlancoDbSqlInfoScrollStringGroup.TYPE_FORWARD_ONLY
                    && fSqlInfo.getUpdatable() == false) {
                // �������J�[�\���Ŋ��X�V�\������OFF�̏ꍇ�ɂ́A����LangDoc�ɏo�͂��܂���B
            } else {
                listDesc.add("�X�N���[������: "
                        + new BlancoDbSqlInfoScrollStringGroup()
                                .convertToString(fSqlInfo.getScroll()));
                if (fSqlInfo.getUpdatable()) {
                    listDesc.add("�X�V�\����: �L��");
                }
            }
        }

        final List<String> listLine = cgMethod.getLineList();

        if(fDbSetting.getLoggingsql()) {
        	// �W���o�͂ɏo�́B 
			listLine.add("fLogSqlInParam = \"\";");

			if (fSqlInfo.getDynamicSql()) {
				listLine.add("fLogSqlDynamicSql = query;");
			}
		}
        
        if (fDbSetting.getLogging()) {
            switch (fDbSetting.getLoggingMode()) {
            case BlancoDbLoggingModeStringGroup.DEBUG:
                listLine.add("if (fLog.isDebugEnabled()) {");
                listLine.add("fLog.debug(\"" + cgMethod.getName()
                        + ": query = \" + query);");
                listLine.add("}");
                break;
            case BlancoDbLoggingModeStringGroup.PERFORMANCE:
                listLine.add("fLog.info(\"" + fSqlInfo.getName()
                        + "���sSQL\\n\" + query);");
                break;
            }
            listLine.add("");
        }

        listLine.add("close();");

        // TODO �X�N���[�����������w��̏ꍇ JDBC API���X�N���[�����������Ŏw�肵�悤�Ƃ������A���̎d�l����
        // 1.6.4�Ɠ��삪�قȂ��Ă��܂��܂��B
        // TODO 1.6.4�Ƃ̌݊�����D�悵�A�X�N���[�������w�薳���̏ꍇ�̏������������܂��B

        if (fSqlInfo.getType() == BlancoDbSqlInfoTypeStringGroup.INVOKER
                || fSqlInfo.getType() == BlancoDbSqlInfoTypeStringGroup.CALLER) {
            // ���s�^�E�ďo�^�̏ꍇ�ɂ́A�P��prepareStatement���Ăяo���܂��B
            listLine.add("fStatement = fConnection.prepareStatement(query);");
        } else if (fSqlInfo.getScroll() == BlancoDbSqlInfoScrollStringGroup.TYPE_FORWARD_ONLY
                && fSqlInfo.getUpdatable() == false) {
            // �����^�̂����A�p�����[�^�̃o���G�[�V�������P���ȏꍇ�ɂ́A�P����prepareStatement���Ăяo���܂��B
            listLine.add("fStatement = fConnection.prepareStatement(query);");
        } else {
            // �o���G�[�V�����̓��e�ɍ��킹�Ĉ����𐶐����܂��B
            // �����^�� BlancoDbSqlInfoScrollStringGroup.NOT_DEFINED �ɂ��Ă�
            // ������ʉ߂���_�ɒ��ӂ��Ă��������B����� 1.6.4�Ƃ̌݊����̂��߂ɕK�v�ł��B

            String resultSetType = "ResultSet.TYPE_FORWARD_ONLY";
            String resultSetConcurrency = "ResultSet.CONCUR_READ_ONLY";
            if (fSqlInfo.getScroll() == BlancoDbSqlInfoScrollStringGroup.TYPE_SCROLL_INSENSITIVE) {
                resultSetType = "ResultSet.TYPE_SCROLL_INSENSITIVE";
            } else if (fSqlInfo.getScroll() == BlancoDbSqlInfoScrollStringGroup.TYPE_SCROLL_SENSITIVE) {
                resultSetType = "ResultSet.TYPE_SCROLL_SENSITIVE";
            }
            if (fSqlInfo.getUpdatable()) {
                resultSetConcurrency = "ResultSet.CONCUR_UPDATABLE";
            }
            listLine.add("fStatement = fConnection.prepareStatement(query, "
                    + resultSetType + ", " + resultSetConcurrency + ");");
        }

        if (fDbSetting.getStatementTimeout() >= 0) {
            listLine.add("// �X�e�[�g�����g�^�C���A�E�g�l�ɂ��ăf�t�H���g�l���Z�b�g���܂��B");
            listLine.add("fStatement.setQueryTimeout("
                    + fDbSetting.getStatementTimeout() + ");");
        }
    }
}