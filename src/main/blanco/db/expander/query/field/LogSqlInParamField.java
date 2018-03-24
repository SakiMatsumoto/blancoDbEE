/*
 * blancoDb
 * Copyright (C) 2004-2006 Yasuo Nakanishi
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
package blanco.db.expander.query.field;

import blanco.cg.BlancoCgObjectFactory;
import blanco.cg.valueobject.BlancoCgClass;
import blanco.cg.valueobject.BlancoCgField;
import blanco.cg.valueobject.BlancoCgSourceFile;
import blanco.db.common.expander.BlancoDbAbstractField;
import blanco.db.common.valueobject.BlancoDbSetting;
import blanco.db.common.valueobject.BlancoDbSqlInfoStructure;

/**
 * Query�N���X��fLog�t�B�[���h�ł��B
 * 
 * @author IGA Tosiki
 */
public class LogSqlInParamField extends BlancoDbAbstractField {
    /**
     * Query�N���X��fLog�t�B�[���h�̃R���X�g���N�^�ł��B
     * 
     * @param bindClassName
     *            ���O�I�u�W�F�N�g�Ƃ��Č��т����̃N���X���B
     * @author IGA Tosiki
     */
    public LogSqlInParamField(final BlancoDbSetting argDbSetting,
            final BlancoDbSqlInfoStructure argSqlInfo,
            final BlancoCgObjectFactory argCgFactory,
            final BlancoCgSourceFile argCgSourceFile,
            final BlancoCgClass argCgClass) {
        super(argDbSetting, argSqlInfo, argCgFactory, argCgSourceFile,
                argCgClass);
    }

    public void expand() {
        final BlancoCgField cgField = fCgFactory.createField("fLogSqlInParam",
                "java.lang.String",
                "���̃N���X�������I�ɗ��p���郍�M���O�̂��߂�SQL���͕�����B");
        fCgClass.getFieldList().add(cgField);

        cgField.getLangDoc().getDescriptionList().add(
                "SQL���̓p�����[�^��~���܂��B");

        /*
         * �W�F�l���[�V�����M���b�v�f�U�C���p�^�[�������p�\�ɂȂ�ړI�ŁA�X�R�[�v��protected�Ƃ��܂��B
         */
        cgField.setAccess("protected");
        cgField.setDefault("\"\"");
    }
}
