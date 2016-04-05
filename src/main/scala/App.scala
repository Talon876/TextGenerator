package org.nolat.textamo

object App {

  def main(args: Array[String]): Unit = {
    val corpus = new Corpus("input/example.txt")
    val textgen = new TextGenerator(corpus)
    (0 to 25).map(_ => textgen.generate(25)).foreach(println)
  }
}
