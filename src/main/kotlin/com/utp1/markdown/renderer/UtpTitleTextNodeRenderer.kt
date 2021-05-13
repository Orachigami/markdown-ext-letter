package com.utp1.markdown.renderer

import org.commonmark.node.AbstractVisitor
import org.commonmark.node.Document
import org.commonmark.node.Node
import org.commonmark.renderer.NodeRenderer
import org.commonmark.renderer.text.TextContentNodeRendererContext
import org.commonmark.renderer.text.TextContentWriter

class UtpTitleTextNodeRenderer(private val context: TextContentNodeRendererContext) : AbstractVisitor(), NodeRenderer {
    private val writer: TextContentWriter = context.writer

    override fun getNodeTypes(): Set<Class<out Node>> {
        return setOf(
            Document::class.java
        )
    }

    override fun render(node: Node) {
        node.accept(this)
    }

    override fun visit(document: Document) {
        visitChildren(document)
        writer.write("\r\n")
        writer.write("\r\n")
    }

    override fun visitChildren(parent: Node) {
        var node = parent.firstChild
        while (node != null) {
            val next = node.next
            context.render(node)
            node = next
        }
    }
}
