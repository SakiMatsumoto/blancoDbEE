/*
 * ���̃N���X�� 'AbstractBlancoDbConstants' �̋�ۃN���X�Ƃ��� blanco Framework �ɂ���Ď�����������܂����B
 */
package blanco.db;

import blanco.fw.BlancoGeneratedBy;

/**
 * blancoDb ????????N???X?B
 */
@BlancoGeneratedBy(name = "Blanco2g")
public class BlancoDbConstants extends AbstractBlancoDbConstants {
    /**
     * Version Number.
     *
     * [@BlancoConstantsVersion]
     */
    public static final String VERSION = "2.2.4-I201403121738";

    /**
     * Getter for version constants.
     *
     * [@BlancoConstantsVersion]
     *
     * @return Version string.
     */
    public static String getVersion() {
        return VERSION;
    }

    /**
     * ?v???_?N?g???B?p???\?????????B
     * [@BlancoGetterSetter]
     *
     * @return �擾�������l�B
     */
    public static String getProductName() {
        return PRODUCT_NAME;
    }
}
