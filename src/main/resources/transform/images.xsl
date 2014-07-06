<xsl:stylesheet version="1.0"
                xmlns:dictiography="com.dictiography.shared.ElexUtils"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        >

    <xsl:template match="void[@property='images']">
        <!--images-->
        <xsl:for-each select="array/void/object[@class='com.dictiography.client.entry.EntryImage']">
            <xsl:variable name="imgId"><xsl:value-of select="generate-id(.)"/></xsl:variable>
            <table class="imageTable"><tr>
                <td>
                    <xsl:for-each select="void/array/void/object[@class='com.dictiography.client.entry.ImageLink']">
                        <span class="id_head">
                            <xsl:number value="position()" format="1"/>
                            <xsl:if test="position()&lt;10"><xsl:text>&#160;&#160;</xsl:text></xsl:if><xsl:text>&#160;</xsl:text>
                        </span>
                        <a href="#" class="imglnk">
                            <xsl:attribute name="id">
                                <xsl:text>anchor_</xsl:text><xsl:value-of select="$imgId"/>
                                <xsl:text>_</xsl:text><xsl:number value="position()" format="1"/>
                                <xsl:text>_</xsl:text>
                                <xsl:choose>
                                    <xsl:when test="void[@property='xoffset']"><xsl:value-of select="void[@property='xoffset']/int/text()"/></xsl:when>
                                    <xsl:otherwise><xsl:text>0</xsl:text></xsl:otherwise>
                                </xsl:choose>
                                <xsl:text>_</xsl:text>
                                <xsl:choose>
                                    <xsl:when test="void[@property='yoffset']"><xsl:value-of select="void[@property='yoffset']/int/text()"/></xsl:when>
                                    <xsl:otherwise><xsl:text>0</xsl:text></xsl:otherwise>
                                </xsl:choose>
                            </xsl:attribute>
                            <xsl:value-of select="void/string/text()"/>
                        </a>
                        <br/>
                    </xsl:for-each>
                </td>
                <td>
                    <div class="imgContainer">
                        <img class="img">
                            <xsl:attribute name="id">img<xsl:value-of select="$imgId"/></xsl:attribute>
                            <xsl:attribute name="src">/media?l=<xsl:value-of select="$lang"/>&amp;hw=<xsl:value-of select="dictiography:encodeUrl($headword)"/>&amp;cs=<xsl:value-of select="void[@property='checksum']/long/text()"/></xsl:attribute>
                        </img>
                    </div>
                </td>
            </tr></table>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>