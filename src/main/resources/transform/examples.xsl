<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        >

    <xsl:template match="void[@property='examples']">
            <table class="ex_block">
                <xsl:for-each select="array/void/object[@class='com.dictiography.client.entry.Example']">
                    <tr>
                        <td>
                            <xsl:if test="void[@property='audio']">
                                <a class="playSound">
                                    <xsl:attribute name="href">/media?l=nl&amp;hw=<xsl:value-of select="$headword"/>&amp;cs=<xsl:value-of select="void[@property='checksum']/long/text()"/></xsl:attribute>
                                    <img src="images/sound.png" />
                                </a>
                            </xsl:if>
                        </td>
                        <td>
                            <xsl:if test="void[@property='meta']"><span class="meta"><xsl:value-of select="void[@property='meta']/string/text()"/></span></xsl:if>

                            <span class="ex"><xsl:value-of select="void[@property='example']/string/text()"/></span>
                            <span class="ex_source"><xsl:value-of select="void[@property='source']/string/text()"/></span>
                            <!-- explanation if any -->
                            <xsl:if test="void[@property='explanation']/string/text()">
                                <xsl:text> — </xsl:text>
                                <span class="expl"><xsl:value-of select="void[@property='explanation']/string/text()"/></span>
                            </xsl:if>

                            <xsl:if test="void/array/void/object[@class='com.dictiography.client.entry.Translation']">
                                <table class="tr_block">
                                    <xsl:for-each select="void/array/void/object[@class='com.dictiography.client.entry.Translation']">
                                        <tr>
                                            <td><span class="locale"><xsl:value-of select="void[@property='locale']"/><xsl:text>:&#160;</xsl:text></span></td>
                                            <td><span class="tr"><xsl:value-of select="void[@property='translation']/string/text()"/></span></td>
                                        </tr>
                                    </xsl:for-each>
                                </table>
                            </xsl:if>
                        </td>
                    </tr>
                </xsl:for-each>
            </table>
    </xsl:template>
</xsl:stylesheet>