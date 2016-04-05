package org.nolat.textamo

import java.io.File

import scala.util.Random

case class Word(word: String, followers: Set[Option[String]] = Set()) {
  override def toString = s"$word -> {${followers.map(_.getOrElse("EOL")).mkString(",")}}"

  def randomFollower(rnd: Random) = followers.toSeq(rnd.nextInt(followers.size))
}

class Corpus(val filename: String) {
  private val random = new Random()

  println(s"Loading corpus from $filename in to memory…")
  private val lines = io.Source.fromFile(new File(filename)).mkString.split("\n")

  println(s"Generating bigrams for ${lines.length} lines…")
  val bigrams = lines.map(_.trim.split(" ").toList)
    .flatMap(words => words.map(_.toLowerCase) zip (words.map(Some(_)).drop(1) :+ None))
    .groupBy(_._1)
    .map(bigramPart => bigramPart._1 -> bigramPart._2.map(_._2).toSet)
    .map(bigram => bigram._1 -> Word(bigram._1, bigram._2))

  println(s"${bigrams.size} bigrams generated…")

  def dumpBigrams() = bigrams.values.toSeq.sortBy(_.word).foreach(println)

  def randomFirstWord() = bigrams.keys.toSeq(random.nextInt(bigrams.size))
}

class TextGenerator(val corpus: Corpus) {
  private val random = new Random()

  def generate(maxWordCount: Int = 15): String = {
    var message = ""

    var nextWord: Option[String] = Some(corpus.randomFirstWord())
    for {
      i <- 1 to maxWordCount if nextWord != None
    } {
      nextWord.foreach { word =>
        message += s" $word"; nextWord = corpus.bigrams.get(word).flatMap(_.randomFollower(random))
      }
    }

    message
  }
}
