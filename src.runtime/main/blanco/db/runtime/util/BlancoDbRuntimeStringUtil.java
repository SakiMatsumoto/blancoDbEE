/*******************************************************************************
 * blancoDb Enterprise Edition
 * Copyright (C) 2004-2012 Toshiki Iga
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *******************************************************************************/
/*******************************************************************************
 * Copyright (c) 2004-2012 Toshiki IGA and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *      Toshiki IGA - initial implementation
 *******************************************************************************/
/*******************************************************************************
 * Copyright 2004-2012 Toshiki IGA and others.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package blanco.db.runtime.util;

public class BlancoDbRuntimeStringUtil {
    /**
     * ��������ꂽ������ɂ��āAMicrosoft Windows ���{��Ō`���� String �ɕϊ����܂��B
     * 
     * @param original
     *            ���͕�����B
     * @return �ϊ���̕�����B
     */
    public static String convertToMsWindows31jUnicode(final String original) {
        if (original == null) {
            // null ����������ꂽ�ꍇ�ɂ� null ��߂��܂��B
            return null;
        }

        // �����񂪕ύX���ꂽ���ǂ����B
        boolean isModified = false;

        final char[] charArray = original.toCharArray();
        for (int index = 0; index < charArray.length; index++) {
            switch (charArray[index]) {
            case '\u2016':
                // From: DOUBLE VERTICAL LINE
                // To: PARALLEL TO
                // half width to full width.
                charArray[index] = '\u2225';
                isModified = true;
                break;
            case '\u2212':
                // From: MINUS SIGN
                // To: FULLWIDTH HYPHEN-MINUS
                // half width to full width.
                charArray[index] = '\uFF0D';
                isModified = true;
                break;
            }
        }

        if (isModified == false) {
            // �ύX�͂���܂���ł����B���̂܂ܕԋp���܂��B
            return original;
        }

        // �ύX���������ꍇ�̂ݕ�����𐶐����܂��B
        return new String(charArray);
    }
}
