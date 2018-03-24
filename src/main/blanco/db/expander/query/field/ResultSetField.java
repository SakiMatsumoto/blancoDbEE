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
 * Query�N���X��fResultSet�t�B�[���h�ł��B
 * 
 * @author IGA Tosiki
 */
public class ResultSetField extends BlancoDbAbstractField {
    /**
     * Query�N���X��fResultSet�t�B�[���h�̃R���X�g���N�^�ł��B
     * 
     * @author IGA Tosiki
     */
    public ResultSetField(final BlancoDbSetting argDbSetting,
            final BlancoDbSqlInfoStructure argSqlInfo,
            final BlancoCgObjectFactory argCgFactory,
            final BlancoCgSourceFile argCgSourceFile,
            final BlancoCgClass argCgClass) {
        super(argDbSetting, argSqlInfo, argCgFactory, argCgSourceFile,
                argCgClass);
    }

    public void expand() {
        final BlancoCgField cgField = fCgFactory.createField("fResultSet",
                "java.sql.ResultSet", "���̃N���X�������I�ɗ��p���錋�ʃZ�b�g�I�u�W�F�N�g�B");
        fCgClass.getFieldList().add(cgField);

        cgField.getLangDoc().getDescriptionList().add(
                "���̃I�u�W�F�N�g�̓f�[�^�x�[�X�X�e�[�g�����g�I�u�W�F�N�g���琶������ē����I�ɗ��p����܂��B<br>");
        cgField.getLangDoc().getDescriptionList().add(
                "close���\�b�h�̌Ăяo�����ɁA���̃I�u�W�F�N�g��close�����s���܂��B");

        /*
         * �W�F�l���[�V�����M���b�v�f�U�C���p�^�[�������p�\�ɂȂ�ړI�ŁA�X�R�[�v��protected�Ƃ��܂��B
         */
        cgField.setAccess("protected");
    }
}
