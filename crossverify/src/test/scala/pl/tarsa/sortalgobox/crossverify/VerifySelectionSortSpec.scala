/*
 * Copyright (C) 2015, 2016 Piotr Tarsa ( http://github.com/tarsa )
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
 */
package pl.tarsa.sortalgobox.crossverify

import java.nio.file.Files

import org.apache.commons.io.IOUtils
import pl.tarsa.sortalgobox.common.SortAlgoBoxConfiguration._
import pl.tarsa.sortalgobox.core.common.agents.implementations.{ComparingIntArrayItemsAgent, VerifyingComparingIntArrayItemsAgent}
import pl.tarsa.sortalgobox.core.crossverify.PureNumberDecoder
import pl.tarsa.sortalgobox.random.Mwc64x
import pl.tarsa.sortalgobox.sorts.natives.NativeSelectionSort
import pl.tarsa.sortalgobox.sorts.scala.selection.SelectionSort
import pl.tarsa.sortalgobox.tests.NativesUnitSpecBase

class VerifySelectionSortSpec extends NativesUnitSpecBase {

  "Native Selection Sort" must
    "take steps identical to Scala Selection Sort" in {
    val recordingFilePath =
      Files.createTempFile(rootTempDir, "recording", ".bin")
    new NativeSelectionSort(testNativesCache, Some(recordingFilePath))
      .forSize(1234, validate = false)
    val stream = IOUtils.buffer(Files.newInputStream(recordingFilePath))
    val replayer = new PureNumberDecoder(stream)
    val generator = Mwc64x()
    val array = Array.fill[Int](1234)(generator.nextInt())
    val itemsAgent = new VerifyingComparingIntArrayItemsAgent(replayer,
      new ComparingIntArrayItemsAgent(array), b => assert(b))
    new SelectionSort().sort(itemsAgent)
  }
}
