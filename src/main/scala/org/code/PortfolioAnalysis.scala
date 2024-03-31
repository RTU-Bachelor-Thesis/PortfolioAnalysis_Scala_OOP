package org.code

object PortfolioAnalysis {
  def main(args: Array[String]): Unit = {
    val assetsCount = 10
    val periodsCount = 12

    val originalPortfolio = Portfolio.fillRandom(assetsCount, periodsCount, -10.0, 10.0)
    println("\nOriginal profitability portfolio (1): ")
    originalPortfolio.print()

    val transposedPortfolio = originalPortfolio.deepCopy()
    transposedPortfolio.transpose()
    println("\nTransposed portfolio: ")
    transposedPortfolio.print()

    val scaledPortfolio = originalPortfolio.deepCopy()
    scaledPortfolio.scale(Math.PI)
    println("\nScaled portfolio: ")
    scaledPortfolio.print()

    val returnChangePortfolio = originalPortfolio.deepCopy()
    returnChangePortfolio.calculateReturnChange()
    println("\nChange in returns: ")
    returnChangePortfolio.print()

    val additionalPortfolio = Portfolio.fillRandom(assetsCount, periodsCount, -10.0, 10.0)
    println("\nAdditional profitability portfolio (2): ")
    additionalPortfolio.print()

    originalPortfolio.combine(additionalPortfolio)
    println("\nCombined profitability of portfolios: ")
    originalPortfolio.print()

    val weightMatrix = Portfolio.createWeightsDistribution(assetsCount, periodsCount)
    println("\nWeight matrix: ")
    weightMatrix.print()

    val weightedPortfolio = originalPortfolio.deepCopy()
    weightedPortfolio.applyWeights(weightMatrix)
    println("\nWeighted portfolio: ")
    weightedPortfolio.print()

    val maxReturn = originalPortfolio.findMaxTotalReturn
    println(f"\nMaximum total return: $maxReturn%.2f")

    val rangeFilteredAssets = originalPortfolio.findAssetsWithinReturnRange(5, 2, 5)
    println(s"\nIndexes of assets with returns in June greater than 2 and less than 5: ${rangeFilteredAssets.mkString(", ")}")
  }
}
