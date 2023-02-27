package example

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.col

case class Embedded(
    a: String,
    b: Int
)

case class Element(
    embeddeds: Seq[Embedded]
)

object Main {

  def main(args: Array[String]): Unit = {
    val config = new SparkConf ()
      .setMaster("local[2]")
      .set("spark.sql.shuffle.partitions", "4")
      .set("spark.default.parallelism", "4")

    val session = SparkSession.builder().config(config).getOrCreate()

    val elements = Seq(
      Element(Nil),
      Element(Seq(
        Embedded(
          "x1",
          1
        ),
        Embedded(
          "x2",
          2
        )
      ))
    )

    println("Initial")
    val dataFrame = session.createDataFrame(elements)
    dataFrame.show()

    println("Select")
    val selected = dataFrame.select(col("embeddeds.b"))
    selected.show()
  }
}
