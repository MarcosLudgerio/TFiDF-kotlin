package serial

import utils.FileProperties
import utils.TFiDF
import java.util.concurrent.atomic.AtomicLong
import kotlin.collections.iterator

fun main(args:Array<String>){
    val filepath:String = "D:\\repositorios-git\\TFiDF-kotlin\\TFiDF vk\\dataset\\dataset_10mb.txt"
    val fileProperties: FileProperties = FileProperties()
    val documents:Map<AtomicLong, String> = fileProperties.readDocumentAtomic(filepath)
    val objTf: TFiDF = TFiDF()
    val countTerm: List<Map<String, Int>> = objTf.calculateTermFrequency(documents.values.stream().toList())
    val objIdf: Map<String, Double> = objTf.calculateIDF(countTerm, documents.size)
    val objTfIdfCalculate: List<Map<String, Double>> = objTf.calculateTFIDF(countTerm, objIdf)

    objTfIdfCalculate.forEach {lines ->
        lines.forEach {
            println("${it.key}: ${it.value}")
        }
    }
}