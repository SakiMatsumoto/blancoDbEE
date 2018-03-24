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
import blanco.db.common.expander.BlancoDbAbstractMethod;
import blanco.db.common.stringgroup.BlancoDbSqlInfoScrollStringGroup;
import blanco.db.common.valueobject.BlancoDbSetting;
import blanco.db.common.valueobject.BlancoDbSqlInfoStructure;
import blanco.db.util.BlancoDbCgUtilJava;

/**
 * �ʂ̃��\�b�h��W�J���邽�߂̃N���X�B
 * 
 * @author Tosiki Iga
 */
public class GetListMethod extends BlancoDbAbstractMethod {
    public GetListMethod(final BlancoDbSetting argDbSetting,
            final BlancoDbSqlInfoStructure argSqlInfo,
            final BlancoCgObjectFactory argCgFactory,
            final BlancoCgSourceFile argCgSourceFile,
            final BlancoCgClass argCgClass) {
        super(argDbSetting, argSqlInfo, argCgFactory, argCgSourceFile,
                argCgClass);
    }

    public void expand() {
        final BlancoCgMethod cgMethod = fCgFactory.createMethod("getList",
                "�������ʂ����X�g�̌`���Ŏ擾���܂��B");
        fCgClass.getMethodList().add(cgMethod);

        // �s�I�u�W�F�N�g�̌^�����擾���܂��B
        final String rowObjectType = BlancoNameAdjuster.toClassName(fSqlInfo
                .getName()) + "Row";

        cgMethod.setReturn(fCgFactory.createReturn("java.util.List<"
                + rowObjectType + ">", fSqlInfo.getName()
                + "�N���X��List�B�������ʂ�0���̏ꍇ�ɂ͋�̃��X�g���߂�܂��B"));

        BlancoDbCgUtilJava.addExceptionToMethodSqlException(fCgFactory,
                cgMethod);

        fCgSourceFile.getImportList().add("java.util.ArrayList");

        final List<String> listDesc = cgMethod.getLangDoc()
                .getDescriptionList();
        listDesc.add("���X�g�ɂ� " + fSqlInfo.getName() + "�N���X���i�[����܂��B<br>");
        listDesc.add("�������ʂ̌��������炩���߂킩���Ă��āA�����������Ȃ��ꍇ�ɗ��p���邱�Ƃ��ł��܂��B<br>");
        listDesc.add("�������ʂ̌����������ꍇ�ɂ́A���̃��\�b�h�͗��p�����A����� next()���\�b�h�𗘗p���邱�Ƃ������߂��܂��B<br>");
        if (fSqlInfo.getScroll() == BlancoDbSqlInfoScrollStringGroup.TYPE_FORWARD_ONLY) {
            listDesc.add("����QueryIterator�� FORWARD_ONLY(�������J�[�\��)�ł��B��ʂ̃f�[�^���������Ƃ��킩���Ă���ꍇ�ɂ́A����getList���\�b�h�̗��p�͋ɗ͔����邩�A���邢�� �X�N���[���J�[�\���Ƃ��ă\�[�X�R�[�h���Đ������Ă��������B");
        } else {
            cgMethod.getParameterList().add(
                    fCgFactory.createParameter("absoluteStartPoint", "int",
                            "�ǂݏo�����J�n����s�B�ŏ��̍s����ǂݏo�������ꍇ�ɂ� 1 ���w�肵�܂��B"));
        }

        cgMethod.getParameterList().add(
                fCgFactory.createParameter("size", "int", "�ǂݏo�����s���s���B"));

        final List<String> listLine = cgMethod.getLineList();

        listLine.add("List<" + rowObjectType + "> result = new ArrayList<"
                + rowObjectType + ">(8192);");
        if (fSqlInfo.getScroll() != BlancoDbSqlInfoScrollStringGroup.TYPE_FORWARD_ONLY) {
            listLine.add("if (absolute(absoluteStartPoint) == false) {");
            listLine.add("return result;");
            listLine.add("}");
        }
        listLine.add("for (int count = 1; count <= size; count++) {");
        if (fSqlInfo.getScroll() == BlancoDbSqlInfoScrollStringGroup.TYPE_FORWARD_ONLY) {
            listLine.add("if (next() == false) {");
            listLine.add("break;");
            listLine.add("}");
        } else {
            listLine.add("if (count != 1) {");
            listLine.add("if (next() == false) {");
            listLine.add("break;");
            listLine.add("}");
            listLine.add("}");
        }
        listLine.add("result.add(getRow());");
        listLine.add("}");
        listLine.add("return result;");
    }
}