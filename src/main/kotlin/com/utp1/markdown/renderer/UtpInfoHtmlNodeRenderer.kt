package com.utp1.markdown.renderer

import org.commonmark.node.AbstractVisitor
import org.commonmark.node.Document
import org.commonmark.node.Node
import org.commonmark.renderer.NodeRenderer
import org.commonmark.renderer.html.HtmlNodeRendererContext
import org.commonmark.renderer.html.HtmlWriter

class UtpInfoHtmlNodeRenderer(private val context: HtmlNodeRendererContext) : AbstractVisitor(), NodeRenderer {
    private val writer: HtmlWriter = context.writer

    override fun getNodeTypes(): Set<Class<out Node>> {
        return setOf(
            Document::class.java
        )
    }

    override fun render(node: Node) {
        node.accept(this)
    }

    override fun visit(document: Document) {
        writer.tag("div", mapOf(
            "style" to "margin:25px 0;border-radius:5px;border:2px solid #cccccc;padding:20px 15px 10px;"
        ))
        writer.raw("{{infoTitleHtml}}")
        visitChildren(document)
        writer.tag("/div")
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
