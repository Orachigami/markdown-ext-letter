package com.utp1.markdown.renderer

import org.commonmark.node.AbstractVisitor
import org.commonmark.node.Link
import org.commonmark.node.Node
import org.commonmark.node.Text
import org.commonmark.renderer.NodeRenderer
import org.commonmark.renderer.text.TextContentNodeRendererContext
import org.commonmark.renderer.text.TextContentWriter

class UtpLinkTextNodeRenderer(context: TextContentNodeRendererContext) : AbstractVisitor(), NodeRenderer {
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
        var text = link.destination
        if (link.firstChild != null && link.firstChild is Text) {
            text = (link.firstChild as Text).literal
        }
        if (link.next == null) {
            writer.write(text)
            writer.write(": ")
            writer.write(link.destination)
        } else {
            writer.write(text)
            writer.write(" (")
            writer.write(link.destination)
            writer.write(")")
        }
    }
}
