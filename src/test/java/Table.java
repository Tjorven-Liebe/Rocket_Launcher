public class Table {

    String tableName;
    String database;

    public Table(String database, String tableName) {
        setDatabase(database);
        setTableName(database);
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
