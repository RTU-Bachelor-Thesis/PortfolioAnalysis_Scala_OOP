package benchmarkings

import scala.compiletime.uninitialized
import org.openjdk.jmh.annotations._
import java.util.concurrent.TimeUnit

import org.code.Portfolio

@State(Scope.Benchmark)
@Warmup(iterations = 2)
@Measurement(iterations = 5)
@Fork(1)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Array(Mode.AverageTime))
class PortfolioBenchmark {
  @Param(Array("10", "100", "1000"))
  var assetsCount: Int = uninitialized
  var periodsCount: Int = 12
  var originalPortfolio: Portfolio = uninitialized
  var additionalPortfolio: Portfolio = uninitialized
  var weightMatrix: Portfolio = uninitialized

  @Setup(Level.Iteration)
  def setupPortfolio(): Unit = {
    originalPortfolio = new Portfolio(assetsCount, periodsCount)
    originalPortfolio.fillRandom(-10.0, 10.0)

    additionalPortfolio = new Portfolio(assetsCount, periodsCount)
    additionalPortfolio.fillRandom(-10.0, 10.0)
    
    weightMatrix = Portfolio.createWeightsDistribution(assetsCount, periodsCount)
  }

  @Benchmark
  def benchmarkFillRandom(): Unit = {
    originalPortfolio.fillRandom(-10.0, 10.0)
  }

  @Benchmark
  def benchmarkTranspose(): Unit = {
    val originalPortfolioCopy = originalPortfolio.deepCopy()
    originalPortfolioCopy.transpose()
  }

  @Benchmark
  def benchmarkScale(): Unit = {
    val originalPortfolioCopy = originalPortfolio.deepCopy()
    originalPortfolioCopy.scale(Math.PI)
  }

  @Benchmark
  def benchmarkCalculateReturnChange(): Unit = {
    val originalPortfolioCopy = originalPortfolio.deepCopy()
    originalPortfolioCopy.calculateReturnChange()
  }

  @Benchmark
  def benchmarkCombine(): Unit = {
    val originalPortfolioCopy = originalPortfolio.deepCopy()
    originalPortfolioCopy.combine(additionalPortfolio)
  }

  @Benchmark
  def benchmarkApplyWeights(): Unit = {
    val originalPortfolioCopy = originalPortfolio.deepCopy()
    originalPortfolioCopy.applyWeights(weightMatrix)
  }

  @Benchmark
  def benchmarkFindAssetsWithinReturnRange(): List[Int] = {
    originalPortfolio.findAssetsWithinReturnRange(5, 2, 5)
  }

  @Benchmark
  def benchmarkFindMaxTotalReturn(): Double = {
    originalPortfolio.findMaxTotalReturn
  }
}
