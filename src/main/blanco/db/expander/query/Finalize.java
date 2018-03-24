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
import blanco.db.common.valueobject.BlancoDbSetting;
import blanco.db.common.valueobject.BlancoDbSqlInfoStructure;

/**
 * �ʂ̃��\�b�h��W�J���邽�߂̃N���X�B
 * 
 * @author Yasuo Nakanishi
 */
public class Finalize extends BlancoDbAbstractMethod {
    public Finalize(final BlancoDbSetting argDbSetting,
            final BlancoDbSqlInfoStructure argSqlInfo,
            final BlancoCgObjectFactory argCgFactory,
            final BlancoCgSourceFile argCgSourceFile,
            final BlancoCgClass argCgClass) {
        super(argDbSetting, argSqlInfo, argCgFactory, argCgSourceFile,
                argCgClass);
    }

    public void expand() {
        final BlancoCgMethod cgMethod = fCgFactory.createMethod("finalize",
                "finalize���\�b�h�B");
        fCgClass.getMethodList().add(cgMethod);

        cgMethod.setAccess("protected");

        cgMethod.getThrowList().add(
                fCgFactory.createException("java.lang.Throwable",
                        "finalize�����̒��Ŕ���������O�B"));

        cgMethod
                .getLangDoc()
                .getDescriptionList()
                .add(
                        "���̃N���X�������I�ɐ��������I�u�W�F�N�g�̂Ȃ��ŁAclose()�Ăяo���Y��o�O�����݂��邩�ǂ����`�F�b�N���܂��B<br>");

        final List<String> listLine = cgMethod.getLineList();

        listLine.add("super.finalize();");
        listLine.add("if (fStatement != null) {");
        listLine.add("final String message = \"" + fCgClass.getName()
                + " : close()���\�b�h�ɂ�郊�\�[�X�̊J�����s���Ă��܂���B\";");
        listLine.add("System.out.println(message);");

        if (fDbSetting.getLogging()) {
            listLine.add("");
            listLine.add("fLog.error(\"" + cgMethod.getName()
                    + ": \" + message);");
        }

        listLine.add("}");
    }
}