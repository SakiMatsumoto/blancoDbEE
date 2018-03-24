/*
 * blanco Framework
 * Copyright (C) 2004-2006 IGA Tosiki
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
package blanco.db.expander.query;

import blanco.cg.BlancoCgObjectFactory;
import blanco.cg.valueobject.BlancoCgClass;
import blanco.cg.valueobject.BlancoCgField;
import blanco.cg.valueobject.BlancoCgMethod;
import blanco.cg.valueobject.BlancoCgSourceFile;

public class BlancoPerfomanceCommonUtil {
    public static void addPerfomanceFieldMethod(
            final BlancoCgObjectFactory cgFactory,
            final BlancoCgSourceFile cgSourceFile, final BlancoCgClass cgClass) {
        {
            final BlancoCgField cgField = cgFactory.createField(
                    "fPerfomanceNumberFormat", "java.text.NumberFormat",
                    "�p�t�H�[�}���X�v�����ɂ̂ݗ��p���鐔�l�t�H�[�}�b�^�B");
            cgClass.getFieldList().add(cgField);
            cgField.setFinal(true);
            cgField.setStatic(true);
            cgField.setDefault("NumberFormat.getInstance()");
        }

        {
            final BlancoCgMethod cgMethod = cgFactory.createMethod(
                    "getUsedMemory", "�p�t�H�[�}���X�v�����ɂ̂ݗ��p�����������擾���\�b�h�B");
            cgClass.getMethodList().add(cgMethod);
            cgMethod.setStatic(true);
            cgMethod.getParameterList().add(
                    cgFactory.createParameter("runtime", "java.lang.Runtime",
                            "�����^�C���̃C���X�^���X�B"));
            cgMethod.setReturn(cgFactory.createReturn("long", "����������ʁB"));
            cgMethod.getLineList().add(
                    "return runtime.totalMemory() - runtime.freeMemory();");
        }

        {
            final BlancoCgMethod cgMethod = cgFactory.createMethod(
                    "getMemorySizeString", "�p�t�H�[�}���X�v�����ɂ̂ݗ��p���郁�����T�C�Y������擾���\�b�h�B");
            cgClass.getMethodList().add(cgMethod);
            cgMethod.setStatic(true);
            cgMethod.getParameterList().add(
                    cgFactory.createParameter("memorySize", "long", "�������T�C�Y�B"));
            cgMethod.setReturn(cgFactory.createReturn("java.lang.String",
                    "�������T�C�Y�̕�����\���B"));
            cgMethod.getLineList().add(
                    "final StringBuffer result = new StringBuffer();");
            cgMethod.getLineList().add(
                    "synchronized (fPerfomanceNumberFormat) {");
            cgMethod
                    .getLineList()
                    .add(
                            "result.append(fPerfomanceNumberFormat.format(memorySize / 1024));");
            cgMethod.getLineList().add("}");
            cgMethod.getLineList().add("result.append(\"(KB)\");");
            cgMethod.getLineList().add("return result.toString();");
        }

        {
            final BlancoCgMethod cgMethod = cgFactory.createMethod(
                    "getTimeString", "�p�t�H�[�}���X�v�����ɂ̂ݗ��p�������~���b������擾���\�b�h�B");
            cgClass.getMethodList().add(cgMethod);
            cgMethod.setStatic(true);
            cgMethod.getParameterList().add(
                    cgFactory.createParameter("time", "long", "����~���b�B"));
            cgMethod.setReturn(cgFactory.createReturn("java.lang.String",
                    "����~���b�̕�����\���B"));
            cgMethod.getLineList().add(
                    "final StringBuffer result = new StringBuffer();");
            cgMethod.getLineList().add("result.append(time);");
            cgMethod.getLineList().add("result.append(\"(ms)\");");
            cgMethod.getLineList().add("return result.toString();");
        }
    }
}
