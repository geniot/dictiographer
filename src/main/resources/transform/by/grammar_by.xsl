<xsl:stylesheet version="1.0"
                xmlns:dictiographer="com.dictiographer.view.TransformUtils"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        >

    <xsl:template name="grammar.by" match="void[@property='grammar']">

        <xsl:variable name="naz"><xsl:value-of select="number(count(object/void[@property='grammarNAZ']))"/></xsl:variable>
        <xsl:variable name="dze"><xsl:value-of select="number(count(object/void[@property='grammarDZE']))"/></xsl:variable>
        <xsl:variable name="pry"><xsl:value-of select="number(count(object/void[@property='grammarPRY']))"/></xsl:variable>

        <xsl:if test="$naz>0"><xsl:apply-templates select="object/void[@property='grammarNAZ']"/></xsl:if>
        <xsl:if test="$dze>0"><xsl:apply-templates select="object/void[@property='grammarDZE']"/></xsl:if>
        <xsl:if test="$pry>0"><xsl:apply-templates select="object/void[@property='grammarPRY']"/></xsl:if>

    </xsl:template>

</xsl:stylesheet>