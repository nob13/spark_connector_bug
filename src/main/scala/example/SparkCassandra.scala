package example

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.col

object SparkCassandra {
  def main(args: Array[String]): Unit = {
    /* Database
    DROP TABLE IF EXISTS crash_test;
    DROP TYPE IF EXISTS embedded;

    CREATE TYPE embedded(
        a TEXT,
        b INT
    );

    CREATE TABLE crash_test(
        id INT,
        embeddeds LIST<FROZEN<embedded>>,
        PRIMARY KEY (id)
    );

    INSERT INTO crash_test JSON '{"id": 1, "embeddeds": []}';
    INSERT INTO crash_test JSON '{"id": 1, "embeddeds": [{"a": "x1", "b": 1}, {"a": "x2", "b": 2}]}';
     */


    val dbUser = sys.env("CASSANDRA_USER")
    val dbPassword = sys.env("CASSANDRA_PASSWORD")
    val dbKeyspace = sys.env("CASSANDRA_KEYSPACE")
    val config = new SparkConf()
      .setMaster("local[2]")
      .set("spark.sql.shuffle.partitions", "4")
      .set("spark.default.parallelism", "4")
      .set("spark.cassandra.auth.username", dbUser)
      .set("spark.cassandra.auth.password", dbPassword)

    val session = SparkSession.builder().config(config).getOrCreate()
    val dataFrame = session.read.format("org.apache.spark.sql.cassandra")
      .options(Map("table" -> "crash_test", "keyspace" -> dbKeyspace))
      .load

    println("Initial")
    dataFrame.show()

    println("Select")
    val selected = dataFrame.select(col("embeddeds.b"))
    selected.show() // This is crashing
  }
}
