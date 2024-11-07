package serial

import utils.FileProperties
import utils.TFiDF
import java.util.concurrent.atomic.AtomicLong
import kotlin.collections.iterator

fun main(args:Array<String>){
    val filepath:String = "D:\\repositorios-git\\TFiDF-kotlin\\TFiDF vk\\dataset\\dataset_test.txt"
    val fileProperties: FileProperties = FileProperties()
    val documents:Map<Int, String> = fileProperties.readDocument(filepath)
    val objTf: TFiDF = TFiDF()
    val countTerm: MutableList<HashMap<String, AtomicLong>> = objTf.calculateTermFrequencyAtomic(documents.values.stream().toList())
    val objIdf: HashMap<String, Double> = objTf.calculateIDFAtomic(countTerm, countTerm.size)

    for (document in objIdf) {
        println("${document.key}: ${document.value}")
    }
}