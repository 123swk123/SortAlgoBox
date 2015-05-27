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
package pl.tarsa.sortalgobox.random

import java.io.{File, PrintStream}
import java.nio.file.Files
import java.util.Scanner

class NativeMwc64x {
  def generate(n: Int): Array[Int] = {
    val fileNamePrefix = "mwc64x"
    val fileNameSource = s"$fileNamePrefix.cpp"
    val rootTempDir = new File(System.getProperty("java.io.tmpdir"),
      "SortAlgoBox")
    rootTempDir.mkdir()
    val tempDir = Files.createTempDirectory(rootTempDir.toPath, "native")
    val sourcePath = tempDir.resolve(fileNameSource)
    Files.copy(getClass.getResourceAsStream(
      "/pl/tarsa/sortalgobox/random/mwc64x/native/mwc64x.cpp"), sourcePath)
    val buildProcess = new ProcessBuilder("g++", "-fopenmp", "-O2",
      "-std=c++11", "-o", fileNamePrefix, fileNameSource)
      .directory(tempDir.toFile).start()
    val buildExitValue = buildProcess.waitFor()
    println(s"Build process exit value: $buildExitValue")
    val generatorProcess = new ProcessBuilder(s"./$fileNamePrefix")
      .directory(tempDir.toFile).start()
    val pipeTo = new PrintStream(generatorProcess.getOutputStream)
    pipeTo.println(n)
    pipeTo.flush()
    val pipeFrom = new Scanner(generatorProcess.getInputStream)
    val result = Array.fill[Int](n)(pipeFrom.nextLong(16).toInt)
    val generatorExitValue = generatorProcess.waitFor()
    println(s"Generator process exit value: $generatorExitValue")
    remove(tempDir.toFile)
    result
  }

  private def remove(file: File): Unit = {
    Option(file.listFiles()).foreach(_.foreach(remove))
    file.delete()
  }
}