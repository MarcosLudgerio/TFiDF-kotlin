package serial

import utils.FileProperties
import kotlin.collections.iterator

fun main(args:Array<String>){
    val filepath:String = "D:\\repositorios-git\\TFiDF-kotlin\\TFiDF vk\\dataset\\dataset_test.txt"


    val fileProperties: FileProperties = FileProperties()
    val documents:Map<Int, String> = fileProperties.readDocument(filepath)
    for (document in documents) {
        println(document)
    }
}