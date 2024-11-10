package forkandjoin

import utils.TFiDF
import java.util.concurrent.RecursiveTask

class TermFrequencyTask(
    private val documents: Array<String>,
    private val start: Int,
    private val end: Int
) : RecursiveTask<List<Map<String, Int>>>() {

    companion object {
        private const val THRESHOLD = 10 // Limite para divisão de tarefas
    }

    val objTFiDF: TFiDF = TFiDF()

    override fun compute(): List<Map<String, Int>> {
        return if (end - start <= THRESHOLD) {
            return objTFiDF.calculateTermFrequency(documents.toList().subList(start, end))
        } else {
            val middle = (start + end) / 2
            val leftTask = TermFrequencyTask(documents, start, middle)
            val rightTask = TermFrequencyTask(documents, middle, end)

            leftTask.fork() // Inicia a tarefa à esquerda em paralelo
            val rightResult = rightTask.compute() // Calcula o lado direito
            val leftResult = leftTask.join() // Aguarda a conclusão do lado esquerdo

            return leftResult + rightResult
        }
    }

}