package com.utp1.markdown.internal

import org.commonmark.node.*
import org.commonmark.renderer.html.AttributeProvider
import org.commonmark.renderer.html.AttributeProviderContext

@Suppress("UNUSED_PARAMETER")
class UtpAttributeProvider(private val defaultValues: HashMap<String, String>, context: AttributeProviderContext) :
    AttributeProvider {
    override fun setAttributes(node: Node, tagName: String, attributes: MutableMap<String, String>) {
        when (node) {
            is Paragraph -> {
                if (node.firstChild is Link && node.firstChild == node.lastChild) {
                    attributes["style"] = "text-align:center;font-weight: bold"
                }
            }
            is Link -> {
                val url = attributes["href"]
                if (url != null) {
                    if (url.contains('?')) {
                        attributes["href"] += "&"
                    } else {
                        attributes["href"] += "?"
                    }
                    attributes["href"] += "letter=${defaultValues["letterCode"]}&channel=${defaultValues["channelCode"]}"
                }
                attributes["style"] = "color:{{mailingSettings.color}}"
            }
            is BulletList -> attributes["style"] = "margin-block-start: 0em;margin-block-end: 0em;"
            is OrderedList -> attributes["style"] = "margin-block-start: 0em;margin-block-end: 0em;"
            is Image -> attributes["style"] = "width:100%;max-width:500px;height:auto;display:block;"
        }
    }
}
