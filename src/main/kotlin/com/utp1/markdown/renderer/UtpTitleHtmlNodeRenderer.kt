package com.utp1.markdown.renderer

import org.commonmark.node.*
import org.commonmark.renderer.NodeRenderer
import org.commonmark.renderer.html.HtmlNodeRendererContext
import org.commonmark.renderer.html.HtmlWriter

class UtpTitleHtmlNodeRenderer(val context: HtmlNodeRendererContext) : AbstractVisitor(), NodeRenderer {
    private val writer: HtmlWriter = context.writer
    override fun getNodeTypes(): Set<Class<out Node>> {
        return setOf(
            Document::class.java,
            Paragraph::class.java,
            StrongEmphasis::class.java
        )
    }

    override fun render(node: Node) {
        node.accept(this)
    }

    override fun visit(document: Document) {
        writer.tag(
            "h3", mapOf(
                "style" to "font-size:22px;font-weight:400;"
            )
        )
        visitChildren(document)
        writer.tag("/h3")
    }

    override fun visit(paragraph: Paragraph) {
        visitChildren(paragraph)
    }

    override fun visit(strongEmphasis: StrongEmphasis) {
        writer.tag(
            "strong", mapOf(
                "style" to "color:{{mailingSettings.color}}"
            )
        )
        visitChildren(strongEmphasis)
        writer.tag("/strong")
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
