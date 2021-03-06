/*
 * Copyright (C) 2015 Piotr Tarsa ( http://github.com/tarsa )
 *
 * This software is provided 'as-is', without any express or implied
 * warranty.  In no event will the author be held liable for any damages
 * arising from the use of this software.
 *
 * Permission is granted to anyone to use this software for any purpose,
 * including commercial applications, and to alter it and redistribute it
 * freely, subject to the following restrictions:
 *
 * 1. The origin of this software must not be misrepresented; you must not
 * claim that you wrote the original software. If you use this software
 * in a product, an acknowledgment in the product documentation would be
 * appreciated but is not required.
 * 2. Altered source versions must be plainly marked as such, and must not be
 * misrepresented as being the original software.
 * 3. This notice may not be removed or altered from any source distribution.
 *
 */
package pl.tarsa.sortalgobox.main

import pl.tarsa.sortalgobox.core.common._
import pl.tarsa.sortalgobox.core.{Benchmark, NativeBenchmark}
import pl.tarsa.sortalgobox.sorts.natives.sab._
import pl.tarsa.sortalgobox.random.Mwc64x
import pl.tarsa.sortalgobox.sorts.jre._
import pl.tarsa.sortalgobox.sorts.natives._
import pl.tarsa.sortalgobox.sorts.opencl._
import pl.tarsa.sortalgobox.sorts.scala.bitonic.BitonicSort

object BenchmarksConfigurations {
  val plainSorts = List[(String, AnyRef)](
    "BitonicSort" -> new BitonicSort,
    "SequentialArraysSort" -> SequentialArraysSort.intSort,
    "ParallelArraySort" -> ParallelArraysSort.intSort)

  val measuredSorts: List[(String, MeasuredSortAlgorithm[Int])] = List(
    "CpuBitonicSort" -> CpuBitonicSort,
    "GpuBitonicSort" -> GpuBitonicSort,
    "CpuQuickSort" -> CpuQuickSort) ::: plainSorts.map {
    case (name, sortAlgorithm) =>
      name -> MeasuringIntSortAlgorithmWrapper(sortAlgorithm)
  }

  val nativeBenchmarks: List[NativeBenchmark] = List(
    new NativeSabHeapBinaryAheadSimpleVariantA(),
    new NativeSabHeapBinaryAheadSimpleVariantB(),
    new NativeSabHeapBinaryCached(),
    new NativeSabHeapBinaryCascadingVariantA(),
    new NativeSabHeapBinaryCascadingVariantB(),
    new NativeSabHeapBinaryCascadingVariantC(),
    new NativeSabHeapBinaryCascadingVariantD(),
    new NativeSabHeapBinaryClusteredVariantA(),
    new NativeSabHeapBinaryClusteredVariantB(),
    new NativeSabHeapBinaryOneBasedVariantA(),
    new NativeSabHeapBinaryOneBasedVariantB(),
    new NativeSabHeapHybrid(),
    new NativeSabHeapHybridCascading(),
    new NativeSabHeapQuaternaryCascadingVariantA(),
    new NativeSabHeapQuaternaryVariantA(),
    new NativeSabHeapQuaternaryVariantB(),
    new NativeSabHeapSimdDwordCascadingVariantB(),
    new NativeSabHeapSimdDwordCascadingVariantC(),
    new NativeSabHeapSimdDwordVariantB(),
    new NativeSabHeapSimdDwordVariantC(),
    new NativeSabHeapTernaryCascadingVariantA(),
    new NativeSabHeapTernaryClusteredVariantA(),
    new NativeSabHeapTernaryClusteredVariantB(),
    new NativeSabHeapTernaryOneBasedVariantA(),
    new NativeSabHeapTernaryOneBasedVariantB(),
    new NativeSabQuickRandomized(),
    new NativeStdSort)

  val benchmarks: List[Benchmark] = nativeBenchmarks :::
    measuredSorts.map {
      case (sortName: String, sort: MeasuredSortAlgorithm[Int]) =>
        new Benchmark {
          override def forSize(n: Int, validate: Boolean,
            buffer: Option[Array[Int]]): Long = {

            val array = buffer.getOrElse(Array.ofDim[Int](n))
            val rng = new Mwc64x
            array.indices.foreach(array(_) = rng.nextInt())
            val totalTime = sort.sort(array)
            if (validate) {
              assert(isSorted(array))
            }
            totalTime
          }

          def name = sortName
        }
    }

  def isSorted(ints: Array[Int]): Boolean = {
    ints.indices.tail.forall(i => ints(i - 1) <= ints(i))
  }
}
