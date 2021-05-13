package com.utp1.markdown.renderer

import org.commonmark.node.*
import org.commonmark.renderer.NodeRenderer
import org.commonmark.renderer.html.HtmlNodeRendererContext
import org.commonmark.renderer.html.HtmlWriter

class UtpActionButtonHtmlNodeRenderer(val context: HtmlNodeRendererContext) : AbstractVisitor(), NodeRenderer {
    private val writer: HtmlWriter = context.writer
    override fun getNodeTypes(): Set<Class<out Node>> {
        return setOf(
            Paragraph::class.java,
            Link::class.java
        )
    }

    override fun render(node: Node) {
        node.accept(this)
    }

    override fun visit(paragraph: Paragraph) {
        writer.tag("p", mapOf(
            "style" to "text-align:center;"
        ))
        visitChildren(paragraph)
        writer.tag("/p")
    }

    override fun visit(link: Link) {
        writer.tag("a", mapOf(
            "href" to link.destination,
            "style" to "display: inline-block;text-decoration: none;color: #ffffff;background-color: {{mailingSettings.color}};font-size: 22px;padding: 12px 40px;border-radius: 5px;border-bottom: 2px solid #90393a;"
        ))
        val content = link.firstChild as Text
        writer.raw(content.literal)
        writer.tag("/a")
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
