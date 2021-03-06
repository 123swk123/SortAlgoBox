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
#define NDEBUG

#include <algorithm>
#include <cassert>
#include <chrono>
#include <cstdint>
#include <cstdlib>
#include <iostream>
#include <utility>

#include "macros.hpp"
#include "utilities.hpp"

#include "items_handler.hpp"
#include "mwc64x.hpp"

#include xstr(SORT_MECHANICS)

int main(int argc, char** argv) {
    bool shouldValidate;
    size_t size;
    std::cin >> shouldValidate >> size;

    int32_t * work;
    checkZero(posix_memalign((void**) &work, 128, sizeof (int32_t) * size));
    mwc64xFill(work, size);

    items_handler_t<int32_t> itemsHandler = sortItemsHandlerPrepare(work, size);

    auto startingChrono = std::chrono::system_clock::now();
    sortPerform(itemsHandler);
    auto elapsedChrono = std::chrono::system_clock::now() - startingChrono;
    uint64_t elapsedChronoNanoseconds = std::chrono::duration_cast<std::chrono
        ::nanoseconds>(elapsedChrono).count();
    printf("%lx\n", elapsedChronoNanoseconds);

    sortItemsHandlerReleasePreValidation(itemsHandler);

    if (shouldValidate) {
        bool const valid = sortValidate(itemsHandler);
        std::cout << (valid ? "pass" : "fail") << std::endl;
    }

    sortItemsHandlerReleasePostValidation(itemsHandler);

    return EXIT_SUCCESS;
}
