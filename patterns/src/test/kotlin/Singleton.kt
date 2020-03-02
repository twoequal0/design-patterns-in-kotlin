import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

object PrinterDriver {
    init {
        println("Initializing with object: $this")
    }

    fun print(): PrinterDriver =
            apply { println("Printing with object: $this") }
}

class SingletonWithParameter {
    init {
        println("Initializing with $this")
    }

    companion object {
        @Volatile
        private var INSTANCE: SingletonWithParameter? = null

        fun getInstance(context: String): SingletonWithParameter {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildSingletonWithParameter(context).also { INSTANCE = it }
            }
        }

        private fun buildSingletonWithParameter(context: String): SingletonWithParameter {
            println("print parameter $context")
            return SingletonWithParameter()
        }
    }
}

class SingletonTest {

    @Test
    fun singletonByObject() {
        println("Start")
        val printerFirst = PrinterDriver.print()
        val printerSecond = PrinterDriver.print()

        assertThat(printerFirst).isSameAs(PrinterDriver)
        assertThat(printerSecond).isSameAs(PrinterDriver)
    }

    @Test
    fun singletonWithCompanionObject() {
        println("start")

        val firstInstance = SingletonWithParameter.getInstance("context")
        val secondInstance = SingletonWithParameter.getInstance("context")

        assertThat(firstInstance).isSameAs(secondInstance)
        assertThat(secondInstance).isSameAs(firstInstance)
    }
}
