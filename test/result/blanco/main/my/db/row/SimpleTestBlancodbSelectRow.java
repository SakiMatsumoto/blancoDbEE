/*
 * ���̃\�[�X�R�[�h�� blanco Framework�ɂ�莩����������܂����B
 */
package my.db.row;

/**
 * SQL��`��(blancoDb)����쐬���ꂽ�s�N���X�B
 *
 * 'SimpleTestBlancodbSelectRow'�s��\�����܂��B
 * (1) 'COL_ID'�� �^:int
 * (2) 'COL_TEXT'�� �^:java.lang.String
 * (3) 'COL_NUMERIC'�� �^:java.lang.Double
 */
public class SimpleTestBlancodbSelectRow {
    /**
     * �t�B�[���h[COL_ID]�ł��B
     *
     * �t�B�[���h: [COL_ID]�B
     */
    private int fColId;

    /**
     * �t�B�[���h[COL_TEXT]�ł��B
     *
     * �t�B�[���h: [COL_TEXT]�B
     */
    private String fColText;

    /**
     * �t�B�[���h[COL_NUMERIC]�ł��B
     *
     * �t�B�[���h: [COL_NUMERIC]�B
     */
    private Double fColNumeric;

    /**
     * �t�B�[���h [COL_ID] �̒l��ݒ肵�܂��B
     *
     * �t�B�[���h�̐���: [�t�B�[���h[COL_ID]�ł��B]�B
     *
     * @param argColId �t�B�[���h[COL_ID]�ɐݒ肷��l�B
     */
    public void setColId(final int argColId) {
        fColId = argColId;
    }

    /**
     * �t�B�[���h [COL_ID] �̒l���擾���܂��B
     *
     * �t�B�[���h�̐���: [�t�B�[���h[COL_ID]�ł��B]�B
     *
     * @return �t�B�[���h[COL_ID]����擾�����l�B
     */
    public int getColId() {
        return fColId;
    }

    /**
     * �t�B�[���h [COL_TEXT] �̒l��ݒ肵�܂��B
     *
     * �t�B�[���h�̐���: [�t�B�[���h[COL_TEXT]�ł��B]�B
     *
     * @param argColText �t�B�[���h[COL_TEXT]�ɐݒ肷��l�B
     */
    public void setColText(final String argColText) {
        fColText = argColText;
    }

    /**
     * �t�B�[���h [COL_TEXT] �̒l���擾���܂��B
     *
     * �t�B�[���h�̐���: [�t�B�[���h[COL_TEXT]�ł��B]�B
     *
     * @return �t�B�[���h[COL_TEXT]����擾�����l�B
     */
    public String getColText() {
        return fColText;
    }

    /**
     * �t�B�[���h [COL_NUMERIC] �̒l��ݒ肵�܂��B
     *
     * �t�B�[���h�̐���: [�t�B�[���h[COL_NUMERIC]�ł��B]�B
     *
     * @param argColNumeric �t�B�[���h[COL_NUMERIC]�ɐݒ肷��l�B
     */
    public void setColNumeric(final Double argColNumeric) {
        fColNumeric = argColNumeric;
    }

    /**
     * �t�B�[���h [COL_NUMERIC] �̒l���擾���܂��B
     *
     * �t�B�[���h�̐���: [�t�B�[���h[COL_NUMERIC]�ł��B]�B
     *
     * @return �t�B�[���h[COL_NUMERIC]����擾�����l�B
     */
    public Double getColNumeric() {
        return fColNumeric;
    }

    /**
     * ���̃o�����[�I�u�W�F�N�g�̕�����\�����擾���܂��B
     *
     * <P>�g�p��̒���</P>
     * <UL>
     * <LI>�I�u�W�F�N�g�̃V�����[�͈͂̂ݕ����񉻂̏����ΏۂƂȂ�܂��B
     * <LI>�I�u�W�F�N�g���z�Q�Ƃ��Ă���ꍇ�ɂ́A���̃��\�b�h�͎g��Ȃ��ł��������B
     * </UL>
     *
     * @return �o�����[�I�u�W�F�N�g�̕�����\���B
     */
    @Override
    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("my.db.row.SimpleTestBlancodbSelectRow[");
        buf.append("COL_ID=" + fColId);
        buf.append(",COL_TEXT=" + fColText);
        buf.append(",COL_NUMERIC=" + fColNumeric);
        buf.append("]");
        return buf.toString();
    }
}
