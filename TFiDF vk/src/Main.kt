//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val name = "Mary"
    val age = 20

    val number: Int = 25;
    val shapes: MutableList<String> = mutableListOf("triangle", "25", "circle", "false")
    val readOnlyFruit = setOf("apple", "apple", "Banana", "cherry")
    val priceAndFruit = mapOf("apple" to 100, "kiwi" to 190, "orange" to 100)
    println("${name} is ${age} years old")
    println(readOnlyFruit.size)
    println(readOnlyFruit.count())
    println("The value of apple juice is: ${priceAndFruit["apple"]}")
    for (fruit in priceAndFruit) {
        println(fruit)
    }
}