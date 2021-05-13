package com.utp1.markdown.renderer

import org.commonmark.node.AbstractVisitor
import org.commonmark.node.Document
import org.commonmark.node.Node
import org.commonmark.node.Paragraph
import org.commonmark.renderer.NodeRenderer
import org.commonmark.renderer.html.HtmlNodeRendererContext
import org.commonmark.renderer.html.HtmlWriter

class UtpInfoTitleHtmlNodeRenderer(val context: HtmlNodeRendererContext) : AbstractVisitor(), NodeRenderer {
    private val writer: HtmlWriter = context.writer
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
        writer.tag(
            "div", mapOf(
                "style" to "color:{{mailingSettings.color}};font-size:22px;font-weight:400;margin: 0;text-align:center;"
            )
        )
        visitChildren(document)
        writer.tag("/div")
    }

    override fun visit(paragraph: Paragraph) {
        visitChildren(paragraph)
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
