<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        >

    <xsl:template match="void[@property='idiomExplanations']">
        <xsl:variable name="expCount">
            <xsl:value-of select="number(count(array/void/object[@class='com.dictiography.client.entry.IdiomExplanation']))"/>
        </xsl:variable>
            <xsl:for-each select="array/void/object[@class='com.dictiography.client.entry.IdiomExplanation']">

                           <!-- explanation if any -->
                            <xsl:if test="void[@property='explanation']/string/text()">
                                <xsl:text>&#160;—&#160;</xsl:text>
                                <xsl:if test="$expCount>1"><span class="expl"><xsl:number value="position()" format="1"/><xsl:text>)&#160;</xsl:text></span></xsl:if>
                                <xsl:if test="void[@property='meta']"><span class="meta"><xsl:value-of select="void[@property='meta']/string/text()"/></span></xsl:if>

                                <span class="expl">
                                    <xsl:value-of select="void[@property='explanation']/string/text()"/>
                                </span>
                            </xsl:if>

                            <xsl:if test="void/array/void/object[@class='com.dictiography.client.entry.Translation']">
                                <table class="tr_block">
                                    <xsl:for-each
                                            select="void/array/void/object[@class='com.dictiography.client.entry.Translation']">
                                        <tr>
                                            <td>
                                                <span class="locale">
                                                    <xsl:value-of select="void[@property='locale']"/><xsl:text>:&#160;</xsl:text>
                                                </span>
                                            </td>
                                            <td>
                                                <span class="tr">
                                                    <xsl:value-of select="void[@property='translation']/string/text()"/>
                                                </span>
                                            </td>
                                        </tr>
                                    </xsl:for-each>
                                </table>
                            </xsl:if>
                            <xsl:if test="not(void/array/void/object[@class='com.dictiography.client.entry.Translation'])">
                                <br/>
                            </xsl:if>
            </xsl:for-each>

    </xsl:template>
</xsl:stylesheet>