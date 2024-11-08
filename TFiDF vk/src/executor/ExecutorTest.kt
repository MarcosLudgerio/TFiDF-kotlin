package executor

import threaded.platform.ThreadPlatform
import utils.FileProperties
import java.io.IOException
import java.util.concurrent.Executors

fun main(){
    var objFileProperties: FileProperties = FileProperties()
    val filepath: String = "D:\\repositorios-git\\TFiDF-kotlin\\TFiDF vk\\dataset\\dataset_100mb.txt"
    try {
        Executors.newVirtualThreadPerTaskExecutor().use { executorService ->
            val documents = objFileProperties.readDocumentAtomic(filepath)
            val platform: ThreadPlatform = ThreadPlatform(documents)

            executorService.submit { platform.calculateDFAndIDF() }
        }
    } catch (e: IOException) {
        println("pode dar merda: ${e.message}")
    }
}