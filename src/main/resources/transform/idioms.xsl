<xsl:stylesheet version="1.0"
                xmlns:dictiography="com.dictiography.shared.ElexUtils"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        >

    <xsl:template match="void[@property='idioms']">
        <xsl:variable name="idCount"><xsl:value-of select="number(count(array/void/object[@class='com.dictiography.client.entry.Idioom']))"/></xsl:variable>
        <table class="idiomen_block"><tr>
            <td style="padding-top:4px">
                <span class="id_head">
                    <xsl:if test="$idCount=1"><xsl:value-of select="dictiography:getProperty($lang,'label.idiom')"/></xsl:if>
                    <xsl:if test="$idCount>1"><xsl:value-of select="dictiography:getProperty($lang,'label.idioms')"/></xsl:if>:&#160;
                </span>
            </td>
            <td>
                <table>
                <xsl:for-each select="array/void/object[@class='com.dictiography.client.entry.Idioom']">
                        <tr>

                        <td style="text-align:right">
                        <span>
                            <xsl:attribute name="class">id</xsl:attribute>
                            <xsl:value-of select="void[@property='idioom']/string/text()"/>
                        </span>
                        </td>

                        <td>
                        <xsl:if test="void[@property='idiomExplanations']">
                                <span class="expl"><xsl:apply-templates select="void[@property='idiomExplanations']"/></span>
                        </xsl:if>
                        </td>

                        </tr>
                </xsl:for-each>
                </table>
            </td>
        </tr></table>
    </xsl:template>
</xsl:stylesheet>