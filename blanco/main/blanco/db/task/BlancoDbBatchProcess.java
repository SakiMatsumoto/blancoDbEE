/*
 * ���̃\�[�X�R�[�h�� blanco Framework�ɂ�莩����������܂����B
 */
package blanco.db.task;

import java.io.IOException;

import blanco.db.task.valueobject.BlancoDbProcessInput;

/**
 * �o�b�`�����N���X [BlancoDbBatchProcess]�B
 *
 * <P>�o�b�`�����̌Ăяo����B</P>
 * <code>
 * java -classpath (�N���X�p�X) blanco.db.task.BlancoDbBatchProcess -help
 * </code>
 */
public class BlancoDbBatchProcess {
    /**
     * ����I���B
     */
    public static final int END_SUCCESS = 0;

    /**
     * ���ُ͈�I���B�����I��java.lang.IllegalArgumentException�����������ꍇ�B
     */
    public static final int END_ILLEGAL_ARGUMENT_EXCEPTION = 7;

    /**
     * ���o�͗�O�I���B�����I��java.io.IOException�����������ꍇ�B
     */
    public static final int END_IO_EXCEPTION = 8;

    /**
     * �ُ�I���B�o�b�`�̏����J�n�Ɏ��s�����ꍇ�A����ѓ����I��java.lang.Error�܂���java.lang.RuntimeException�����������ꍇ�B
     */
    public static final int END_ERROR = 9;

    /**
     * �R�}���h���C��������s���ꂽ�ۂ̃G���g���|�C���g�ł��B
     *
     * @param args �R���\�[����������p���ꂽ�����B
     */
    public static final void main(final String[] args) {
        final BlancoDbBatchProcess batchProcess = new BlancoDbBatchProcess();

        // �o�b�`�����̈����B
        final BlancoDbProcessInput input = new BlancoDbProcessInput();

        boolean isNeedUsage = false;
        boolean isFieldJdbcdriverProcessed = false;
        boolean isFieldJdbcurlProcessed = false;
        boolean isFieldJdbcuserProcessed = false;
        boolean isFieldJdbcpasswordProcessed = false;
        boolean isFieldMetadirProcessed = false;
        boolean isFieldBasepackageProcessed = false;

        // �R�}���h���C�������̉�͂������Ȃ��܂��B
        for (int index = 0; index < args.length; index++) {
            String arg = args[index];
            if (arg.startsWith("-verbose=")) {
                input.setVerbose(Boolean.valueOf(arg.substring(9)).booleanValue());
            } else if (arg.startsWith("-jdbcdriver=")) {
                input.setJdbcdriver(arg.substring(12));
                isFieldJdbcdriverProcessed = true;
            } else if (arg.startsWith("-jdbcurl=")) {
                input.setJdbcurl(arg.substring(9));
                isFieldJdbcurlProcessed = true;
            } else if (arg.startsWith("-jdbcuser=")) {
                input.setJdbcuser(arg.substring(10));
                isFieldJdbcuserProcessed = true;
            } else if (arg.startsWith("-jdbcpassword=")) {
                input.setJdbcpassword(arg.substring(14));
                isFieldJdbcpasswordProcessed = true;
            } else if (arg.startsWith("-jdbcdriverfile=")) {
                input.setJdbcdriverfile(arg.substring(16));
            } else if (arg.startsWith("-metadir=")) {
                input.setMetadir(arg.substring(9));
                isFieldMetadirProcessed = true;
            } else if (arg.startsWith("-tmpdir=")) {
                input.setTmpdir(arg.substring(8));
            } else if (arg.startsWith("-targetdir=")) {
                input.setTargetdir(arg.substring(11));
            } else if (arg.startsWith("-basepackage=")) {
                input.setBasepackage(arg.substring(13));
                isFieldBasepackageProcessed = true;
            } else if (arg.startsWith("-runtimepackage=")) {
                input.setRuntimepackage(arg.substring(16));
            } else if (arg.startsWith("-schema=")) {
                input.setSchema(arg.substring(8));
            } else if (arg.startsWith("-table=")) {
                input.setTable(arg.substring(7));
            } else if (arg.startsWith("-sql=")) {
                input.setSql(arg.substring(5));
            } else if (arg.startsWith("-failonerror=")) {
                input.setFailonerror(arg.substring(13));
            } else if (arg.startsWith("-log=")) {
                input.setLog(arg.substring(5));
            } else if (arg.startsWith("-logmode=")) {
                input.setLogmode(arg.substring(9));
            } else if (arg.startsWith("-logsql=")) {
                input.setLogsql(arg.substring(8));
            } else if (arg.startsWith("-statementtimeout=")) {
                input.setStatementtimeout(arg.substring(18));
            } else if (arg.startsWith("-executesql=")) {
                input.setExecutesql(arg.substring(12));
            } else if (arg.startsWith("-encoding=")) {
                input.setEncoding(arg.substring(10));
            } else if (arg.startsWith("-convertStringToMsWindows31jUnicode=")) {
                input.setConvertStringToMsWindows31jUnicode(arg.substring(36));
            } else if (arg.startsWith("-cache=")) {
                input.setCache(arg.substring(7));
            } else if (arg.equals("-?") || arg.equals("-help")) {
                usage();
                System.exit(END_SUCCESS);
            } else {
                System.out.println("BlancoDbBatchProcess: ���̓p�����[�^[" + arg + "]�͖�������܂����B");
                isNeedUsage = true;
            }
        }

        if (isNeedUsage) {
            usage();
        }

        if( isFieldJdbcdriverProcessed == false) {
            System.out.println("BlancoDbBatchProcess: �����J�n���s�B���̓p�����[�^[input]�̕K�{�t�B�[���h�l[jdbcdriver]�ɒl���ݒ肳��Ă��܂���B");
            System.exit(END_ILLEGAL_ARGUMENT_EXCEPTION);
        }
        if( isFieldJdbcurlProcessed == false) {
            System.out.println("BlancoDbBatchProcess: �����J�n���s�B���̓p�����[�^[input]�̕K�{�t�B�[���h�l[jdbcurl]�ɒl���ݒ肳��Ă��܂���B");
            System.exit(END_ILLEGAL_ARGUMENT_EXCEPTION);
        }
        if( isFieldJdbcuserProcessed == false) {
            System.out.println("BlancoDbBatchProcess: �����J�n���s�B���̓p�����[�^[input]�̕K�{�t�B�[���h�l[jdbcuser]�ɒl���ݒ肳��Ă��܂���B");
            System.exit(END_ILLEGAL_ARGUMENT_EXCEPTION);
        }
        if( isFieldJdbcpasswordProcessed == false) {
            System.out.println("BlancoDbBatchProcess: �����J�n���s�B���̓p�����[�^[input]�̕K�{�t�B�[���h�l[jdbcpassword]�ɒl���ݒ肳��Ă��܂���B");
            System.exit(END_ILLEGAL_ARGUMENT_EXCEPTION);
        }
        if( isFieldMetadirProcessed == false) {
            System.out.println("BlancoDbBatchProcess: �����J�n���s�B���̓p�����[�^[input]�̕K�{�t�B�[���h�l[metadir]�ɒl���ݒ肳��Ă��܂���B");
            System.exit(END_ILLEGAL_ARGUMENT_EXCEPTION);
        }
        if( isFieldBasepackageProcessed == false) {
            System.out.println("BlancoDbBatchProcess: �����J�n���s�B���̓p�����[�^[input]�̕K�{�t�B�[���h�l[basepackage]�ɒl���ݒ肳��Ă��܂���B");
            System.exit(END_ILLEGAL_ARGUMENT_EXCEPTION);
        }

        int retCode = batchProcess.execute(input);

        // �I���R�[�h��߂��܂��B
        // �����ӁFSystem.exit()���Ăяo���Ă���_�ɒ��ӂ��Ă��������B
        System.exit(retCode);
    }

    /**
     * ��̓I�ȃo�b�`�������e���L�q���邽�߂̃��\�b�h�ł��B
     *
     * ���̃��\�b�h�Ɏ��ۂ̏������e���L�q���܂��B
     *
     * @param input �o�b�`�����̓��̓p�����[�^�B
     * @return �o�b�`�����̏I���R�[�h�BEND_SUCCESS, END_ILLEGAL_ARGUMENT_EXCEPTION, END_IO_EXCEPTION, END_ERROR �̂����ꂩ�̒l��߂��܂��B
     * @throws IOException ���o�͗�O�����������ꍇ�B
     * @throws IllegalArgumentException ���͒l�ɕs�������������ꍇ�B
     */
    public int process(final BlancoDbProcessInput input) throws IOException, IllegalArgumentException {
        // ���̓p�����[�^���`�F�b�N���܂��B
        validateInput(input);

        // ���̉ӏ��ŃR���p�C���G���[����������ꍇ�ABlancoDbProcess�C���^�t�F�[�X���������� blanco.db.task�p�b�P�[�W�� BlancoDbProcessImpl�N���X���쐬���邱�Ƃɂ������ł���ꍇ������܂��B
        final BlancoDbProcess process = new BlancoDbProcessImpl();

        // �����̖{�̂����s���܂��B
        final int retCode = process.execute(input);

        return retCode;
    }

    /**
     * �N���X���C���X�^���X�����ăo�b�`�����s����ۂ̃G���g���|�C���g�ł��B
     *
     * ���̃��\�b�h�͉��L�̎d�l��񋟂��܂��B
     * <ul>
     * <li>���\�b�h�̓��̓p�����[�^�̓��e�`�F�b�N�B
     * <li>IllegalArgumentException, RuntimeException, Error�Ȃǂ̗�O��catch���Ė߂�l�ւƕϊ��B
     * </ul>
     *
     * @param input �o�b�`�����̓��̓p�����[�^�B
     * @return �o�b�`�����̏I���R�[�h�BEND_SUCCESS, END_ILLEGAL_ARGUMENT_EXCEPTION, END_IO_EXCEPTION, END_ERROR �̂����ꂩ�̒l��߂��܂��B
     * @throws IllegalArgumentException ���͒l�ɕs�������������ꍇ�B
     */
    public final int execute(final BlancoDbProcessInput input) throws IllegalArgumentException {
        try {
            // �o�b�`�����̖{�̂����s���܂��B
            int retCode = process(input);

            return retCode;
        } catch (IllegalArgumentException ex) {
            System.out.println("BlancoDbBatchProcess: ���͗�O���������܂����B�o�b�`�����𒆒f���܂��B:" + ex.toString());
            // ���ُ͈�I���B
            return END_ILLEGAL_ARGUMENT_EXCEPTION;
        } catch (IOException ex) {
            System.out.println("BlancoDbBatchProcess: ���o�͗�O���������܂����B�o�b�`�����𒆒f���܂��B:" + ex.toString());
            // ���ُ͈�I���B
            return END_IO_EXCEPTION;
        } catch (RuntimeException ex) {
            System.out.println("BlancoDbBatchProcess: �����^�C����O���������܂����B�o�b�`�����𒆒f���܂��B:" + ex.toString());
            ex.printStackTrace();
            // �ُ�I���B
            return END_ERROR;
        } catch (Error er) {
            System.out.println("BlancoDbBatchProcess: �����^�C���G���[���������܂����B�o�b�`�����𒆒f���܂��B:" + er.toString());
            er.printStackTrace();
            // �ُ�I���B
            return END_ERROR;
        }
    }

    /**
     * ���̃o�b�`�����N���X�̎g�����̐�����W���o�͂Ɏ������߂̃��\�b�h�ł��B
     */
    public static final void usage() {
        System.out.println("BlancoDbBatchProcess: Usage:");
        System.out.println("  java blanco.db.task.BlancoDbBatchProcess -verbose=�l1 -jdbcdriver=�l2 -jdbcurl=�l3 -jdbcuser=�l4 -jdbcpassword=�l5 -jdbcdriverfile=�l6 -metadir=�l7 -tmpdir=�l8 -targetdir=�l9 -basepackage=�l10 -runtimepackage=�l11 -schema=�l12 -table=�l13 -sql=�l14 -failonerror=�l15 -log=�l16 -logmode=�l17 -logsql=�l18 -statementtimeout=�l19 -executesql=�l20 -encoding=�l21 -convertStringToMsWindows31jUnicode=�l22 -cache=�l23");
        System.out.println("    -verbose");
        System.out.println("      ����[verbose���[�h�œ��삳���邩�ǂ����B]");
        System.out.println("      �^[�^�U]");
        System.out.println("      �f�t�H���g�l[false]");
        System.out.println("    -jdbcdriver");
        System.out.println("      ����[JDBC�h���C�o�̃N���X�����w�肵�܂��B]");
        System.out.println("      �^[������]");
        System.out.println("      �K�{�p�����[�^");
        System.out.println("    -jdbcurl");
        System.out.println("      ����[JDBC�ڑ���URL���w�肵�܂��B]");
        System.out.println("      �^[������]");
        System.out.println("      �K�{�p�����[�^");
        System.out.println("    -jdbcuser");
        System.out.println("      ����[JDBC�f�[�^�x�[�X�ڑ����s���ۂ̃��[�U�����w�肵�܂��B]");
        System.out.println("      �^[������]");
        System.out.println("      �K�{�p�����[�^");
        System.out.println("    -jdbcpassword");
        System.out.println("      ����[JDBC�f�[�^�x�[�X�ڑ����s���ۂ̃p�X���[�h���w�肵�܂��B]");
        System.out.println("      �^[������]");
        System.out.println("      �K�{�p�����[�^");
        System.out.println("    -jdbcdriverfile");
        System.out.println("      ����[JDBC�h���C�o�� jar �t�@�C�������w�肵�܂��B�ʏ�͗��p���܂���B]");
        System.out.println("      �^[������]");
        System.out.println("    -metadir");
        System.out.println("      ����[SQL��`���^�t�@�C�����i�[����Ă���f�B���N�g�����w�肵�܂��B]");
        System.out.println("      �^[������]");
        System.out.println("      �K�{�p�����[�^");
        System.out.println("    -tmpdir");
        System.out.println("      ����[�e���|�����t�H���_���w�肵�܂��B���w��̏ꍇ�ɂ̓J�����g������tmp�t�H���_�𗘗p���܂��B]");
        System.out.println("      �^[������]");
        System.out.println("      �f�t�H���g�l[tmp]");
        System.out.println("    -targetdir");
        System.out.println("      ����[blancoDb��Java�\�[�X�R�[�h���o�͂���f�B���N�g�����w�肵�܂��B]");
        System.out.println("      �^[������]");
        System.out.println("      �f�t�H���g�l[blanco]");
        System.out.println("    -basepackage");
        System.out.println("      ����[blancoDb��Java�\�[�X�R�[�h�𐶐�����ۂ̊�ƂȂ�p�b�P�[�W�����w�肵�܂��B]");
        System.out.println("      �^[������]");
        System.out.println("      �K�{�p�����[�^");
        System.out.println("    -runtimepackage");
        System.out.println("      ����[�����^�C���N���X�𐶐����鐶������w�肵�܂��B���w��̏ꍇ�ɂ� basepackage����ɐ�������܂��B]");
        System.out.println("      �^[������]");
        System.out.println("    -schema");
        System.out.println("      ����[�P��\�����擾����ۂ̃X�L�[�}���B��{�I�ɖ��w��ł��B������Oracle�̏ꍇ�ɂ̂݁A���[�U����啶�����������̂��w�肵�܂��BOracle�̏ꍇ�� ������w�肵�Ȃ��ƁA�V�X�e���\�܂Ō������Ă��܂��s����������邽�߂ł��B]");
        System.out.println("      �^[������]");
        System.out.println("    -table");
        System.out.println("      ����[true��ݒ肷��ƒP��\�̂��߂̃A�N�Z�T�E�R�[�h�𐶐����܂��B]");
        System.out.println("      �^[������]");
        System.out.println("      �f�t�H���g�l[false]");
        System.out.println("    -sql");
        System.out.println("      ����[true��ݒ肷���SQL��`����R�[�h�𐶐����܂��B]");
        System.out.println("      �^[������]");
        System.out.println("      �f�t�H���g�l[false]");
        System.out.println("    -failonerror");
        System.out.println("      ����[SQL ��`������\�[�X�R�[�h�����Ɏ��s�����ۂɏ����𒆒f���܂��B]");
        System.out.println("      �^[������]");
        System.out.println("      �f�t�H���g�l[false]");
        System.out.println("    -log");
        System.out.println("      ����[true��ݒ肷��� Jakarta Commons�p�̃��M���O�R�[�h�𐶐����܂��B]");
        System.out.println("      �^[������]");
        System.out.println("      �f�t�H���g�l[false]");
        System.out.println("    -logmode");
        System.out.println("      ����[���O���[�h�̎w��Bdebug, performance, sqlid �̂����ꂩ�̒l���w��B]");
        System.out.println("      �^[������]");
        System.out.println("      �f�t�H���g�l[debug]");
        System.out.println("    -logsql");
        System.out.println("      ����[SQL �����O�ŏo�͂��邩�ǂ����̃t���O�B�ulog�v��ulogmode�v�̓g���[�X���x���̃��O��f�����A�ulogsql�v�́A���ǐ��̂��郍�O���o���B]");
        System.out.println("      �^[������]");
        System.out.println("      �f�t�H���g�l[false]");
        System.out.println("    -statementtimeout");
        System.out.println("      ����[�X�e�[�g�����g�̃^�C���A�E�g�l�BSQL���̃^�C���A�E�g���������l��ݒ肵�܂��BsetQueryTimeout�ɔ��f����܂��B���w��̏ꍇ�ɂ�API�f�t�H���g�B]");
        System.out.println("      �^[������]");
        System.out.println("    -executesql");
        System.out.println("      ����[�\�[�X�R�[�h������������SQL��`��SQL�������s���邩�ǂ�����ݒ肷��t���O�B�f�t�H���g�� iterator�Biterator:�����^�̂�SQL�������s���Č��؂���Bnone:SQL���͎��s���Ȃ��B]");
        System.out.println("      �^[������]");
        System.out.println("      �f�t�H���g�l[iterator]");
        System.out.println("    -encoding");
        System.out.println("      ����[������������\�[�X�t�@�C���̕����G���R�[�f�B���O���w�肵�܂��B]");
        System.out.println("      �^[������]");
        System.out.println("    -convertStringToMsWindows31jUnicode");
        System.out.println("      ����[������ɂ��āAMicrosoft Windows 3.1���{��ł̃��j�R�[�h�ւƕϊ����邩�ǂ����B�������ʂɔ��f����܂��B]");
        System.out.println("      �^[������]");
        System.out.println("      �f�t�H���g�l[false]");
        System.out.println("    -cache");
        System.out.println("      ����[��`�����^�t�@�C�����璆��XML�t�@�C���ւ̕ϊ����L���b�V���ōς܂����ǂ����̃t���O�B]");
        System.out.println("      �^[������]");
        System.out.println("      �f�t�H���g�l[false]");
        System.out.println("    -? , -help");
        System.out.println("      ����[�g������\�����܂��B]");
    }

    /**
     * ���̃o�b�`�����N���X�̓��̓p�����[�^�̑Ó����`�F�b�N�����{���邽�߂̃��\�b�h�ł��B
     *
     * @param input �o�b�`�����̓��̓p�����[�^�B
     * @throws IllegalArgumentException ���͒l�ɕs�������������ꍇ�B
     */
    public void validateInput(final BlancoDbProcessInput input) throws IllegalArgumentException {
        if (input == null) {
            throw new IllegalArgumentException("BlancoBatchProcessBatchProcess: �����J�n���s�B���̓p�����[�^[input]��null���^�����܂����B");
        }
        if (input.getJdbcdriver() == null) {
            throw new IllegalArgumentException("BlancoDbBatchProcess: �����J�n���s�B���̓p�����[�^[input]�̕K�{�t�B�[���h�l[jdbcdriver]�ɒl���ݒ肳��Ă��܂���B");
        }
        if (input.getJdbcurl() == null) {
            throw new IllegalArgumentException("BlancoDbBatchProcess: �����J�n���s�B���̓p�����[�^[input]�̕K�{�t�B�[���h�l[jdbcurl]�ɒl���ݒ肳��Ă��܂���B");
        }
        if (input.getJdbcuser() == null) {
            throw new IllegalArgumentException("BlancoDbBatchProcess: �����J�n���s�B���̓p�����[�^[input]�̕K�{�t�B�[���h�l[jdbcuser]�ɒl���ݒ肳��Ă��܂���B");
        }
        if (input.getJdbcpassword() == null) {
            throw new IllegalArgumentException("BlancoDbBatchProcess: �����J�n���s�B���̓p�����[�^[input]�̕K�{�t�B�[���h�l[jdbcpassword]�ɒl���ݒ肳��Ă��܂���B");
        }
        if (input.getMetadir() == null) {
            throw new IllegalArgumentException("BlancoDbBatchProcess: �����J�n���s�B���̓p�����[�^[input]�̕K�{�t�B�[���h�l[metadir]�ɒl���ݒ肳��Ă��܂���B");
        }
        if (input.getBasepackage() == null) {
            throw new IllegalArgumentException("BlancoDbBatchProcess: �����J�n���s�B���̓p�����[�^[input]�̕K�{�t�B�[���h�l[basepackage]�ɒl���ݒ肳��Ă��܂���B");
        }
    }
}