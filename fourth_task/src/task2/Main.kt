package task2

import java.lang.StringBuilder

class Node(
    val name: String = "root"
) {
    val node = mutableListOf<Node>()

    private fun getChildren(): String {
        val str = StringBuilder()
        node.forEach { str.append(" $it ") }
        return str.toString()
    }

    override fun toString(): String {
        return "\nNode: $name, leafs: [${getChildren()}]\n"
    }

    private fun initNode(n: Node, init: Node.() -> Unit): Node {
        n.init()
        node.add(n)
        return n
    }

    fun node(str: String, a: Node.() -> Unit) = initNode(Node(str), a)
    fun node(str: String): Node {
        val res = Node(str)
        node.add(res)
        return res
    }
}


fun root(init: Node.() -> Unit): Node {
    val root = Node()
    root.init()
    return root
}

fun main(args: Array<String>) {
    val a = root {
        node("1") {
            node("3") {
                node("5")
            }
            node("4")
        }
        node("12")
        node("8")
    }
    print(a)
}