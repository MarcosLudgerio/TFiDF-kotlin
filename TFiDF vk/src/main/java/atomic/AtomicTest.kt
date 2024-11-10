package atomic

import utils.FileProperties
import utils.TFiDF

fun main() {
    val filepath: String = "D:\\repositorios-git\\TFiDF-kotlin\\TFiDF vk\\dataset\\dataset_test.txt"
    val file: FileProperties = FileProperties()
    val objTf: TFiDF = TFiDF()
    val lines = file.readDocumentAtomic(filepath)
    val countTerms = objTf.calculateTermFrequencyAtomic(lines.values.stream().toList())
    val termFrequency = objTf.calculateIDFAtomic(countTerms, countTerms.size)
    objTf.calculateTFIDFAtomic(countTerms, termFrequency)
}