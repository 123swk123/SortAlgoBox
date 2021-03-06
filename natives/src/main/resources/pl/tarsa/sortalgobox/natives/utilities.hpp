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
#ifndef UTILITIES_HPP
#define UTILITIES_HPP

template<typename T>
T * checkNonNull(T * const value) {
    assert(value != nullptr);
    return value;
}

template<typename T>
T const * checkNonNull(T const * const value) {
    assert(value != nullptr);
    return value;
}

template<typename T>
void checkZero(T const value) {
    assert(value == 0);
}

template<typename T>
void safeDelete(T* &pointer) {
    delete pointer;
    pointer = nullptr;
}

template<typename T>
void safeFree(T* &pointer) {
    free(pointer);
    pointer == nullptr;
}

#endif /* UTILITIES_HPP */
