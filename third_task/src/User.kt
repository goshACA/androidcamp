import java.lang.ref.WeakReference
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class User(name: String, surname: String, age: Int){
    var name: String? by weak(name)
    var surname: String? by weak(surname)
    var age: Int? by weak(age)
}

fun <R,T> weak(value: T): WeakDelegation<R, T> = WeakDelegation(value)

class WeakDelegation<R, T>(value: T) : ReadWriteProperty< R, T?> {
    private var ref: WeakReference<T?> = WeakReference(value)
    override fun getValue(thisRef: R, property: KProperty<*>): T? {
        val value = ref.get()
        println("${property.name} = $value")
        return value
    }

    override fun setValue(thisRef: R, property: KProperty<*>, value: T?) {
        ref = WeakReference(value)
        println("${property.name} is $value now")
    }
}