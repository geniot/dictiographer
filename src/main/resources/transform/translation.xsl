<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        >

    <xsl:template match="void[@property='translations']">
        <table class="tr_block">
            <xsl:for-each select="array/void/object[@class='com.dictiography.client.entry.Translation']">
                <tr>
                    <td><span class="locale"><xsl:value-of select="void[@property='locale']"/><xsl:text>:&#160;</xsl:text></span></td>
                    <td><span class="tr"><xsl:value-of select="void[@property='translation']/string/text()"/></span></td>
                </tr>
            </xsl:for-each>
        </table>
    </xsl:template>
</xsl:stylesheet>