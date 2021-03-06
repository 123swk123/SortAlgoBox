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
#ifndef ITEMS_AGENT_HPP
#define ITEMS_AGENT_HPP

#include <cstdint>

namespace tarsa {

    template<typename item_t>
    class ItemsAgent {
    public:
        size_t size0() const {}

        item_t get0(size_t const i) const {}

        void set0(size_t const i, item_t const v) const {}

        void copy0(size_t const i, size_t const j, size_t const n) const {}

        void swap0(size_t const i, size_t const j) const {}
    };
}

#endif /* ITEMS_AGENT_HPP */
