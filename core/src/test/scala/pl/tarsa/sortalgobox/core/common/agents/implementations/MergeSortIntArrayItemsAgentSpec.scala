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
package pl.tarsa.sortalgobox.core.common.agents.implementations

class MergeSortIntArrayItemsAgentSpec extends BaseDoubleIntArrayItemsAgentSpec(
  new MergeSortIntArrayItemsAgent(_, _)) {
  typeBehavior[MergeSortIntArrayItemsAgent]

  it must "return correct size for empty array" in {
    readTest()()(
      a => assert(a.size0 == 0),
      a => assert(a.size1 == 0))
  }

  it must "return correct size for non-empty items array" in {
    readTest(1, 2, 3)(4, 5, 6, 7)(
      a => assert(a.size0 == 3),
      a => assert(a.size1 == 4))
  }

  it must "get proper values" in {
    readTest(5, 3, 2, 8)(4, 9, 2, 1)(
      a => assert(a.get0(0) == 5),
      a => assert(a.get0(1) == 3),
      a => assert(a.get0(2) == 2),
      a => assert(a.get0(3) == 8),
      a => assert(a.get1(0) == 4),
      a => assert(a.get1(1) == 9),
      a => assert(a.get1(2) == 2),
      a => assert(a.get1(3) == 1))
  }

  it must "set proper cells" in {
    writeTest(5, 3, 2, 8)(4, 9, 2, 1)(
      _.set0(3, 1),
      _.set1(3, 5),
      _.set0(1, 2),
      _.set1(1, 0),
      _.set0(0, 9),
      _.set1(3, 2),
      _.set0(3, 7),
      _.set1(3, 2)
    )(9, 2, 2, 7)(4, 0, 2, 2)
  }

  it must "copy proper cells within items (copy0)" in {
    writeTest(5, 3, 2, 8)(4, 9, 2, 1)(
      _.copy0(1, 0, 2)
    )(3, 2, 2, 8)(4, 9, 2, 1)
  }

  it must "copy proper cells within items (copy00)" in {
    writeTest(5, 3, 2, 8)(4, 9, 2, 1)(
      _.copy00(1, 0, 2)
    )(3, 2, 2, 8)(4, 9, 2, 1)
  }

  it must "copy proper cells within buffer" in {
    writeTest(5, 3, 2, 8)(4, 9, 2, 1)(
      _.copy11(1, 0, 2)
    )(5, 3, 2, 8)(9, 2, 2, 1)
  }

  it must "copy proper cells from items to buffer" in {
    writeTest(5, 3, 2, 8)(4, 9, 2, 1)(
      _.copy01(1, 0, 2)
    )(5, 3, 2, 8)(3, 2, 2, 1)
  }

  it must "copy proper cells from buffer to items" in {
    writeTest(5, 3, 2, 8)(4, 9, 2, 1)(
      _.copy10(1, 0, 2)
    )(9, 2, 2, 8)(4, 9, 2, 1)
  }

  it must "swap proper cells within items (swap0)" in {
    writeTest(5, 3, 2, 8)(4, 9, 2, 1)(
      _.swap0(3, 0)
    )(8, 3, 2, 5)(4, 9, 2, 1)
  }

  it must "swap proper cells within items (swap00)" in {
    writeTest(5, 3, 2, 8)(4, 9, 2, 1)(
      _.swap00(3, 0)
    )(8, 3, 2, 5)(4, 9, 2, 1)
  }

  it must "swap proper cells within buffer" in {
    writeTest(5, 3, 2, 8)(4, 9, 2, 1)(
      _.swap11(3, 0)
    )(5, 3, 2, 8)(1, 9, 2, 4)
  }

  it must "swap proper cells between items and buffer" in {
    writeTest(5, 3, 2, 8)(4, 9, 2, 1)(
      _.swap01(3, 0)
    )(5, 3, 2, 4)(8, 9, 2, 1)
  }

  it must "swap proper cells between buffer and items" in {
    writeTest(5, 3, 2, 8)(4, 9, 2, 1)(
      _.swap10(3, 0)
    )(1, 3, 2, 8)(4, 9, 2, 5)
  }

  it must "compare values properly" in {
    pureTest(
      a => assert(a.compare(1, 2) == -1),
      a => assert(a.compare(2, 1) == 1),
      a => assert(a.compare(1, 1) == 0)
    )
  }

  it must "compare cells properly within items (compare0)" in {
    readTest(5, 3, 2, 8, 5)(4, 9, 2, 1, 4)(
      a => assert(a.compare0(0, 1) == 1),
      a => assert(a.compare0(2, 3) == -1),
      a => assert(a.compare0(0, 4) == 0)
    )
  }

  it must "compare cells properly within items (compare00)" in {
    readTest(5, 3, 2, 8, 5)(4, 9, 2, 1, 4)(
      a => assert(a.compare00(0, 1) == 1),
      a => assert(a.compare00(2, 3) == -1),
      a => assert(a.compare00(0, 4) == 0)
    )
  }

  it must "compare cells properly within buffer" in {
    readTest(5, 3, 2, 8, 5)(4, 9, 2, 1, 4)(
      a => assert(a.compare11(0, 1) == -1),
      a => assert(a.compare11(2, 3) == 1),
      a => assert(a.compare11(0, 4) == 0)
    )
  }

  it must "compare cells properly between items and buffer" in {
    readTest(5, 3, 2, 8, 5)(4, 9, 2, 1, 4)(
      a => assert(a.compare01(0, 1) == -1),
      a => assert(a.compare01(2, 3) == 1),
      a => assert(a.compare01(2, 2) == 0)
    )
  }

  it must "compare cells properly between buffer and items" in {
    readTest(5, 3, 2, 8, 5)(4, 9, 2, 1, 4)(
      a => assert(a.compare10(4, 0) == -1),
      a => assert(a.compare10(1, 3) == 1),
      a => assert(a.compare10(2, 2) == 0)
    )
  }
}
