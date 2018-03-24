/*
 * blancoDb Enterprise Edition
 * Copyright (C) 2004-2012 Toshiki Iga
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
package blanco.db;

import blanco.constants.BlancoConstantsVersion;
import blanco.gettersetter.BlancoGetterSetter;

/**
 * blancoDb �̂��߂̒萔�N���X�B
 * 
 * @author Toshiki IGA
 */
@BlancoConstantsVersion(prefix = "2.2.4-I")
public abstract class AbstractBlancoDbConstants {
    /**
     * �v���_�N�g���B�p���\���Ƃ��܂��B
     */
    @BlancoGetterSetter(setter = false)
    public static final String PRODUCT_NAME = "blancoDb Enterprise Edition";

    /**
     * �v���_�N�g���̏������ŁB�p���\���Ƃ��܂��B
     */
    public static final String PRODUCT_NAME_LOWER = "blancodbee";

    /**
     * �����̉ߒ��ŗ��p�����T�u�f�B���N�g���B
     */
    public static final String TARGET_SUBDIRECTORY = "/db";
}
