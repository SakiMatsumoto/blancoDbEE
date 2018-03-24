/*
 * blancoDb Enterprise Edition Copyright (C) 2004-2005 Tosiki Iga
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 */
package blanco.db.util;

import java.sql.ResultSetMetaData;
import java.sql.Types;

import blanco.commons.util.BlancoNameUtil;
import blanco.dbmetadata.BlancoDbMetaDataUtil;
import blanco.dbmetadata.valueobject.BlancoDbMetaDataColumnStructure;

/**
 * blancoDb�̌^�}�b�s���O�Ɋւ��郁�\�b�h���W�߂��N���X�B
 * 
 * @author ToshikiIga
 */
public final class BlancoDbMappingUtilJava {
    /**
     * ��\���̂���Java����ɂ�����t���N���X�����擾���܂��B
     * 
     * ���̏����́A�v���O���~���O���ꂲ�ƂɈقȂ���̂ł��B Types�� Java����̉��̌^�Ƀ}�b�s���O����̂��A�Ƃ����d�v�ȏ����ɂ�����܂��B
     * 
     * @param columnStructure
     * @return
     */
    public static final String getFullClassName(
            final BlancoDbMetaDataColumnStructure columnStructure) {
        final boolean isNoNulls = (columnStructure.getNullable() == ResultSetMetaData.columnNoNulls);
        switch (columnStructure.getDataType()) {
        case Types.BIT:
        case Types.BOOLEAN:
            if (isNoNulls) {
                return "boolean";
            } else {
                return "java.lang.Boolean";
            }
        case Types.TINYINT:
            if (isNoNulls) {
                return "byte";
            } else {
                return "java.lang.Byte";
            }
        case Types.SMALLINT:
            if (isNoNulls) {
                return "short";
            } else {
                return "java.lang.Short";
            }
        case Types.INTEGER:
            if (isNoNulls) {
                return "int";
            } else {
                return "java.lang.Integer";
            }
        case Types.BIGINT:
            if (isNoNulls) {
                return "long";
            } else {
                return "java.lang.Long";
            }
        case Types.REAL:
            if (isNoNulls) {
                return "float";
            } else {
                return "java.lang.Float";
            }
        case Types.FLOAT:
        case Types.DOUBLE:
            if (isNoNulls) {
                return "double";
            } else {
                return "java.lang.Double";
            }
        case Types.NUMERIC:
        case Types.DECIMAL:
            return "java.math.BigDecimal";
        case Types.CHAR:
        case Types.VARCHAR:
        case Types.NCHAR:
        case Types.NVARCHAR:
            return "java.lang.String";
        case Types.DATE:
            // ���� TIMESTAMP�Ɠ��������������Ă��܂��B
        case Types.TIME:
            // ���� TIMESTAMP�Ɠ��������������Ă��܂��B
        case Types.TIMESTAMP:
            return "java.util.Date";
        case Types.BINARY:
        case Types.VARBINARY:
        case Types.LONGVARBINARY:
        case Types.BLOB:
            return "java.io.InputStream";
        case Types.LONGVARCHAR:
        case Types.LONGNVARCHAR:
        case Types.CLOB:
        case Types.NCLOB:
            return "java.io.Reader";
        case Types.JAVA_OBJECT:
        case Types.DISTINCT:
        case Types.STRUCT:
        case Types.ARRAY:
        case Types.NULL:
        case Types.OTHER:
        case Types.REF:
        case Types.DATALINK:
        case -101:
            // Oracle �� SYSTIMESTAMP �^�̂��߂́Aworkaround
            if("SYSTIMESTAMP".equals(columnStructure.getName())){
                return "java.util.Date";
            }
        default:
            throw new IllegalArgumentException("BlancoDbTableMeta2Xml: ��p�����[�^["
                    + columnStructure.getName()
                    + "]("
                    + BlancoDbMetaDataUtil
                            .convertJdbcDataTypeToString(columnStructure
                                    .getDataType())
                    + ")�̃o�C���h: �����ł��Ȃ�SQL�^("
                    + columnStructure.getDataType()
                    + "/"
                    + BlancoDbMetaDataUtil
                            .convertJdbcDataTypeToString(columnStructure
                                    .getDataType()) + ")���w�肳��܂����B");
        }
    }

    /**
     * ��\���̂���Java����ɂ�����N���X�����擾���܂��B
     * 
     * @param columnStructure
     * @return
     */
    public static final String getClassName(
            final BlancoDbMetaDataColumnStructure columnStructure) {
        return BlancoNameUtil.trimJavaPackage(BlancoDbMappingUtilJava
                .getFullClassName(columnStructure));
    }

    /**
     * �v���~�e�B�u�ł�����NULL���T�|�[�g���ׂ����̂��ǂ����𔻒f���܂��B
     * 
     * �v���~�e�B�u�^�ł�null���\���ł��Ȃ��^������܂��̂ŁA���̌^�ɊY�����邩�ǂ�������������Ȃ��܂��B
     * 
     * @param columnStructure
     * @return
     */
    public static boolean getPrimitiveAndNullable(
            final BlancoDbMetaDataColumnStructure columnStructure) {
        final boolean isNoNulls = !(columnStructure.getNullable() == ResultSetMetaData.columnNullable);

        switch (columnStructure.getDataType()) {
        case Types.BIT:
        case Types.BOOLEAN:
            if (isNoNulls) {
                return false;
            } else {
                return true;
            }
        case Types.TINYINT:
            if (isNoNulls) {
                return false;
            } else {
                return true;
            }
        case Types.SMALLINT:
            if (isNoNulls) {
                return false;
            } else {
                return true;
            }
        case Types.INTEGER:
            if (isNoNulls) {
                return false;
            } else {
                return true;
            }
        case Types.BIGINT:
            if (isNoNulls) {
                return false;
            } else {
                return true;
            }
        case Types.REAL:
            if (isNoNulls) {
                return false;
            } else {
                return true;
            }
        case Types.FLOAT:
        case Types.DOUBLE:
            if (isNoNulls) {
                return false;
            } else {
                return true;
            }
        case Types.NUMERIC:
        case Types.DECIMAL:
            return false;
        case Types.CHAR:
        case Types.VARCHAR:
            return false;
        case Types.DATE:
            // TIMESTAMP�Ɠ��������������Ă��܂��B
        case Types.TIME:
            // TIMESTAMP�Ɠ��������������Ă��܂��B
        case Types.TIMESTAMP:
            // ���ʂȓ����BDATE, TIME, TIMESTAMP �ɂ��ẮA�v���~�e�B�u�^ + NULL���e�̍ۂƓ��������������Ă��܂��B
            return true;
        case Types.BINARY:
        case Types.VARBINARY:
        case Types.LONGVARBINARY:
        case Types.BLOB:
            return false;
        case Types.LONGVARCHAR:
        case Types.CLOB:
            return false;
        case Types.JAVA_OBJECT:
        case Types.DISTINCT:
        case Types.STRUCT:
        case Types.ARRAY:
        case Types.NULL:
        case Types.OTHER:
        case Types.REF:
        case Types.DATALINK:
        default:
            return false;
        }
    }

    /**
     * ��������ƂɁAPreparedStatement�ɑ΂���Z�b�^�[���\�b�h�����擾���܂��B
     * 
     * @param columnStructure
     * @return
     */
    public static final String getSetterMethodNameForPreparedStatement(
            final BlancoDbMetaDataColumnStructure columnStructure) {
        return "set" + getGetterSetterBaseMethodName(columnStructure);
    }

    /**
     * ��������ƂɁAResultSet�ɑ΂���Q�b�^�[���\�b�h�����擾���܂��B
     * 
     * @param columnStructure
     * @return
     */
    public static final String getGetterMethodNameForResultSet(
            final BlancoDbMetaDataColumnStructure columnStructure) {
        return "get" + getGetterSetterBaseMethodName(columnStructure);
    }

    /**
     * ��������ƂɁAResultSet�ɑ΂���update���\�b�h�����擾���܂��B
     * 
     * @param columnStructure
     * @return
     */
    public static final String getUpdateMethodNameForResultSet(
            final BlancoDbMetaDataColumnStructure columnStructure) {
        return "update" + getGetterSetterBaseMethodName(columnStructure);
    }

    /**
     * �Q�b�^�[�Z�b�^�[���\�b�h���̃x�[�X���̂��擾���܂��B
     * 
     * @param columnStructure
     * @return
     */
    private static final String getGetterSetterBaseMethodName(
            final BlancoDbMetaDataColumnStructure columnStructure) {
        switch (columnStructure.getDataType()) {
        case Types.BIT:
        case Types.BOOLEAN:
            return "Boolean";
        case Types.TINYINT:
            return "Byte";
        case Types.SMALLINT:
            return "Short";
        case Types.INTEGER:
            return "Int";
        case Types.BIGINT:
            return "Long";
        case Types.REAL:
            return "Float";
        case Types.FLOAT:
        case Types.DOUBLE:
            return "Double";
        case Types.NUMERIC:
        case Types.DECIMAL:
            return "BigDecimal";
        case Types.CHAR:
        case Types.VARCHAR:
        case Types.NCHAR:
        case Types.NVARCHAR:
            return "String";
        case Types.DATE:
            // ���� TIMESTAMP�Ɠ��������������Ă��܂��B
        case Types.TIME:
            // ���� TIMESTAMP�Ɠ��������������Ă��܂��B
        case Types.TIMESTAMP:
            return "Timestamp";
        case Types.BINARY:
        case Types.VARBINARY:
        case Types.LONGVARBINARY:
        case Types.BLOB:
            return "BinaryStream";
        case Types.LONGVARCHAR:
        case Types.LONGNVARCHAR:
        case Types.CLOB:
        case Types.NCLOB:
            return "CharacterStream";
        case Types.JAVA_OBJECT:
        case Types.DISTINCT:
        case Types.STRUCT:
        case Types.ARRAY:
        case Types.NULL:
        case Types.OTHER:
        case Types.REF:
        case Types.DATALINK:
        case -101:
            // Oracle �� SYSTIMESTAMP �^�̂��߂́Aworkaround
            if("SYSTIMESTAMP".equals(columnStructure.getName())){
                return "Timestamp";
            }
        default:
            throw new IllegalArgumentException("�Q�b�^�[����уZ�b�^�[���擾���鏈���ŁA�^["
                    + columnStructure.getDataType()
                    + "/"
                    + BlancoDbMetaDataUtil
                            .convertJdbcDataTypeToString(columnStructure
                                    .getDataType()) + "]�ɑΉ����郁�\�b�h���̉�͂Ɏ��s���܂����B");
        }
    }

    /**
     * �K�v�ȏꍇ�̂݁A�v���~�e�B�u�^�Ȃǂɑ΂��ă��b�p�[�N���X�����b�s���O���܂��B
     * 
     * �v���~�e�B�u�^�����b�p�[�N���X�̃I�u�W�F�N�g�ւƒu�������鏈���������Ȃ��܂��B<br>
     * �����āAjava.sql.Date��java.sql.Timestamp�Ȃǂ���
     * java.util.Date�ւ̒u�������������ł����Ȃ��Ă��܂��B
     * 
     * @param String
     *            originalLine �I���W�i���s
     * @param String
     *            javaTypeName Java�����̌^
     * @return
     */
    public static final String mapPrimitiveIntoWrapperClass(
            final BlancoDbMetaDataColumnStructure columnStructure,
            final String originalLine) {
        String converter1 = "";
        String converter2 = "";

        switch (columnStructure.getDataType()) {
        case Types.BIT:
        case Types.BOOLEAN:
            if (columnStructure.getNullable() == ResultSetMetaData.columnNullable) {
                converter1 = "new Boolean(";
                converter2 = ")";
            }
            break;
        case Types.TINYINT:
            if (columnStructure.getNullable() == ResultSetMetaData.columnNullable) {
                converter1 = "new Byte(";
                converter2 = ")";
            }
            break;
        case Types.SMALLINT:
            if (columnStructure.getNullable() == ResultSetMetaData.columnNullable) {
                converter1 = "new Short(";
                converter2 = ")";
            }
            break;
        case Types.INTEGER:
            if (columnStructure.getNullable() == ResultSetMetaData.columnNullable) {
                converter1 = "new Integer(";
                converter2 = ")";
            }
            break;
        case Types.BIGINT:
            if (columnStructure.getNullable() == ResultSetMetaData.columnNullable) {
                converter1 = "new Long(";
                converter2 = ")";
            }
            break;
        case Types.REAL:
            if (columnStructure.getNullable() == ResultSetMetaData.columnNullable) {
                converter1 = "new Float(";
                converter2 = ")";
            }
            break;
        case Types.FLOAT:
        case Types.DOUBLE:
            if (columnStructure.getNullable() == ResultSetMetaData.columnNullable) {
                converter1 = "new Double(";
                converter2 = ")";
            }
            break;
        case Types.DATE:
        case Types.TIME:
        case Types.TIMESTAMP:
            // Date�̏ꍇ�ɂ� ResultSet�����Timestamp���n���Ă��܂��B
            converter1 = "BlancoDbUtil.convertTimestampToDate(";
            converter2 = ")";
            break;
        }

        return converter1 + originalLine + converter2;
    }

    /**
     * ���b�p�[�N���X���v���~�e�B�u�ɕϊ����܂��B<br>
     * ���b�p�[�N���X�̃I�u�W�F�N�g���v���~�e�B�u�^�ւƒu�������鏈���������Ȃ��܂��B<br>
     * �����āAjava.util.Date���� java.sql.Timestamp�ւ̒u�������������ł����Ȃ��Ă��܂��B
     * 
     * @param originalLine
     * @param javaTypeName
     * @return
     */
    public static final String mapWrapperClassIntoPrimitive(
            final BlancoDbMetaDataColumnStructure columnStructure,
            final String originalLine) {
        String converter1 = "";
        String converter2 = "";

        switch (columnStructure.getDataType()) {
        case Types.BIT:
        case Types.BOOLEAN:
            if (columnStructure.getNullable() == ResultSetMetaData.columnNullable) {
                converter1 = "";
                converter2 = ".booleanValue()";
            }
            break;
        case Types.TINYINT:
            if (columnStructure.getNullable() == ResultSetMetaData.columnNullable) {
                converter1 = "";
                converter2 = ".byteValue()";
            }
            break;
        case Types.SMALLINT:
            if (columnStructure.getNullable() == ResultSetMetaData.columnNullable) {
                converter1 = "";
                converter2 = ".shortValue()";
            }
            break;
        case Types.INTEGER:
            if (columnStructure.getNullable() == ResultSetMetaData.columnNullable) {
                converter1 = "";
                converter2 = ".intValue()";
            }
            break;
        case Types.BIGINT:
            if (columnStructure.getNullable() == ResultSetMetaData.columnNullable) {
                converter1 = "";
                converter2 = ".longValue()";
            }
            break;
        case Types.REAL:
            if (columnStructure.getNullable() == ResultSetMetaData.columnNullable) {
                converter1 = "";
                converter2 = ".floatValue()";
            }
            break;
        case Types.FLOAT:
        case Types.DOUBLE:
            if (columnStructure.getNullable() == ResultSetMetaData.columnNullable) {
                converter1 = "";
                converter2 = ".doubleValue()";
            }
            break;
        case Types.DATE:
        case Types.TIME:
        case Types.TIMESTAMP:
            converter1 = "new Timestamp(";
            converter2 = ".getTime())";
            break;
        }

        return converter1 + originalLine + converter2;
    }
}
