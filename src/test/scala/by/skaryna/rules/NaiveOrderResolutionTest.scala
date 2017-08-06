package by.skaryna.rules

import org.apache.spark.sql.catalyst.plans.logical.{LogicalPlan, Project, Sort}
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.LongType
import org.scalatest.{BeforeAndAfter, FlatSpec}

/**
  * Created by yuri on 8/6/17.
  */
class NaiveOrderResolutionTest extends FlatSpec with BeforeAndAfter  {
  private var spark: SparkSession = _

  before {
    spark = SparkSession.builder.
      master("local[*]")
      .withExtensions(extensions => extensions.injectOptimizerRule(session => NaiveOrderResolution))
      .config("spark.ui.enabled", "false")
      .getOrCreate()
  }

  after {
    if (spark != null) {
      spark.stop()
    }
  }

  "NaiveOrderResolution" should "avoid unnecessary sorting" in {
    val df = generateDataFrame(10)
    val sorted = df.sort("key")
    val renamed = sorted.withColumnRenamed("key", "key2")
    val sortedAgain = renamed.sort("key2")
    assert(checkOptimizedPlan(sortedAgain.queryExecution.optimizedPlan))
  }

  private def checkOptimizedPlan(logicalPlan: LogicalPlan): Boolean = logicalPlan match {
    case Sort(_, _, Project(_, Project(_, _))) => true
    case _ => false
  }

  private def generateDataFrame(cnt: Int): DataFrame = {
    val ids = spark.sqlContext.range(0, cnt)
    ids.withColumn("key", (rand() * 1000000).cast(LongType))
  }
}
