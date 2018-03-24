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
 * Query�N���X��fConnection�t�B�[���h�ł��B
 * 
 * @author IGA Tosiki
 */
public class ConnectionField extends BlancoDbAbstractField {
    /**
     * Query�N���X��fConnection�t�B�[���h�̃R���X�g���N�^�ł��B
     * 
     * @author IGA Tosiki
     */
    public ConnectionField(final BlancoDbSetting argDbSetting,
            final BlancoDbSqlInfoStructure argSqlInfo,
            final BlancoCgObjectFactory argCgFactory,
            final BlancoCgSourceFile argCgSourceFile,
            final BlancoCgClass argCgClass) {
        super(argDbSetting, argSqlInfo, argCgFactory, argCgSourceFile,
                argCgClass);
    }

    public void expand() {
        final BlancoCgField cgField = fCgFactory.createField("fConnection",
                "java.sql.Connection", "���̃N���X�������I�ɗ��p����f�[�^�x�[�X�ڑ��I�u�W�F�N�g�B");
        fCgClass.getFieldList().add(cgField);

        cgField.getLangDoc().getDescriptionList().add(
                "�f�[�^�x�[�X�ڑ��I�u�W�F�N�g�̓R���X�g���N�^�̈����Ƃ��ĊO������^�����܂��B<br>");
        cgField.getLangDoc().getDescriptionList().add(
                "�g�����U�N�V�����̃R�~�b�g�⃍�[���o�b�N�́A���̃N���X�̓����ł͎��s���܂���B");

        /*
         * �W�F�l���[�V�����M���b�v�f�U�C���p�^�[�������p�\�ɂȂ�ړI�ŁA�X�R�[�v��protected�Ƃ��܂��B
         */
        cgField.setAccess("protected");
    }
}
