package com.utp1.markdown.renderer

import org.commonmark.node.AbstractVisitor
import org.commonmark.node.Link
import org.commonmark.node.Node
import org.commonmark.node.Text
import org.commonmark.renderer.NodeRenderer
import org.commonmark.renderer.text.TextContentNodeRendererContext
import org.commonmark.renderer.text.TextContentWriter

class UtpActionButtonTextNodeRenderer(context: TextContentNodeRendererContext) : AbstractVisitor(), NodeRenderer {
    private val writer: TextContentWriter = context.writer

    override fun getNodeTypes(): Set<Class<out Node>> {
        return setOf(
            Link::class.java
        )
    }

    override fun render(node: Node) {
        node.accept(this)
    }

    override fun visit(link: Link) {
        val content = link.firstChild as Text
        writer.write("${link.destination} (${content.literal})")
    }
}
