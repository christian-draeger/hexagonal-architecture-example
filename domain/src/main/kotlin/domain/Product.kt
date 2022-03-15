package domain

import kotlin.random.Random

// composed Objects are only using domain objects
data class Product(
    val sku: SKU,
    val price: Price,
    val name: Name,
)

// using inline classes removes the overhead of the wrapper, i.e. the object instantiation --> byte code optimizations
@JvmInline
value class SKU(val value: String) {
    // domain models are validating themselves
    init {
        require(isValid) { "invalid domain.SKU '$value' given" }
    }

    companion object {
        fun randomSKU(length: Int = 10): SKU = with(('A'..'Z') + ('0'..'9')) {
            (1..length)
                .map { Random.nextInt(0, size) }
                .map(::get)
                .joinToString("")
                .toSKU()
        }
    }

    // let's assume a valid domain.SKU is alphanumeric and has a length between 8 and 12 uppercase characters, e.g.: 1234AB567X
    val isValid get() = "(\\d|[A-Z]){8,12}".toRegex().matches(value)

    override fun toString() = value
}

fun String.toSKU() = SKU(this)

// for show-case simplicity let's not deal with tax here / this should probably be done by another service (product service or pricing service) anyway
data class Price(
    val inCents: Long,
    val currency: Currency = Currency.EUR
) {
    // for show-case simplicity let's not deal with country specific price formats or Money libs here
    override fun toString() =
        inCents.toBigDecimal().movePointLeft(2).let {
            "$it ${currency.symbol}"
        }

    enum class Currency(val symbol: String) {
        EUR("€"),
        USD("$"),
        GBP("£"),
    }
}

fun Number.toPrice() = Price(toLong())

@JvmInline
value class Name(val name: String) {
    override fun toString(): String = name
}
fun String.toName() = Name(this)
