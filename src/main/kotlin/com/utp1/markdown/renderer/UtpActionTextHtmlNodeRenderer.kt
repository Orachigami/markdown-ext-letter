package com.utp1.markdown.renderer

import org.commonmark.node.AbstractVisitor
import org.commonmark.node.Node
import org.commonmark.node.Paragraph
import org.commonmark.renderer.NodeRenderer
import org.commonmark.renderer.html.HtmlNodeRendererContext
import org.commonmark.renderer.html.HtmlWriter

class UtpActionTextHtmlNodeRenderer(val context: HtmlNodeRendererContext) : AbstractVisitor(), NodeRenderer {
    private val writer: HtmlWriter = context.writer
    override fun getNodeTypes(): Set<Class<out Node>> {
        return setOf(
            Paragraph::class.java
        )
    }

    override fun render(node: Node) {
        node.accept(this)
    }

    override fun visit(paragraph: Paragraph) {
        writer.tag("p", mapOf(
            "style" to "text-align:center;font-size:19px;"
        ))
        visitChildren(paragraph)
        writer.tag("/p")
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
