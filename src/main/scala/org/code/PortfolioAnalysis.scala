package org.code

object PortfolioAnalysis {
  def main(args: Array[String]): Unit = {
    val assetsCount = 10
    val periodsCount = 12

    val originalPortfolio = new Portfolio(assetsCount, periodsCount)
    originalPortfolio.fillRandom(-10.0, 10.0)
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
    println("\nChange in returns on assets: ")
    returnChangePortfolio.print()

    val additionalPortfolio = new Portfolio(assetsCount, periodsCount)
    additionalPortfolio.fillRandom(-10.0, 10.0)
    println("\nAdditional profitability portfolio (2): ")
    additionalPortfolio.print()

    val combinedPortfolio = originalPortfolio.deepCopy()
    combinedPortfolio.combine(additionalPortfolio)
    println("\nTotal profitability of portfolios: ")
    combinedPortfolio.print()

    val weightMatrix = Portfolio.createWeightsDistribution(assetsCount, periodsCount)
    println("\nWeight matrix: ")
    weightMatrix.print()

    val weightedPortfolio = originalPortfolio.deepCopy()
    weightedPortfolio.applyWeights(weightMatrix)
    println("\nWeighted portfolio: ")
    weightedPortfolio.print()

    val rangeFilteredAssets = originalPortfolio.findAssetsWithinReturnRange(5, 2, 5)
    println(s"\nIndexes of assets with returns in June greater than 2 and less than 5: ${rangeFilteredAssets.mkString(", ")}")

    val maxYearProfitability = originalPortfolio.findMaxTotalReturn
    println(f"\nMaximum year profitability: $maxYearProfitability%.2f")
  }
}
