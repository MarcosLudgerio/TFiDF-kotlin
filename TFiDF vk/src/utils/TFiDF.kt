package utils

import jdk.javadoc.internal.doclets.formats.html.resources.standard
import java.util.concurrent.atomic.AtomicLong
import kotlin.math.log10

class TFiDF {
    fun calculateTermFrequency(documents: List<String>): MutableList<HashMap<String, Int>> {
        val termFrequencyList: MutableList<HashMap<String, Int>> = ArrayList()
        for (document in documents) {
            if (document != null) {
                val termFrequency: HashMap<String, Int> = HashMap()
                var words: Array<String> = document.split(" ").toTypedArray()

                for (word in words) {
                    termFrequency.put(word.lowercase(), termFrequency.getOrDefault(word, 0) + 1)
                }
                termFrequencyList.add(termFrequency)
            }
        }
        return termFrequencyList
    }

    fun calculateTermFrequencyAtomic(documents: List<String>): MutableList<HashMap<String, AtomicLong>> {
        val termFrequencyList: MutableList<HashMap<String, AtomicLong>> = ArrayList()
        for (document in documents) {
            if (document != null) {
                var termFrequencyAtomic: HashMap<String, AtomicLong> = HashMap()
                var words: Array<String> = document.split(" ").toTypedArray()
                for (word in words) {
                    val atomicLong: AtomicLong = AtomicLong()
                    atomicLong.incrementAndGet()
                    termFrequencyAtomic.put(word.lowercase(), termFrequencyAtomic.getOrDefault(word, atomicLong))
                }
                termFrequencyList.add(termFrequencyAtomic)
            }
        }
        return termFrequencyList
    }

    fun calculateIDF(termFrequencyList: List<Map<String, Int>>, numDocuments: Int): Map<String, Double> {
        var idfValues: HashMap<String, Double> = HashMap()
        val allTerms: Set<String> = termFrequencyList
            .flatMap { it.keys }
            .toSet()
        for (document in allTerms) {
            var docCount: Int = 0
            for (tf in termFrequencyList) {
                if (tf.contains(document)) {
                    docCount++
                }
            }
            val idf: Double = log10((numDocuments / (docCount + 1)).toDouble())
            idfValues.put(document, idf)
        }
        return idfValues
    }

    fun calculateIDFAtomic(termFrequencyList: List<Map<String, AtomicLong>>, numDocuments: Int): Map<String, Double> {
        val idfValues = mutableMapOf<String, Double>()

        val allTerms = termFrequencyList
            .flatMap { it.keys }
            .toSet()

        for (term in allTerms) {
            val docCount = termFrequencyList.count { it.containsKey(term) }
            val idf = log10(numDocuments.toDouble() / (docCount + 1))
            idfValues[term] = idf
        }

        return idfValues
    }

    fun calculateTFIDF(termFrequencyList: List<Map<String, Int>>, idfValues: Map<String, Double>): List<Map<String, Double>> {
        val tfidfList = mutableListOf<Map<String, Double>>()

        for (tf in termFrequencyList) {
            val tfidf = mutableMapOf<String, Double>()

            for ((term, termFreq) in tf) {
                val tfidfValue = termFreq * (idfValues[term] ?: 0.0)
                tfidf[term] = tfidfValue
            }

            tfidfList.add(tfidf)
        }
        return tfidfList
    }


}