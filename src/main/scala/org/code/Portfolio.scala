package org.code

import scala.util.Random

class Portfolio(var data: Array[Array[Double]]) {
  def deepCopy(): Portfolio = {
    val copiedData = data.map(_.clone())
    new Portfolio(copiedData)
  }

  def transpose(): Unit = {
    val transposedData = Array.tabulate(data(0).length, data.length)((j, i) => data(i)(j))
    data = transposedData
  }

  def scale(coefficient: Double): Unit = {
    for (i <- data.indices; j <- data(i).indices) {
      data(i)(j) *= coefficient
    }
  }

  def calculateReturnChange(): Unit = {
    val newData = Array.ofDim[Double](data.length, data(0).length - 1)
    for (i <- data.indices; j <- 0 until data(i).length - 1) {
      newData(i)(j) = data(i)(j + 1) - data(i)(j)
    }
    data = newData
  }

  def combine(other: Portfolio): Unit = {
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
  def fillRandom(rows: Int, columns: Int, min: Double, max: Double): Portfolio = {
    val data = Array.fill(rows, columns)(0.0)
    val rnd = new Random()
    for (i <- data.indices; j <- data(i).indices) {
      data(i)(j) = min + rnd.nextDouble() * (max - min)
    }
    new Portfolio(data)
  }

  def createWeightsDistribution(assets: Int, periods: Int): Portfolio = {
    val random = new Random()
    val weights = Array.fill(periods, assets)(random.nextDouble())
    val normalizedWeights = weights.map { row =>
      val sum = row.sum
      row.map(_ / sum)
    }
    new Portfolio(normalizedWeights.transpose)
  }

  def combine(portfolio1: Portfolio, portfolio2: Portfolio): Portfolio = {
    val combinedData = portfolio1.data.zip(portfolio2.data).map {
      case (row1, row2) => row1.zip(row2).map { case (v1, v2) => v1 + v2 }
    }
    new Portfolio(combinedData)
  }
}
