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
#ifndef SABMAIN_HPP
#define SABMAIN_HPP

#if defined(SORT_CACHED) && defined(SORT_SIMD)
#error Unsupported combination
#endif

#include xstr(SORT_HEADER)

using namespace tarsa;

#define ComparisonOperator genericComparisonOperator

template<typename item_t>
void sortPerform(items_handler_t<item_t> &itemsHandler) {
    int32_t * const work = itemsHandler.input;
    size_t const size = itemsHandler.size;
#if defined(SORT_SIMD)
    tarsa::SORT_ALGO<item_t, true>(work, size);
#elif defined(SORT_CACHED)
    tarsa::SORT_ALGO<item_t, ComparisonOperator>(work, size,
        itemsHandler.scratchpad);
#else
    tarsa::SORT_ALGO<item_t, ComparisonOperator>(work, size);
#endif
}

#endif /* SABMAIN_HPP */
