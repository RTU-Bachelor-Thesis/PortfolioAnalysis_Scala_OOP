package org.code

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

class Portfolio(var data: ArrayBuffer[ArrayBuffer[Double]]) {
  def this(rows: Int, columns: Int) = {
    this(ArrayBuffer.fill(rows, columns)(0.0))
  }

  def deepCopy(): Portfolio = {
    val copiedData = data.map(row => row.clone())
    new Portfolio(copiedData)
  }

  def fillRandom(min: Double, max: Double): Unit = {
    val random = new Random()
    for (i <- data.indices; j <- data(i).indices) {
      data(i)(j) = random.nextDouble() * (max - min) + min
    }
  }

  def transpose(): Unit = {
    val transposedData = ArrayBuffer.fill(data(0).length, data.length)(0.0)
    for (i <- data.indices; j <- data(i).indices) {
      transposedData(j)(i) = data(i)(j)
    }
    data = transposedData
  }

  def scale(coefficient: Double): Unit = {
    data.foreach(row =>
      row.indices.foreach(j =>
        row(j) *= coefficient
      )
    )
  }

  def calculateReturnChange(): Unit = {
    val newData = ArrayBuffer.fill(data.length)(ArrayBuffer.fill(data(0).length - 1)(0.0))
    for (i <- data.indices; j <- 0 until data(i).length - 1) {
      newData(i)(j) = data(i)(j + 1) - data(i)(j)
    }
    data = newData
  }

  def combine(other: Portfolio): Unit = {
    if (data.length != other.data.length || data(0).length != other.data(0).length) {
      throw new IllegalArgumentException("Dimensions of the portfolios do not match")
    }

    for (i <- data.indices; j <- data(i).indices) {
      data(i)(j) += other.data(i)(j)
    }
  }

  def applyWeights(weights: Portfolio): Unit = {
    for (i <- data.indices; j <- data(i).indices) {
      data(i)(j) *= weights.data(i)(j)
    }
  }

  def findAssetsWithinReturnRange(period: Int, min: Double, max: Double): List[Int] =
    data.indices.filter(i => data(i)(period) > min && data(i)(period) < max).toList

  def findMaxTotalReturn: Double =
    data.map(_.sum).max

  def print(): Unit =
    data.foreach(row => println(row.map(element => f"$element%8.2f").mkString(" ")))
}

object Portfolio {
  def createWeightsDistribution(assets: Int, periods: Int): Portfolio = {
    val random = new Random()
    val weights = ArrayBuffer.fill(assets, periods)(random.nextDouble())

    val columnSums = ArrayBuffer.fill(periods)(0.0)
    for (j <- 0 until periods) {
      for (i <- 0 until assets) {
        columnSums(j) += weights(i)(j)
      }
    }

    for (i <- 0 until assets) {
      for (j <- 0 until periods) {
        weights(i)(j) /= columnSums(j)
      }
    }

    new Portfolio(weights)
  }
}
