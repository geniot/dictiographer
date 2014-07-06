<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        >
    <xsl:template match="void[@property='grammar']">
        <xsl:if test="$lang='nl'">
            <xsl:call-template name="grammar.nl"/>
        </xsl:if>
        <xsl:if test="$lang='by'">
            <xsl:call-template name="grammar.by"/>
        </xsl:if>
    </xsl:template>
</xsl:stylesheet>