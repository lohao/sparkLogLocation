package com.lohao

import java.net.InetAddress

import org.apache.spark.sql.SparkSession
;

object MultiJobTest {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().getOrCreate()

    val rdd = spark.sparkContext.parallelize(Seq(1,2,3,4),2)

    val obj1 = new Caller("test1")

    val rdd1 = rdd.map(row => {
      obj1.fun()

      println("executor1 in " + System.currentTimeMillis() + " " + InetAddress.getLocalHost)
      val obj2 = new Caller("test2")
      obj2.fun()
      println(row)
    })
    println("executor2 in" + System.currentTimeMillis() + " " + InetAddress.getLocalHost)
    val cnt = rdd1.count()
    println(s"Count: $cnt")

    val newRdd = rdd.map(x => (x, 1))
    obj1.fun()
    val newRdd1 = newRdd.reduceByKey(_ + _)
    val newRdd2 = newRdd1.map(x => x._2 + 2)

    println(newRdd2.map(x => x - 2).count())
    println("test finished!")

//    jobExecutor.execute(new Runnable {
//      override def run(): Unit = {
//        spark.sparkContext.setLocalProperty("spark.scheduler.pool", "take")
//        val data = rdd.take(10)
//        data.foreach(x => println(x))
//      }
//    })


    Thread.sleep(10000)
  }
}
