/*
 * ���̃\�[�X�R�[�h�� blanco Framework�ɂ�莩����������܂����B
 */
package blanco.db.resourcebundle;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * blancoDb�����p���郊�\�[�X�o���h���ł��B
 *
 * ���\�[�X�o���h����`: [BlancoDb]�B<BR>
 * ���̃N���X�̓��\�[�X�o���h����`�����玩���������ꂽ���\�[�X�o���h���N���X�ł��B<BR>
 * ���m�̃��P�[��<BR>
 * <UL>
 * <LI>ja
 * </UL>
 */
public class BlancoDbResourceBundle {
    /**
     * ���\�[�X�o���h���I�u�W�F�N�g�B
     *
     * �����I�Ɏ��ۂɓ��͂��s�����\�[�X�o���h�����L�����܂��B
     */
    private ResourceBundle fResourceBundle;

    /**
     * BlancoDbResourceBundle�N���X�̃R���X�g���N�^�B
     *
     * ��ꖼ[BlancoDb]�A�f�t�H���g�̃��P�[���A�Ăяo�����̃N���X���[�_���g�p���āA���\�[�X�o���h�����擾���܂��B
     */
    public BlancoDbResourceBundle() {
        try {
            fResourceBundle = ResourceBundle.getBundle("blanco/db/resourcebundle/BlancoDb");
        } catch (MissingResourceException ex) {
        }
    }

    /**
     * BlancoDbResourceBundle�N���X�̃R���X�g���N�^�B
     *
     * ��ꖼ[BlancoDb]�A�w�肳�ꂽ���P�[���A�Ăяo�����̃N���X���[�_���g�p���āA���\�[�X�o���h�����擾���܂��B
     *
     * @param locale ���P�[���̎w��
     */
    public BlancoDbResourceBundle(final Locale locale) {
        try {
            fResourceBundle = ResourceBundle.getBundle("blanco/db/resourcebundle/BlancoDb", locale);
        } catch (MissingResourceException ex) {
        }
    }

    /**
     * BlancoDbResourceBundle�N���X�̃R���X�g���N�^�B
     *
     * ��ꖼ[BlancoDb]�A�w�肳�ꂽ���P�[���A�w�肳�ꂽ�N���X���[�_���g�p���āA���\�[�X�o���h�����擾���܂��B
     *
     * @param locale ���P�[���̎w��
     * @param loader �N���X���[�_�̎w��
     */
    public BlancoDbResourceBundle(final Locale locale, final ClassLoader loader) {
        try {
            fResourceBundle = ResourceBundle.getBundle("blanco/db/resourcebundle/BlancoDb", locale, loader);
        } catch (MissingResourceException ex) {
        }
    }

    /**
     * �����I�ɕێ����Ă��郊�\�[�X�o���h���I�u�W�F�N�g���擾���܂��B
     *
     * @return �����I�ɕێ����Ă��郊�\�[�X�o���h���I�u�W�F�N�g�B
     */
    public ResourceBundle getResourceBundle() {
        return fResourceBundle;
    }

    /**
     * bundle[BlancoDb], key[METAFILE_DISPLAYNAME]
     *
     * [SQL��`��] (ja)<br>
     *
     * @return key[METAFILE_DISPLAYNAME]�ɑΉ�����l�B�O������ǂݍ��݂��ł��Ȃ��ꍇ�ɂ́A��`���̒l��߂��܂��B�K��null�ȊO�̒l���߂�܂��B
     */
    public String getMetafileDisplayname() {
        // �����l�Ƃ��Ē�`���̒l�𗘗p���܂��B
        String strFormat = "SQL��`��";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("METAFILE_DISPLAYNAME");
            }
        } catch (MissingResourceException ex) {
        }
        // �u��������͂ЂƂ�����܂���B
        return strFormat;
    }

    /**
     * bundle[BlancoDb], key[EXPANDER.DISABLE_GET_STATEMENT]
     *
     * [false] (ja)<br>
     *
     * @return key[EXPANDER.DISABLE_GET_STATEMENT]�ɑΉ�����l�B�O������ǂݍ��݂��ł��Ȃ��ꍇ�ɂ́A��`���̒l��߂��܂��B�K��null�ȊO�̒l���߂�܂��B
     */
    public String getExpanderDisableGetStatement() {
        // �����l�Ƃ��Ē�`���̒l�𗘗p���܂��B
        String strFormat = "false";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("EXPANDER.DISABLE_GET_STATEMENT");
            }
        } catch (MissingResourceException ex) {
        }
        // �u��������͂ЂƂ�����܂���B
        return strFormat;
    }

    /**
     * bundle[BlancoDb], key[TASK.ERR001]
     *
     * [SQL��O���������܂����B�������f���܂��B] (ja)<br>
     *
     * @return key[TASK.ERR001]�ɑΉ�����l�B�O������ǂݍ��݂��ł��Ȃ��ꍇ�ɂ́A��`���̒l��߂��܂��B�K��null�ȊO�̒l���߂�܂��B
     */
    public String getTaskErr001() {
        // �����l�Ƃ��Ē�`���̒l�𗘗p���܂��B
        String strFormat = "SQL��O���������܂����B�������f���܂��B";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("TASK.ERR001");
            }
        } catch (MissingResourceException ex) {
        }
        // �u��������͂ЂƂ�����܂���B
        return strFormat;
    }

    /**
     * bundle[BlancoDb], key[TASK.ERR002]
     *
     * [�N���X��������܂���B�N���X�p�X�̐ݒ�Ȃǂ��m�F���Ă��������B] (ja)<br>
     *
     * @return key[TASK.ERR002]�ɑΉ�����l�B�O������ǂݍ��݂��ł��Ȃ��ꍇ�ɂ́A��`���̒l��߂��܂��B�K��null�ȊO�̒l���߂�܂��B
     */
    public String getTaskErr002() {
        // �����l�Ƃ��Ē�`���̒l�𗘗p���܂��B
        String strFormat = "�N���X��������܂���B�N���X�p�X�̐ݒ�Ȃǂ��m�F���Ă��������B";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("TASK.ERR002");
            }
        } catch (MissingResourceException ex) {
        }
        // �u��������͂ЂƂ�����܂���B
        return strFormat;
    }

    /**
     * bundle[BlancoDb], key[TASK.ERR003]
     *
     * [�z�肳��Ȃ�SAX��O���������܂����B�������f���܂��B] (ja)<br>
     *
     * @return key[TASK.ERR003]�ɑΉ�����l�B�O������ǂݍ��݂��ł��Ȃ��ꍇ�ɂ́A��`���̒l��߂��܂��B�K��null�ȊO�̒l���߂�܂��B
     */
    public String getTaskErr003() {
        // �����l�Ƃ��Ē�`���̒l�𗘗p���܂��B
        String strFormat = "�z�肳��Ȃ�SAX��O���������܂����B�������f���܂��B";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("TASK.ERR003");
            }
        } catch (MissingResourceException ex) {
        }
        // �u��������͂ЂƂ�����܂���B
        return strFormat;
    }

    /**
     * bundle[BlancoDb], key[TASK.ERR004]
     *
     * [�z�肳��Ȃ����o�͗�O���������܂����B�������f���܂��B] (ja)<br>
     *
     * @return key[TASK.ERR004]�ɑΉ�����l�B�O������ǂݍ��݂��ł��Ȃ��ꍇ�ɂ́A��`���̒l��߂��܂��B�K��null�ȊO�̒l���߂�܂��B
     */
    public String getTaskErr004() {
        // �����l�Ƃ��Ē�`���̒l�𗘗p���܂��B
        String strFormat = "�z�肳��Ȃ����o�͗�O���������܂����B�������f���܂��B";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("TASK.ERR004");
            }
        } catch (MissingResourceException ex) {
        }
        // �u��������͂ЂƂ�����܂���B
        return strFormat;
    }

    /**
     * bundle[BlancoDb], key[TASK.ERR005]
     *
     * [�z�肳��Ȃ�XML�p�[�T��O���������܂����B�������f���܂��B] (ja)<br>
     *
     * @return key[TASK.ERR005]�ɑΉ�����l�B�O������ǂݍ��݂��ł��Ȃ��ꍇ�ɂ́A��`���̒l��߂��܂��B�K��null�ȊO�̒l���߂�܂��B
     */
    public String getTaskErr005() {
        // �����l�Ƃ��Ē�`���̒l�𗘗p���܂��B
        String strFormat = "�z�肳��Ȃ�XML�p�[�T��O���������܂����B�������f���܂��B";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("TASK.ERR005");
            }
        } catch (MissingResourceException ex) {
        }
        // �u��������͂ЂƂ�����܂���B
        return strFormat;
    }

    /**
     * bundle[BlancoDb], key[TASK.ERR006]
     *
     * [�z�肳��Ȃ�XML�g�����X�t�H�[�}�[��O���������܂����B�������f���܂��B] (ja)<br>
     *
     * @return key[TASK.ERR006]�ɑΉ�����l�B�O������ǂݍ��݂��ł��Ȃ��ꍇ�ɂ́A��`���̒l��߂��܂��B�K��null�ȊO�̒l���߂�܂��B
     */
    public String getTaskErr006() {
        // �����l�Ƃ��Ē�`���̒l�𗘗p���܂��B
        String strFormat = "�z�肳��Ȃ�XML�g�����X�t�H�[�}�[��O���������܂����B�������f���܂��B";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("TASK.ERR006");
            }
        } catch (MissingResourceException ex) {
        }
        // �u��������͂ЂƂ�����܂���B
        return strFormat;
    }
}
