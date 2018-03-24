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
 * @author tosiki iga
 */
public class GetStatementMethod extends BlancoDbAbstractMethod {
    protected boolean fIsCallableStatement = false;

    public GetStatementMethod(final BlancoDbSetting argDbSetting,
            final BlancoDbSqlInfoStructure argSqlInfo,
            final BlancoCgObjectFactory argCgFactory,
            final BlancoCgSourceFile argCgSourceFile,
            final BlancoCgClass argCgClass, final boolean isCallableStatement) {
        super(argDbSetting, argSqlInfo, argCgFactory, argCgSourceFile,
                argCgClass);
        fIsCallableStatement = isCallableStatement;
    }

    public void expand() {
        final BlancoCgMethod cgMethod = fCgFactory.createMethod("getStatement",
                null);
        fCgClass.getMethodList().add(cgMethod);

        String resultType = "java.sql.PreparedStatement";
        if (fIsCallableStatement == false) {
        } else {
            resultType = "java.sql.CallableStatement";
        }

        cgMethod.setReturn(fCgFactory.createReturn(resultType, "�����I�ɗ��p����Ă��� "
                + resultType + "�I�u�W�F�N�g"));

        cgMethod.getLangDoc().getDescriptionList().add(
                "�X�e�[�g�����g (" + resultType + ") ���擾���܂��B");
        cgMethod.getLangDoc().getDescriptionList().add(
                "@deprecated ��{�I��Statement�͊O�����璼�ڗ��p����K�v�͂���܂���B");

        final List<String> listLine = cgMethod.getLineList();

        listLine.add("return fStatement;");
    }
}