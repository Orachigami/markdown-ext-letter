package com.utp1.markdown.internal

import com.utp1.markdown.node.UtpInfoNode
import com.utp1.markdown.node.UtpVariableNode
import com.utp1.markdown.node.UtpWarnNode
import org.commonmark.node.*
import org.commonmark.parser.PostProcessor

class UtpVariablePostProcessor(private val defaultValues: HashMap<String, String>) : PostProcessor {
    private val pattern = Regex("\\[#([^(]+)\\(([^)]*)\\)]")

    override fun process(node: Node): Node {
        val utpVariableVisitor = UtpVariableVisitor()
        node.accept(utpVariableVisitor)
        return node
    }

    private fun fillTextWithVariables(textNode: Text) {
        val literal = textNode.literal
        var lastNode: Node = textNode

        val sequenceIterator = pattern.findAll(literal).iterator()
        if (sequenceIterator.hasNext()) {
            var cursor = 0
            do {
                val result = sequenceIterator.next()
                val newTextNode = Text(literal.substring(cursor, result.range.first))
                val variableName = result.groupValues[1]
                val variableDefaultValue = result.groupValues[2]
                val variableNode = getNode(variableName)
                if (variableDefaultValue.isNotEmpty()) {
                    defaultValues[variableName] = variableDefaultValue
                }
                if (newTextNode.literal.isNotEmpty()) {
                    lastNode = insertNode(newTextNode, lastNode)
                }
                when(variableNode) {
                    is UtpWarnNode -> insertNode(variableNode, lastNode.parent)
                    is UtpInfoNode -> {
                        insertNode(variableNode, lastNode.parent)
                    }
                    else -> lastNode = insertNode(variableNode, lastNode)
                }
                cursor = result.range.last + 1
            } while (sequenceIterator.hasNext())
            if (literal.length > cursor) {
                val newTextNode = Text(literal.substring(cursor, literal.length))
                insertNode(newTextNode, lastNode)
            }

            val parent = textNode.parent
            textNode.unlink()
            if (parent.firstChild == null || (parent.firstChild is SoftLineBreak && parent.lastChild == parent.firstChild)) {
                parent.unlink()
            }
        }
    }

    private fun getNode(name: String): Node {
        return when (name) {
            "info" -> UtpInfoNode()
            "warn" -> UtpWarnNode()
            else -> {
                if (name.startsWith("link")) {
                    val link = Link()
                    link.destination = defaultValues[name]
                    link.title = defaultValues["${name}Title"]
                    link.appendChild(Text(link.title))
                    return link
                }
                if (name.startsWith("image")) {
                    var node = Image() as Node
                    (node as Image).destination = defaultValues[name]
                    if (defaultValues["${name}DestinationLink"] != "") {
                        val link = Link()
                        link.destination = defaultValues["${name}DestinationLink"]
                        link.appendChild(node)
                        node = link
                    }
                    return node
                }
                UtpVariableNode(name)
            }
        }
    }

    private inner class UtpVariableVisitor : AbstractVisitor() {
        override fun visit(text: Text) {
            fillTextWithVariables(text)
        }
    }

    companion object {
        private fun insertNode(node: Node, insertAfterNode: Node): Node {
            insertAfterNode.insertAfter(node)
            return node
        }
    }
}
