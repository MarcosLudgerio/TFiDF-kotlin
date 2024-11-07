package threaded

import com.sun.org.apache.bcel.internal.util.Args
import threaded.platform.ThreadPlatform
import threaded.virtual.ThreadVirtual


fun main(args:Array<String>) {
    val filepath:String = "D:\\repositorios-git\\TFiDF-kotlin\\TFiDF vk\\dataset\\dataset_10mb.txt"
    val virtual = ThreadVirtual(filepath)
    virtual.run()
    val documentVirtualThread = virtual.getDocument().get()
    val platform = ThreadPlatform(documentVirtualThread)
    platform.run()
    println("Finalizando threads")
}
