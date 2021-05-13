package com.utp1.markdown.internal

import org.commonmark.node.AbstractVisitor
import org.commonmark.node.HardLineBreak
import org.commonmark.node.Node
import org.commonmark.node.SoftLineBreak
import org.commonmark.parser.PostProcessor

class UtpWarnPostProcessor : PostProcessor {
    override fun process(node: Node): Node {
        val utpWarnVisitor = UtpWarnVisitor()
        node.accept(utpWarnVisitor)
        return node
    }

    private fun replaceSoftBreakWithHardBreak(softLineBreak: SoftLineBreak) {
        insertNode(HardLineBreak(), softLineBreak)
        softLineBreak.unlink()
    }

    private inner class UtpWarnVisitor : AbstractVisitor() {
        override fun visit(softLineBreak: SoftLineBreak) {
            replaceSoftBreakWithHardBreak(softLineBreak)
        }
    }

    companion object {
        private fun insertNode(node: Node, insertAfterNode: Node): Node {
            insertAfterNode.insertAfter(node)
            return node
        }
    }
}
