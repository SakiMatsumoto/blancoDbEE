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
import blanco.commons.util.BlancoJavaSourceUtil;
import blanco.db.common.expander.BlancoDbAbstractMethod;
import blanco.db.common.util.BlancoDbQueryParserUtil;
import blanco.db.common.valueobject.BlancoDbSetting;
import blanco.db.common.valueobject.BlancoDbSqlInfoStructure;

/**
 * �ʂ̃��\�b�h��W�J���邽�߂̃N���X�B
 * 
 * @author Yasuo Nakanishi
 */
public class GetQueryMethod extends BlancoDbAbstractMethod {
    public GetQueryMethod(final BlancoDbSetting argDbSetting,
            final BlancoDbSqlInfoStructure argSqlInfo,
            final BlancoCgObjectFactory argCgFactory,
            final BlancoCgSourceFile argCgSourceFile,
            final BlancoCgClass argCgClass) {
        super(argDbSetting, argSqlInfo, argCgFactory, argCgSourceFile,
                argCgClass);
    }

    public void expand() {
        final BlancoCgMethod cgMethod = fCgFactory.createMethod("getQuery",
                "SQL��`���ŗ^����ꂽSQL�����擾���܂��B");
        fCgClass.getMethodList().add(cgMethod);

        cgMethod.setReturn(fCgFactory.createReturn("java.lang.String",
                "JDBC�h���C�o�ɗ^���Ď��s�\�ȏ�Ԃ�SQL���B"));

        cgMethod
                .getLangDoc()
                .getDescriptionList()
                .add(
                        "SQL���̓p�����[�^�Ƃ��� #�L�[���[�h�ɂ��w�肪����ꍇ�ɂ́A�Y���ӏ��� ? �ɒu����������� SQL�����擾�ł��܂��B");

        final List<String> listLine = cgMethod.getLineList();

        // 2005.04.15 t.iga ���s�͉��s�Ƃ��ďo�͂���悤�ɕύX�B
        // 2005.10.12 t.iga blancoCommons�̕ϊ����[�e�B���e�B�𗘗p����悤�ɕύX�B
        final String escapedQuery = BlancoJavaSourceUtil
                .escapeStringAsJavaSource(fSqlInfo.getQuery());

        // �N�G���� #�p�����[�^��?�ւ̕ϊ�
        final String actualSql = new BlancoDbQueryParserUtil(escapedQuery)
                .getNaturalSqlStringForJava();

        listLine.add("return \"" + actualSql + "\";");
    }
}