class Challenge6 {

}

fun main(args: Array<String>) {
    // Simple test
    val string1 = "this is a test"
    val string2 = "wokka wokka!!!"
    println("The distance between '$string1' and '$string2' is ${(string1 xor string2).bitCount()}")
}