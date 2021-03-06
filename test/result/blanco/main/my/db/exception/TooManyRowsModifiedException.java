/*
 * This code is generated by blanco Framework.
 */
package my.db.exception;

/**
 * データベースの処理の結果、1行を超えるデータが変更されてしまったことを示す例外クラス <br>
 * このクラスはblancoDbが生成したソースコードで利用されます <br>
 * ※このクラスは、ソースコード自動生成後のファイルとして利用されます。
 * @since 2005.05.12
 * @author blanco Framework
 */
public class TooManyRowsModifiedException extends NotSingleRowException {
    /**
     * このクラスを表現するSQLStateコード。<br>
     * ※このクラスを利用する際には、SQLStateには頼らずに例外クラスの型によって状態を判断するようにしてください。
     */
    protected static final String SQLSTATE_TOOMANYROWSMODIFIED = "00112";

    /**
     * データベースの処理の結果、1行を超えるデータが変更されてしまったことを示す例外クラスのインスタンスを生成します。
     *
     * @deprecated 理由を格納することができる別のコンストラクタを利用することを薦めます。
     */
    public TooManyRowsModifiedException() {
        super("Too many rows modified exception has occured..", SQLSTATE_TOOMANYROWSMODIFIED);
    }

    /**
     * データベースの処理の結果、1行を超えるデータが変更されてしまったことを示す例外クラスのインスタンスを生成します。
     *
     * @param reason 例外の説明
     */
    public TooManyRowsModifiedException(final String reason) {
        super(reason, SQLSTATE_TOOMANYROWSMODIFIED);
    }

    /**
     * データベースの処理の結果、1行を超えるデータが変更されてしまったことを示す例外クラスのインスタンスを生成します。
     *
     * @param reason 例外の説明
     * @param SQLState 例外を識別する XOPENコードまたは SQL 99のコード
     */
    public TooManyRowsModifiedException(final String reason, final String SQLState) {
        super(reason, SQLState);
    }

    /**
     * データベースの処理の結果、1行を超えるデータが変更されてしまったことを示す例外クラスのインスタンスを生成します。
     *
     * @param reason 例外の説明
     * @param SQLState 例外を識別する XOPENコードまたは SQL 99のコード
     * @param vendorCode データベースベンダーが定める固有の例外コード
     */
    public TooManyRowsModifiedException(final String reason, final String SQLState, final int vendorCode) {
        super(reason, SQLState, vendorCode);
    }
}
