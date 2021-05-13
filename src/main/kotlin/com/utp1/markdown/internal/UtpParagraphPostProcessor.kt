package com.utp1.markdown.internal

import org.commonmark.node.*
import org.commonmark.parser.PostProcessor

class UtpParagraphPostProcessor : PostProcessor {
    override fun process(node: Node): Node {
        val utpParagraphVisitor = UtpParagraphVisitor()
        node.accept(utpParagraphVisitor)
        return node
    }

    private fun splitParagraphsBySoftLineBreak(softLineBreak: SoftLineBreak) {
        if (softLineBreak.parent is Paragraph) {
            if (softLineBreak.previous != null) {
                var lastParagraph = softLineBreak.parent
                var lastSoftLineBreak = softLineBreak
                while (lastSoftLineBreak.next != null) {
                    val newParagraph = Paragraph()
                    moveAllChildren(lastSoftLineBreak.next, newParagraph)
                    if (lastParagraph.parent is ListItem) {
                        insertNode(newParagraph, lastParagraph.parent.parent)
                    } else {
                        insertNode(newParagraph, lastParagraph)
                    }
                    lastParagraph = newParagraph
                    val nextSoftLineBreakCandidate = lastSoftLineBreak.next
                    lastSoftLineBreak.unlink()
                    if (nextSoftLineBreakCandidate is SoftLineBreak) {
                        lastSoftLineBreak = nextSoftLineBreakCandidate
                    }
                }
            }
        }
    }

    private fun moveAllChildren(fromNode: Node?, toNode: Node) {
        var nextNode = fromNode
        while (nextNode != null) {
            val bufferedNode = nextNode.next
            toNode.appendChild(nextNode)
            nextNode = bufferedNode
        }
    }

    private inner class UtpParagraphVisitor : AbstractVisitor() {
        override fun visit(softLineBreak: SoftLineBreak) {
            splitParagraphsBySoftLineBreak(softLineBreak)
        }
    }

    companion object {
        private fun insertNode(node: Node, insertAfterNode: Node): Node {
            insertAfterNode.insertAfter(node)
            return node
        }
    }
}
