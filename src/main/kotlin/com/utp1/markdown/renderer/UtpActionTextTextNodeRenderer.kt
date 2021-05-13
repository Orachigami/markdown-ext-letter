package com.utp1.markdown.renderer

import org.commonmark.node.AbstractVisitor
import org.commonmark.node.Document
import org.commonmark.node.Node
import org.commonmark.node.Paragraph
import org.commonmark.renderer.NodeRenderer
import org.commonmark.renderer.text.TextContentNodeRendererContext
import org.commonmark.renderer.text.TextContentWriter

class UtpActionTextTextNodeRenderer(private val context: TextContentNodeRendererContext) : AbstractVisitor(), NodeRenderer {
    private val writer: TextContentWriter = context.writer

    override fun getNodeTypes(): Set<Class<out Node>> {
        return setOf(
            Document::class.java,
            Paragraph::class.java
        )
    }

    override fun render(node: Node) {
        node.accept(this)
    }

    override fun visit(document: Document) {
        writer.write("\r\n")
        visitChildren(document)
    }

    override fun visit(paragraph: Paragraph) {
        visitChildren(paragraph)
        writer.write(": ")
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
