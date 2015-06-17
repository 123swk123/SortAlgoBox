/**
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
package pl.tarsa.sortalgobox

import pl.tarsa.sortalgobox.sorts.common.SortAlgorithm
import pl.tarsa.sortalgobox.tests.CommonUnitSpecBase

class SortManagerSpec extends CommonUnitSpecBase {

  typeBehavior[SortManager]

  it should "run sorting algorithms with correct array size" in {
    val algorithm1 = mock[SortAlgorithm[Int]]
    val arraySize = 100
    (algorithm1.sort(_: Array[Int])).expects(where {
      (a: Array[Int]) =>
        a.length == arraySize
    }).once()
    val props = SortSuiteProps(arraySize)
    val algorithms = Seq(algorithm1)
    new SortManager(props, algorithms).executeAll()
  }

  it should "run sorting algorithms in order" in {
    val numAlgorithms = 5
    val algorithms = (0 until numAlgorithms)
      .map(_ => mock[SortAlgorithm[Int]])
    inSequence(algorithms.foreach { algorithm =>
      (algorithm.sort(_: Array[Int])).expects(*).once()
    })
    val props = SortSuiteProps(5)
    new SortManager(props, algorithms).executeAll()
  }
}
