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
package pl.tarsa.sortalgobox.sorts.selection

import pl.tarsa.sortalgobox.sorts.common.ArrayHelpers._
import pl.tarsa.sortalgobox.sorts.common.ComparisonsSupport._
import pl.tarsa.sortalgobox.sorts.common.SortAlgorithm

class SelectionSort[T: Conv] extends SortAlgorithm[T] {
  override def sort(array: Array[T]): Unit = {
    for (i <- 0 to array.length - 2) {
      val j = array.view.zipWithIndex.drop(i).minBy(_._1)._2
      swap(array, i, j)
    }
  }
}
