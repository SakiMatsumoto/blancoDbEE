/*
 * blancoDb
 * Copyright (C) 2004-2006 Yasuo Nakanishi
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
package blanco.db.util;

import java.util.List;

import blanco.cg.BlancoCgObjectFactory;
import blanco.cg.valueobject.BlancoCgMethod;
import blanco.db.common.util.BlancoDbUtil;
import blanco.db.common.valueobject.BlancoDbSetting;

/**
 * blancoDb�ɂ����� blancoCg�Ɋւ��郆�[�e�B���e�B���W�߂��N���X�B
 * 
 * ���ɗǂ�����g�ݍ��킹�ɂ��āA���̏ꏊ�ňꊇ���ď������܂��B
 * 
 * @author ToshikiIga
 */
public class BlancoDbCgUtilJava {
    /**
     * ���\�b�h�� SQL��O�̃X���[��ǉ����܂��B
     * 
     * @param cgFactory
     * @param cgMethod
     */
    public static void addExceptionToMethodSqlException(
            final BlancoCgObjectFactory cgFactory, final BlancoCgMethod cgMethod) {
        cgMethod.getThrowList().add(
                cgFactory.createException("java.sql.SQLException",
                        "SQL��O�����������ꍇ�B"));
    }

    /**
     * ���\�b�h�� �f�b�h���b�N�ƃ^�C���A�E�g�̃X���[��ǉ����܂��B
     * 
     * @param cgFactory
     * @param cgMethod
     * @param storage
     */
    public static void addExceptionToMethodDeadlockTimeoutException(
            final BlancoCgObjectFactory cgFactory,
            final BlancoCgMethod cgMethod, final BlancoDbSetting storage) {
        cgMethod.getThrowList().add(
                cgFactory.createException(BlancoDbUtil
                        .getRuntimePackage(storage)
                        + ".exception.DeadlockException",
                        "�f�[�^�x�[�X�f�b�h���b�N�����������ꍇ�B"));
        cgMethod.getThrowList().add(
                cgFactory
                        .createException(BlancoDbUtil
                                .getRuntimePackage(storage)
                                + ".exception.TimeoutException",
                                "�f�[�^�x�[�X�^�C���A�E�g�����������ꍇ�B"));
    }

    /**
     * ���\�b�h�� IntegrityConstraintException�̃X���[��ǉ����܂��B
     * 
     * @param cgFactory
     * @param cgMethod
     * @param storage
     */
    public static void addExceptionToMethodIntegrityConstraintException(
            final BlancoCgObjectFactory cgFactory,
            final BlancoCgMethod cgMethod, final BlancoDbSetting storage) {
        cgMethod.getThrowList().add(
                cgFactory.createException(BlancoDbUtil
                        .getRuntimePackage(storage)
                        + ".exception.IntegrityConstraintException",
                        "�f�[�^�x�[�X����ᔽ�����������ꍇ�B"));
    }

    /**
     * ���\�b�h�Ƀ��\�b�h�J�n�̓T�^�I�ȃ��M���O��ǉ����܂��B
     * 
     * �T�^�I�ł͂Ȃ����O�ɂ��ẮA���̃��\�b�h�͗��p�����ɌʂɎ������Ă��������B
     * 
     * @param cgMethod
     */
    public static void addBeginLogToMethod(final BlancoCgMethod cgMethod) {
        final List<String> listLine = cgMethod.getLineList();

        listLine.add("if (fLog.isDebugEnabled()) {");
        listLine.add("fLog.debug(\"" + cgMethod.getName() + "\");");
        listLine.add("}");
        listLine.add("");
    }
}
